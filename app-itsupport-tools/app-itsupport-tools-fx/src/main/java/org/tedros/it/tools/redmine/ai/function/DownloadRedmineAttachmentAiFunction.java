package org.tedros.it.tools.redmine.ai.function;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.common.model.TFileContentInfo;
import org.tedros.it.tools.redmine.api.model.TAttachment;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TLoggerUtil;

public class DownloadRedmineAttachmentAiFunction extends TFunction<TAttachment> {
    
    private static final Logger LOGGER = TLoggerUtil.getLogger(DownloadRedmineAttachmentAiFunction.class);

    public static final String NAME = "download_redmine_attachment";
    public static final String DESCRIPTION = """
    Call this function when the user wants to read, analyze, or view the content of a specific attached file.
    Only use AFTER get_redmine_issue has returned the attachment list.
    Required fields in input:
      • id (integer) – attachment ID
      • contentURL (string) – direct download URL
    Preferred: also send fileName and contentType for better context.
    This function downloads the actual file and returns its content as bytes (text, PDF, image, doc, etc.).
    Never hallucinate file content — always call this function first.
    """;

    public DownloadRedmineAttachmentAiFunction() {
        super(NAME, DESCRIPTION, TAttachment.class, attachment -> {
        	
            try {
                if (attachment.getId() == null || attachment.getContentURL() == null || attachment.getContentURL().isBlank()) {
                    return ToolCallResult.builder()
							.message("Error: Attachment missing required fields (id or contentURL). Cannot download.")
							.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "missing_fields",
			                    ERROR_MESSAGE, "Attachment missing required fields (id or contentURL). Cannot download."
			                ))
							.build();
                    
                }

                LOGGER.info("Downloading attachment #{} – {} ({})", 
                            attachment.getId(), attachment.getFileName(), attachment.getContentType());

                var gateway = new RedmineApiGateway(
                    RedmineApiPropertyUtil.getInstance().getRedmineUrl(),
                    RedmineApiPropertyUtil.getInstance().getRedmineKey()
                );

                // gateway já lida com token/auth se necessário
                List<TFileContentInfo> downloaded = gateway.dowloadTAttachments(List.of(attachment));

                if (downloaded == null || downloaded.isEmpty()) {
                	return ToolCallResult.builder()
							.message("Error: No data found for the requested attachment.")
							.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "no_data_found",
			                    ERROR_MESSAGE, "No data found for the requested attachment."
			                ))
							.build();
                }

                TFileContentInfo file = downloaded.get(0);
                LOGGER.info("Attachment downloaded: {} ({} bytes)", file.fileName(), file.bytes().length);

                return ToolCallResult.builder()
                		.message("Attachment downloaded successfully.")
                		.result(Map.of(
		                    STATUS, SUCCESS,
		                    ACTION, "attachment_downloaded",
		                    SYSTEM_INSTRUCTION, "Attachment downloaded successfully. "
		                    		+ "Do not retry again. Inform the user to check the downloaded file."
		                ))
                		.filesContentInfo(file)
                		.build();                

            } catch (Exception e) {
                LOGGER.error("Download failed for attachment #{} ({}): {}", 
                            attachment.getId(), attachment.getFileName(), e.getMessage(), e);
                return ToolCallResult.builder()
                		.message("Error downloading attachment: " + e.getMessage())
                		.result(Map.of(
		                    STATUS, ERROR,
		                    ACTION, "attachment_download_failed",
		                    ERROR_MESSAGE, "Error downloading attachment: " + e.getMessage()
		                ))
                		.build();
            }
        });
    }
}