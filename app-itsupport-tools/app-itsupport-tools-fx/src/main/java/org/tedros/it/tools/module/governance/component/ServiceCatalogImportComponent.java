package org.tedros.it.tools.module.governance.component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.TLanguage;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.component.ITComponent;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.it.tools.ejb.controller.IServiceCatalogController;
import org.tedros.it.tools.entity.CatalogService;
import org.tedros.it.tools.entity.ServiceCatalog;
import org.tedros.it.tools.entity.ServiceGroup;
import org.tedros.it.tools.entity.ServiceVariant;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * Component for importing a ServiceCatalog from a JSON file.
 * <p>
 * Workflow:
 * 1) Select JSON file via FileChooser
 * 2) Validate the JSON structure and display any inconsistencies
 * 3) Preview the data structure that will be imported
 * 4) Import button triggers the save via ServiceCatalogController
 *
 * @author Davis Gordon
 */
public class ServiceCatalogImportComponent extends BorderPane implements ITComponent {

    @SuppressWarnings("rawtypes")
    private ITView view;
    private SimpleBooleanProperty progressIndicatorVisible = new SimpleBooleanProperty(false);

    // UI elements
    private TButton btnSelectFile;
    private TButton btnImport;
    private TLabel lblSelectedFile;
    private TLabel lblValidationStatus;
    private VBox validationMessagesBox;
    private TreeView<String> previewTree;
    private VBox centerBox;

    // Parsed data
    private ServiceCatalog parsedCatalog;
    private boolean jsonValid = false;

    // Process
    private ImportProcess importProcess;

    public ServiceCatalogImportComponent() {
    }

    @Override
    public void tInitializeComponent(ITComponentDescriptor descriptor) {
        view = descriptor.getForm().gettPresenter().getView();
        view.gettProgressIndicator().bind(progressIndicatorVisible);

        importProcess = new ImportProcess();
        importProcess.setOnFailed(e -> showAlert(TMessageType.ERROR, importProcess.getException().getMessage()));
        importProcess.setOnSucceeded(e -> {
            List<TResult<ServiceCatalog>> results = importProcess.getValue();
            if (results != null && !results.isEmpty()) {
                TResult<ServiceCatalog> result = results.get(0);
                if (result.getState() == TState.SUCCESS) {
                    showAlert(TMessageType.INFO,
                            TLanguage.getInstance().getString(ItToolsKey.MSG_IMPORT_SUCCESS) + result.getValue().getId());
                    resetUI();
                } else {
                    showAlert(TMessageType.ERROR, result.getMessage());
                }
            }
        });

        progressIndicatorVisible.bind(importProcess.runningProperty());

        buildUI();
    }

