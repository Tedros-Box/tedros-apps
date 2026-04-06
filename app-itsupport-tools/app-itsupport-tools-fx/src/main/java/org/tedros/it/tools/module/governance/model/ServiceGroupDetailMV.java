package org.tedros.it.tools.module.governance.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.entity.ServiceGroup;

import javafx.beans.property.SimpleStringProperty;

/**
 * Detail ModelView for ServiceGroup.
 * Used as an inline row inside the ServiceCatalogMV form.
 * Only the 'name' field is exposed for editing; 'services' is managed elsewhere.
 *
 * @author Davis Gordon
 */
@TDetailTableViewPresenter(
		tableView = @TTableView(
			control=@TControl(maxHeight=200, parse = true),
			columns = 
			{
				@TTableColumn(text = TUsualKey.NAME, cellValue="name")
			}))
public class ServiceGroupDetailMV extends TEntityModelView<ServiceGroup> {

    @TLabel(text = ItToolsKey.SERVICE_GROUP_NAME)
    @TTextField(maxLength = 200, required = true)
    private SimpleStringProperty name;

    public ServiceGroupDetailMV(ServiceGroup entity) {
        super(entity);
        super.formatToString("%s", name);
    }

    // --- Getters and Setters ---

    public SimpleStringProperty getName() {
        return name;
    }

    public void setName(SimpleStringProperty name) {
        this.name = name;
    }
}
