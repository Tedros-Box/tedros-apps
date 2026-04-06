package org.tedros.it.tools.module.governance.model;

import org.tedros.fx.annotation.control.TDoubleField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.entity.ServiceVariant;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Detail ModelView for ServiceVariant.
 * Used as an inline row inside the CatalogServiceMV form.
 * Exposes all variant fields for editing: variantId, complexity,
 * estimatedHours, scope, deliverables, requiredProfiles, activitiesPerformed.
 *
 * @author Davis Gordon
 */
@TDetailTableViewPresenter(
    tableView = @TTableView(
        control = @TControl(maxHeight = 220, parse = true),
        columns = {
            @TTableColumn(text = ItToolsKey.VARIANT_ID,            	cellValue = "variantId"),
            @TTableColumn(text = ItToolsKey.VARIANT_COMPLEXITY,    	cellValue = "complexity"),
            @TTableColumn(text = ItToolsKey.VARIANT_ESTIMATED_HOURS,cellValue = "estimatedHours")
        }))
public class ServiceVariantDetailMV extends TEntityModelView<ServiceVariant> {
	
    @TLabel(text = ItToolsKey.VARIANT_ID)
    @TTextField(maxLength = 10, required = true)
    @TFlowPane(hgap = 20, vgap = 12,
        pane = @TPane(children = {"variantId", "complexity", "estimatedHours"}))
    private SimpleStringProperty variantId;

    @TLabel(text = ItToolsKey.VARIANT_COMPLEXITY)
    @TTextField(maxLength = 50, required = true)
    private SimpleStringProperty complexity;

    @TLabel(text = ItToolsKey.VARIANT_ESTIMATED_HOURS)
    @TDoubleField
    private SimpleDoubleProperty estimatedHours;
    
    @TTabPane(tabs = { 
			@TTab(text = ItToolsKey.VARIANT_SCOPE, fields={"scope"}), 
			@TTab(text = ItToolsKey.VARIANT_DELIVERABLES, fields={"deliverables"}),
			@TTab(text = ItToolsKey.VARIANT_REQUIRED_PROFILES, fields={"requiredProfiles"}),
			@TTab(text = ItToolsKey.VARIANT_ACTIVITIES_PERFORMED, fields={"activitiesPerformed"})
			})
    @TTextAreaField(wrapText = true, prefRowCount = 4)
    private SimpleStringProperty scope;

    @TTextAreaField(wrapText = true, prefRowCount = 4)
    private SimpleStringProperty deliverables;

    @TTextAreaField(wrapText = true, prefRowCount = 4)
    private SimpleStringProperty requiredProfiles;

    @TTextAreaField(wrapText = true, prefRowCount = 4)
    private SimpleStringProperty activitiesPerformed;

    public ServiceVariantDetailMV(ServiceVariant entity) {
        super(entity);
        super.formatToString("[%s] %s", variantId, complexity);
    }

    // --- Getters and Setters ---

    public SimpleStringProperty getVariantId() {
        return variantId;
    }

    public void setVariantId(SimpleStringProperty variantId) {
        this.variantId = variantId;
    }

    public SimpleStringProperty getComplexity() {
        return complexity;
    }

    public void setComplexity(SimpleStringProperty complexity) {
        this.complexity = complexity;
    }

    public SimpleDoubleProperty getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(SimpleDoubleProperty estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public SimpleStringProperty getScope() {
        return scope;
    }

    public void setScope(SimpleStringProperty scope) {
        this.scope = scope;
    }

    public SimpleStringProperty getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(SimpleStringProperty deliverables) {
        this.deliverables = deliverables;
    }

    public SimpleStringProperty getRequiredProfiles() {
        return requiredProfiles;
    }

    public void setRequiredProfiles(SimpleStringProperty requiredProfiles) {
        this.requiredProfiles = requiredProfiles;
    }

    public SimpleStringProperty getActivitiesPerformed() {
        return activitiesPerformed;
    }

    public void setActivitiesPerformed(SimpleStringProperty activitiesPerformed) {
        this.activitiesPerformed = activitiesPerformed;
    }
}
