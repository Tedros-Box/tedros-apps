package org.tedros.it.tools.module.gmud.builder;

import org.tedros.core.TLanguage;
import org.tedros.fx.builder.TBaseEventHandlerBuilder;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.module.gmud.model.GmudIssueReferenceMV;
import org.tedros.it.tools.redmine.ai.function.RedmineApiPropertyUtil;
import org.tedros.it.tools.redmine.api.model.TIssue;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TLoggerUtil;

import com.taskadapter.redmineapi.NotFoundException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class GetRedmineIssueActionBuilder extends TBaseEventHandlerBuilder<ActionEvent>{
	
	private static final RedmineApiGateway gateway;
	
	static {
		gateway = new RedmineApiGateway(
                RedmineApiPropertyUtil.getInstance().getRedmineUrl(),
                RedmineApiPropertyUtil.getInstance().getRedmineKey()
            );
	}

	@Override
	public EventHandler<ActionEvent> build() {
		return e -> {
			GmudIssueReferenceMV mv = (GmudIssueReferenceMV) super.getComponentDescriptor().getModelView();
			if(mv!=null) {
				try {
					int redmineId = mv.getIssueId().get();
					TIssue issue = gateway.getIssuesById(redmineId);
					if(issue!=null) {
						mv.getIssueTitle().set(issue.getSubject());
					}
				} catch (Exception e1) {
					TLanguage lang = TLanguage.getInstance();
					if(e1.getCause() instanceof NotFoundException) {
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle(lang.getString(ItToolsKey.TITLE_ALERT_REDMINE));
						alert.setContentText(lang.getString(ItToolsKey.TEXT_ALERT_ISSUE_NOT_FOUND));
						alert.showAndWait();
					}else {
						TLoggerUtil.error(GetRedmineIssueActionBuilder.class, "Error getting Redmine issue", e1);
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle(lang.getString(ItToolsKey.TITLE_ALERT_REDMINE));
						alert.setHeaderText(lang.getString(ItToolsKey.TEXT_ALERT_ERROR_HEADER));
						alert.setContentText(e1.getMessage());
						alert.showAndWait();						
					}
				}
			}
		};
	}

}
