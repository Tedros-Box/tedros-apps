package org.tedros.it.tools.module.gmud.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TIntegerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.domain.TValidateNumber;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.entity.GmudIssueReference;
import org.tedros.it.tools.module.gmud.builder.GetRedmineIssueActionBuilder;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

@TForm(scroll = false)
@TDetailTableViewPresenter(
		tableView = @TTableView(
			control=@TControl(minHeight=400, parse = true),
			columns = 
			{
				@TTableColumn(text = ItToolsKey.ISSUE_ID, cellValue="issueId"),  
				@TTableColumn(text = TUsualKey.TITLE, cellValue="issueTitle")
			}))
public class GmudIssueReferenceMV extends TEntityModelView<GmudIssueReference> {
	
	@TLabel(text=ItToolsKey.ISSUE_ID)
	@TIntegerField(validate=TValidateNumber.GREATHER_THAN_ZERO,
		onAction = GetRedmineIssueActionBuilder.class)
	@THBox(pane=@TPane(children={"issueId", "issueTitle"}),
	hgrow = @THGrow(priority = {@TPriority(field = "issueId",priority = Priority.NEVER),
			@TPriority(field = "issueTitle", priority = Priority.ALWAYS)}))	
	private SimpleIntegerProperty issueId;

	@TLabel(text=TUsualKey.TITLE)
	@TTextField(maxLength=200)
    private SimpleStringProperty issueTitle;

	public GmudIssueReferenceMV(GmudIssueReference entity) {
		super(entity);
		super.formatToString("%s - %s", issueId, issueTitle);
	}

	public SimpleIntegerProperty getIssueId() {
		return issueId;
	}

	public void setIssueId(SimpleIntegerProperty issueId) {
		this.issueId = issueId;
	}

	public SimpleStringProperty getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(SimpleStringProperty issueTitle) {
		this.issueTitle = issueTitle;
	}

}
