package org.tedros.redminetools.gateway;

import java.util.List;

import org.tedros.common.model.TFileContentInfo;
import org.tedros.redminetools.model.TAttachment;
import org.tedros.redminetools.model.TIssueEvidenceInfo;

public record IssueRelevantInfo(TIssueEvidenceInfo info, List<TAttachment> attachments) {

}