    @Override
    public void tStopComponent() {
        // cleanup if needed
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // UI Building
    // ─────────────────────────────────────────────────────────────────────────────

    private void buildUI() {
    	
    	TLanguage lang = TLanguage.getInstance();
    	
        setPadding(new Insets(16));

        // ── Top: title ────────────────────────────────────────────────────────
        TLabel lblTitle = new TLabel(lang.getString(ItToolsKey.TITLE_SERVICE_CATALOG_IMPORT));
        lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Separator sep = new Separator();
        sep.setPadding(new Insets(8, 0, 8, 0));

        // ── File selector row ─────────────────────────────────────────────────
        btnSelectFile = new TButton(lang.getString(ItToolsKey.LABEL_SELECT_JSON_FILE));
        btnSelectFile.setOnAction(e -> selectFile());

        lblSelectedFile = new TLabel(lang.getString(ItToolsKey.MSG_NO_FILE_SELECTED));
        lblSelectedFile.setStyle("-fx-text-fill: -fx-mid-text-color; -fx-font-style: italic;");
        HBox.setHgrow(lblSelectedFile, Priority.ALWAYS);
        lblSelectedFile.setMaxWidth(Double.MAX_VALUE);

        HBox fileRow = new HBox(12, btnSelectFile, lblSelectedFile);
        fileRow.setAlignment(Pos.CENTER_LEFT);

        // ── Validation status ─────────────────────────────────────────────────
        lblValidationStatus = new TLabel();
        lblValidationStatus.setStyle("-fx-font-weight: bold;");
        lblValidationStatus.setVisible(false);

        validationMessagesBox = new VBox(4);
        validationMessagesBox.setStyle("-fx-border-color: derive(-fx-base,-20%); -fx-border-radius: 4; "
                + "-fx-background-color: derive(-fx-base,5%); -fx-background-radius: 4; -fx-padding: 8;");
        validationMessagesBox.setVisible(false);
        validationMessagesBox.setManaged(false);

        // ── Preview tree ──────────────────────────────────────────────────────
        previewTree = new TreeView<>();
        previewTree.setVisible(false);
        previewTree.setManaged(false);
        VBox.setVgrow(previewTree, Priority.ALWAYS);

        TLabel lblPreviewTitle = new TLabel(lang.getString(ItToolsKey.MSG_STRUCTURE_TO_IMPORT));
        lblPreviewTitle.setStyle("-fx-font-weight: bold; -fx-padding: 8 0 4 0;");
        lblPreviewTitle.setVisible(false);
        lblPreviewTitle.setManaged(false);

        ScrollPane scrollPreview = new ScrollPane(previewTree);
        scrollPreview.setMinHeight(200);
        scrollPreview.setPrefHeight(500);        
        scrollPreview.setFitToWidth(true);
        scrollPreview.setFitToHeight(true);
        scrollPreview.setVisible(false);
        scrollPreview.setManaged(false);
        VBox.setVgrow(scrollPreview, Priority.ALWAYS);

        // ── Import button ─────────────────────────────────────────────────────
        btnImport = new TButton(lang.getString(ItToolsKey.LABEL_IMPORT));
        btnImport.setStyle("-fx-font-weight: bold;");
        btnImport.setDisable(true);
        btnImport.setOnAction(e -> doImport());

        HBox bottomRow = new HBox(btnImport);
        bottomRow.setAlignment(Pos.CENTER_RIGHT);
        bottomRow.setPadding(new Insets(12, 0, 0, 0));

        // ── Assemble center box ───────────────────────────────────────────────
        centerBox = new VBox(10,
                lblTitle,
                sep,
                fileRow,
                lblValidationStatus,
                validationMessagesBox,
                lblPreviewTitle,
                scrollPreview);
        VBox.setVgrow(scrollPreview, Priority.ALWAYS);
        centerBox.setPrefHeight(Double.MAX_VALUE);

        // Store references so we can show/hide them later
        this.getProperties().put("lblPreviewTitle", lblPreviewTitle);
        this.getProperties().put("scrollPreview", scrollPreview);

        setCenter(centerBox);
        setBottom(bottomRow);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // File Selection & Parsing
    // ─────────────────────────────────────────────────────────────────────────────

    private void selectFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(TLanguage.getInstance().getString(ItToolsKey.TITLE_SELECT_JSON_FILE));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(TLanguage.getInstance().getString(ItToolsKey.LABEL_JSON_FILES), "*.json"));

        File file = chooser.showOpenDialog(this.getScene() != null ? this.getScene().getWindow() : null);
        if (file == null) {
            return;
        }

        lblSelectedFile.setText(file.getAbsolutePath());
        lblSelectedFile.setStyle("-fx-text-fill: -fx-text-base-color; -fx-font-style: normal;");

        parseAndValidate(file);
    }

