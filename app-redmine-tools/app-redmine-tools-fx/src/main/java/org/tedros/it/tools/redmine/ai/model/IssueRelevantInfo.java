package org.tedros.it.tools.redmine.ai.model;

import java.util.List;

import org.tedros.common.model.TFileContentInfo;
import org.tedros.it.tools.redmine.api.model.TAttachment;
import org.tedros.it.tools.redmine.api.model.TIssueEvidenceInfo;

public record IssueRelevantInfo(TIssueEvidenceInfo info, List<TAttachment> attachments) {

}
