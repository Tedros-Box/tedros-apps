package org.tedros.it.tools.module.governance.action;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.it.tools.entity.CatalogService;
import org.tedros.it.tools.entity.ServiceCatalog;
import org.tedros.it.tools.entity.ServiceGroup;
import org.tedros.it.tools.entity.ServiceVariant;
import org.tedros.it.tools.module.governance.model.ServiceCatalogMV;
import org.tedros.util.TFileUtil;
import org.tedros.util.TedrosFolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ExportServiceCatalogAction extends TPresenterAction {
	
	public ExportServiceCatalogAction() {
		super(TActionType.PRINT);
	}

	@Override
	public boolean runBefore() {

		TDynaPresenter<ServiceCatalogMV> presenter = getPresenter();

		ServiceCatalogMV mv = presenter.getBehavior().getModelView();

		if (mv.isChanged()) {
			TMessage message = new TMessage(TMessageType.WARNING, "Existem alterações não salvas.");
			super.getPresenter().getView().tShowModal(new TMessageBox(message), true);
			return false;
		}

		try {

			ServiceCatalog serviceCatalog = mv.getEntity();

			String folder = TedrosFolder.EXPORT_FOLDER.getFullPath();
			File jsonFile = new File(folder + "/" + serviceCatalog.getName() + ".json");

			// TODO: Implementar a geração do JSON apartir da entidade ServiceCatalog
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode rootNode = mapper.createObjectNode();
			ObjectNode catalogNode = mapper.createObjectNode();
			catalogNode.put("nome", serviceCatalog.getName() != null ? serviceCatalog.getName() : "");

			ArrayNode gruposNode = mapper.createArrayNode();
			if (serviceCatalog.getGroups() != null) {
				for (ServiceGroup group : serviceCatalog.getGroups()) {
					ObjectNode groupNode = mapper.createObjectNode();
					groupNode.put("nome", group.getName() != null ? group.getName() : "");

					ArrayNode servicosNode = mapper.createArrayNode();
					if (group.getServices() != null) {
						for (CatalogService service : group.getServices()) {
							ObjectNode svcNode = mapper.createObjectNode();
							
							if (service.getNumber() != null) {
								svcNode.put("numero", service.getNumber());
							} else {
								svcNode.putNull("numero");
							}
							
							svcNode.put("nome", service.getName() != null ? service.getName() : "");

							ArrayNode variantesNode = mapper.createArrayNode();
							if (service.getVariants() != null) {
								for (ServiceVariant variant : service.getVariants()) {
									ObjectNode vNode = mapper.createObjectNode();
									vNode.put("id", variant.getVariantId() != null ? variant.getVariantId() : "");
									vNode.put("complexidade", variant.getComplexity() != null ? variant.getComplexity() : "");
									vNode.put("escopo", variant.getScope() != null ? variant.getScope() : "");
									
									if (variant.getEstimatedHours() != null) {
										// Format as integer if it's a whole number, to match the sample
										double hours = variant.getEstimatedHours();
										if (hours == Math.floor(hours) && !Double.isInfinite(hours)) {
											vNode.put("horas_previstas", (int) hours);
										} else {
											vNode.put("horas_previstas", hours);
										}
									} else {
										vNode.putNull("horas_previstas");
									}
									
									vNode.put("entregaveis", variant.getDeliverables() != null ? variant.getDeliverables() : "");
									vNode.put("perfis_exigidos", variant.getRequiredProfiles() != null ? variant.getRequiredProfiles() : "");
									vNode.put("atividades_desempenhadas", variant.getActivitiesPerformed() != null ? variant.getActivitiesPerformed() : "");
									variantesNode.add(vNode);
								}
							}
							svcNode.set("variantes", variantesNode);
							servicosNode.add(svcNode);
						}
					}
					groupNode.set("servicos", servicosNode);
					gruposNode.add(groupNode);
				}
			}
			catalogNode.set("grupos", gruposNode);
			rootNode.set("catalogo", catalogNode);

			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);

			Files.write(jsonFile.toPath(), json.getBytes(StandardCharsets.UTF_8));

			TMessage message = new TMessage(TMessageType.INFO, "O arquivo Json foi gerado na pasta : " + folder);
			super.getPresenter().getView().tShowModal(new TMessageBox(message), true);

			TFileUtil.open(jsonFile);
		} catch (Exception e) {
			TMessage message = new TMessage(TMessageType.ERROR, "Erro ao gerar arquivo Json.");
			super.getPresenter().getView().tShowModal(new TMessageBox(message), true);
		}

		return false;
	}

	@Override
	public void runAfter() {
		// TODO Auto-generated method stub

	}

}