    private void parseAndValidate(File file) {
        // Reset state
        parsedCatalog = null;
        jsonValid = false;
        btnImport.setDisable(true);
        validationMessagesBox.getChildren().clear();
        previewTree.setRoot(null);

        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();

        try {
            String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(content);

            // ── Structural validation ──────────────────────────────────────────
            JsonNode catalogNode = root.get("catalogo");
            if (catalogNode == null) {
                errors.add(TLanguage.getInstance().getString(ItToolsKey.ERR_ROOT_CATALOG_NOT_FOUND));
            } else {
                JsonNode nomeNode = catalogNode.get("nome");
                if (nomeNode == null || nomeNode.asText().isBlank()) {
                    errors.add(TLanguage.getInstance().getString(ItToolsKey.ERR_CATALOG_NAME_MISSING));
                }

                JsonNode gruposNode = catalogNode.get("grupos");
                if (gruposNode == null || !gruposNode.isArray() || gruposNode.size() == 0) {
                    errors.add(TLanguage.getInstance().getString(ItToolsKey.ERR_CATALOG_GROUPS_MISSING));
                } else {
                    for (int gi = 0; gi < gruposNode.size(); gi++) {
                        JsonNode grupo = gruposNode.get(gi);
                        String grupoCtx = "grupos[" + gi + "]";

                        if (!grupo.hasNonNull("nome") || grupo.get("nome").asText().isBlank()) {
                            errors.add(grupoCtx + TLanguage.getInstance().getString(ItToolsKey.ERR_NAME_MISSING));
                        }

                        JsonNode servicosNode = grupo.get("servicos");
                        if (servicosNode == null || !servicosNode.isArray() || servicosNode.size() == 0) {
                            warnings.add(grupoCtx + TLanguage.getInstance().getString(ItToolsKey.WARN_NO_SERVICES));
                        } else {
                            for (int si = 0; si < servicosNode.size(); si++) {
                                JsonNode servico = servicosNode.get(si);
                                String svcCtx = grupoCtx + ".servicos[" + si + "]";

                                if (!servico.hasNonNull("numero")) {
                                    errors.add(svcCtx + TLanguage.getInstance().getString(ItToolsKey.ERR_NUMBER_MISSING));
                                }
                                if (!servico.hasNonNull("nome") || servico.get("nome").asText().isBlank()) {
                                    errors.add(svcCtx + TLanguage.getInstance().getString(ItToolsKey.ERR_NAME_MISSING));
                                }

                                JsonNode variantesNode = servico.get("variantes");
                                if (variantesNode == null || !variantesNode.isArray() || variantesNode.size() == 0) {
                                    warnings.add(svcCtx + TLanguage.getInstance().getString(ItToolsKey.WARN_NO_VARIANTS));
                                } else {
                                    for (int vi = 0; vi < variantesNode.size(); vi++) {
                                        JsonNode v = variantesNode.get(vi);
                                        String vCtx = svcCtx + ".variantes[" + vi + "]";
                                        if (!v.hasNonNull("id")) {
                                            errors.add(vCtx + TLanguage.getInstance().getString(ItToolsKey.ERR_ID_MISSING));
                                        }
                                        if (!v.hasNonNull("complexidade") || v.get("complexidade").asText().isBlank()) {
                                            errors.add(vCtx + TLanguage.getInstance().getString(ItToolsKey.ERR_COMPLEXITY_MISSING));
                                        }
                                        if (!v.hasNonNull("horas_previstas")) {
                                            errors.add(vCtx + TLanguage.getInstance().getString(ItToolsKey.ERR_HOURS_MISSING));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ── Display validation results ─────────────────────────────────────
            if (errors.isEmpty()) {
                lblValidationStatus.setText(warnings.isEmpty()
                        ? TLanguage.getInstance().getString(ItToolsKey.MSG_JSON_VALID)
                        : String.format(TLanguage.getInstance().getString(ItToolsKey.MSG_JSON_VALID_WARNINGS), warnings.size()));
                lblValidationStatus.setStyle("-fx-font-weight: bold; -fx-text-fill: " + (warnings.isEmpty() ? "#2e7d32" : "#f57c00") + ";");
                jsonValid = true;
                btnImport.setDisable(false);

                // Build object graph
                parsedCatalog = buildCatalog(catalogNode);

                // Show preview
                buildPreviewTree(parsedCatalog);

            } else {
                lblValidationStatus.setText(String.format(TLanguage.getInstance().getString(ItToolsKey.MSG_JSON_INVALID), errors.size()));
                lblValidationStatus.setStyle("-fx-font-weight: bold; -fx-text-fill: #c62828;");
                jsonValid = false;
                btnImport.setDisable(true);
            }

            lblValidationStatus.setVisible(true);

            // Populate validation messages box
            if (!errors.isEmpty() || !warnings.isEmpty()) {
                for (String err : errors) {
                    TLabel lbl = new TLabel("✘ " + err);
                    lbl.setStyle("-fx-text-fill: #c62828;");
                    lbl.setWrapText(true);
                    validationMessagesBox.getChildren().add(lbl);
                }
                for (String warn : warnings) {
                    TLabel lbl = new TLabel("⚠ " + warn);
                    lbl.setStyle("-fx-text-fill: #f57c00;");
                    lbl.setWrapText(true);
                    validationMessagesBox.getChildren().add(lbl);
                }
                validationMessagesBox.setVisible(true);
                validationMessagesBox.setManaged(true);
            } else {
                validationMessagesBox.setVisible(false);
                validationMessagesBox.setManaged(false);
            }

        } catch (IOException ex) {
            lblValidationStatus.setText(TLanguage.getInstance().getString(ItToolsKey.MSG_ERROR_READING_FILE) + ex.getMessage());
            lblValidationStatus.setStyle("-fx-font-weight: bold; -fx-text-fill: #c62828;");
            lblValidationStatus.setVisible(true);
        } catch (Exception ex) {
            lblValidationStatus.setText(TLanguage.getInstance().getString(ItToolsKey.MSG_ERROR_PROCESSING_JSON) + ex.getMessage());
            lblValidationStatus.setStyle("-fx-font-weight: bold; -fx-text-fill: #c62828;");
            lblValidationStatus.setVisible(true);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // Object Graph Builder (JSON → Entities)
    // ─────────────────────────────────────────────────────────────────────────────

    private ServiceCatalog buildCatalog(JsonNode catalogNode) {
        ServiceCatalog catalog = new ServiceCatalog();
        catalog.setName(catalogNode.get("nome").asText());

        JsonNode gruposNode = catalogNode.get("grupos");
        List<ServiceGroup> groups = new ArrayList<>();

        if (gruposNode != null && gruposNode.isArray()) {
            for (JsonNode grupoNode : gruposNode) {
                ServiceGroup group = new ServiceGroup();
                group.setName(grupoNode.get("nome").asText());
                group.setCatalog(catalog);

                JsonNode servicosNode = grupoNode.get("servicos");
                List<CatalogService> services = new ArrayList<>();

                if (servicosNode != null && servicosNode.isArray()) {
                    for (JsonNode svcNode : servicosNode) {
                        CatalogService service = new CatalogService();
                        service.setNumber(svcNode.hasNonNull("numero") ? svcNode.get("numero").asInt() : 0);
                        service.setName(svcNode.hasNonNull("nome") ? svcNode.get("nome").asText() : "");
                        service.setGroup(group);

                        JsonNode variantesNode = svcNode.get("variantes");
                        List<ServiceVariant> variants = new ArrayList<>();

                        if (variantesNode != null && variantesNode.isArray()) {
                            for (JsonNode vNode : variantesNode) {
                                ServiceVariant variant = new ServiceVariant();
                                variant.setVariantId(vNode.hasNonNull("id") ? vNode.get("id").asText() : "-");
                                variant.setComplexity(vNode.hasNonNull("complexidade") ? vNode.get("complexidade").asText() : "");
                                variant.setScope(vNode.hasNonNull("escopo") ? vNode.get("escopo").asText() : "");
                                variant.setEstimatedHours(vNode.hasNonNull("horas_previstas")
                                        ? vNode.get("horas_previstas").asDouble() : 0.0);
                                variant.setDeliverables(vNode.hasNonNull("entregaveis") ? vNode.get("entregaveis").asText() : "");
                                variant.setRequiredProfiles(vNode.hasNonNull("perfis_exigidos") ? vNode.get("perfis_exigidos").asText() : "");
                                variant.setActivitiesPerformed(vNode.hasNonNull("atividades_desempenhadas")
                                        ? vNode.get("atividades_desempenhadas").asText() : "");
                                variant.setService(service);
                                variants.add(variant);
                            }
                        }

                        service.setVariants(variants);
                        services.add(service);
                    }
                }

                group.setServices(services);
                groups.add(group);
            }
        }

        catalog.setGroups(groups);
        return catalog;
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // Preview Tree Builder
    // ─────────────────────────────────────────────────────────────────────────────

    private void buildPreviewTree(ServiceCatalog catalog) {
        TreeItem<String> rootItem = new TreeItem<>("📁 " + catalog.getName());
        rootItem.setExpanded(true);

        int totalServices = 0;
        int totalVariants = 0;

        if (catalog.getGroups() != null) {
            for (ServiceGroup group : catalog.getGroups()) {
                int groupSvcCount = group.getServices() != null ? group.getServices().size() : 0;
                TreeItem<String> groupItem = new TreeItem<>("📂 " + group.getName()
                        + "  [" + groupSvcCount + TLanguage.getInstance().getString(ItToolsKey.LABEL_SERVICES) + "]");
                groupItem.setExpanded(true);

                if (group.getServices() != null) {
                    for (CatalogService svc : group.getServices()) {
                        int varCount = svc.getVariants() != null ? svc.getVariants().size() : 0;
                        TreeItem<String> svcItem = new TreeItem<>(
                                "📄 [" + svc.getNumber() + "] " + svc.getName()
                                + "  (" + varCount + TLanguage.getInstance().getString(ItToolsKey.LABEL_VARIANTS) + ")");
                        totalVariants += varCount;

                        if (svc.getVariants() != null) {
                            for (ServiceVariant v : svc.getVariants()) {
                                TreeItem<String> varItem = new TreeItem<>(
                                        "   ▸ " + TLanguage.getInstance().getString(ItToolsKey.VARIANT_ID).replace(" ID", "") + " " + v.getVariantId()
                                        + " | " + v.getComplexity()
                                        + " | " + v.getEstimatedHours() + "h");
                                svcItem.getChildren().add(varItem);
                            }
                        }

                        groupItem.getChildren().add(svcItem);
                        totalServices++;
                    }
                }

                rootItem.getChildren().add(groupItem);
            }
        }

        // Summary node
        TreeItem<String> summary = new TreeItem<>(
                "📊 " + TLanguage.getInstance().getString(ItToolsKey.LABEL_SUMMARY) + (catalog.getGroups() != null ? catalog.getGroups().size() : 0)
                + TLanguage.getInstance().getString(ItToolsKey.LABEL_GROUPS) + totalServices + TLanguage.getInstance().getString(ItToolsKey.LABEL_SERVICES) + ", " + totalVariants + TLanguage.getInstance().getString(ItToolsKey.LABEL_VARIANTS));
        rootItem.getChildren().add(0, summary);

        previewTree.setRoot(rootItem);

        // Show preview elements
        TLabel lblPreviewTitle = (TLabel) this.getProperties().get("lblPreviewTitle");
        ScrollPane scrollPreview = (ScrollPane) this.getProperties().get("scrollPreview");
        if (lblPreviewTitle != null) {
            lblPreviewTitle.setVisible(true);
            lblPreviewTitle.setManaged(true);
        }
        if (scrollPreview != null) {
            scrollPreview.setVisible(true);
            scrollPreview.setManaged(true);
        }
        previewTree.setVisible(true);
        previewTree.setManaged(true);
        previewTree.setPrefHeight(400);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // Import
    // ─────────────────────────────────────────────────────────────────────────────

    private void doImport() {
        if (!jsonValid || parsedCatalog == null) {
            showAlert(TMessageType.WARNING, TLanguage.getInstance().getString(ItToolsKey.MSG_FIX_ERRORS));
            return;
        }

        importProcess.save(parsedCatalog);
        importProcess.startProcess();
    }

    private void resetUI() {
        parsedCatalog = null;
        jsonValid = false;
        btnImport.setDisable(true);
        lblSelectedFile.setText(TLanguage.getInstance().getString(ItToolsKey.MSG_NO_FILE_SELECTED));
        lblSelectedFile.setStyle("-fx-text-fill: -fx-mid-text-color; -fx-font-style: italic;");
        lblValidationStatus.setVisible(false);
        validationMessagesBox.getChildren().clear();
        validationMessagesBox.setVisible(false);
        validationMessagesBox.setManaged(false);
        previewTree.setRoot(null);
        previewTree.setVisible(false);
        previewTree.setManaged(false);

        TLabel lblPreviewTitle = (TLabel) this.getProperties().get("lblPreviewTitle");
        ScrollPane scrollPreview = (ScrollPane) this.getProperties().get("scrollPreview");
        if (lblPreviewTitle != null) {
            lblPreviewTitle.setVisible(false);
            lblPreviewTitle.setManaged(false);
        }
        if (scrollPreview != null) {
            scrollPreview.setVisible(false);
            scrollPreview.setManaged(false);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────────

    private void showAlert(TMessageType type, String content) {
        TMessage message = new TMessage(type, content);
        view.tShowModal(new TMessageBox(message), true);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // Inner Process Class
    // ─────────────────────────────────────────────────────────────────────────────

    private class ImportProcess extends TEntityProcess<ServiceCatalog> {
        public ImportProcess() {
            super(ServiceCatalog.class, IServiceCatalogController.JNDI_NAME);
        }
    }
}
