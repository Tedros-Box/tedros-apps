package org.tedros.it.tools.module.evidence.trigger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.tedros.fx.control.TTextAreaField;
import org.tedros.fx.control.trigger.TTrigger;
import org.tedros.fx.form.TFieldBox;
import org.tedros.it.tools.redmine.ai.function.RedmineApiPropertyUtil;
import org.tedros.it.tools.redmine.api.model.TIssueEvidenceInfo;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TLoggerUtil;

public class SearchForIssueTrigger extends TTrigger<String> {
	
	private static final Logger log = TLoggerUtil.getLogger(SearchForIssueTrigger.class);
	
	public SearchForIssueTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@Override
	public void run(TEvent event, String newValue, String oldValue) {
		
		if(event.equals(TEvent.ADDED) && StringUtils.isNotBlank(newValue)) {
			RedmineApiPropertyUtil util = RedmineApiPropertyUtil.getInstance();
			RedmineApiGateway gateway = new RedmineApiGateway(util.getRedmineUrl(), util.getRedmineKey());
			
			try {
				TIssueEvidenceInfo issue = gateway.getTIssueEvidenceInfo(Integer.valueOf(newValue));
				fillFields(issue.getSubject(), util.getRedmineUrl()+"/issues/"+newValue);
			}catch (Exception e) {
				log.warn("Problem occurred to get the Redmine issue {} : {}", newValue, e.getMessage());
			}
		}
		
		if(event.equals(TEvent.ADDED) && StringUtils.isBlank(newValue)) {
			fillFields(null, null);
		}

	}

	private void fillFields(String title, String link) {
		TTextAreaField issueTitle = (TTextAreaField) super.getTarget().gettControl();
		issueTitle.setText(title);
		
		TTextAreaField issueLink = (TTextAreaField) super.getAssociatedFieldBox("issueLink").gettControl();
		issueLink.setText(link);
	}

}
