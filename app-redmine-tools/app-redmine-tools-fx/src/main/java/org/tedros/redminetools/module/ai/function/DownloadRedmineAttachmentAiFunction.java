package org.tedros.redminetools.module.ai.function;

import java.util.List;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.common.model.TFileContentInfo;
import org.tedros.redminetools.gateway.RedmineApiGateway;
import org.tedros.redminetools.model.TAttachment;
import org.tedros.util.TLoggerUtil;

public class DownloadRedmineAttachmentAiFunction extends TFunction<TAttachment> {
    
    private static final Logger LOGGER = TLoggerUtil.getLogger(DownloadRedmineAttachmentAiFunction.class);

    private static final String FUNCTION_NAME = "download_redmine_attachment";

    private static final String PROMPT = """
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
        super(FUNCTION_NAME, PROMPT, TAttachment.class, attachment -> {
            try {
                if (attachment.getId() == null || attachment.getContentURL() == null || attachment.getContentURL().isBlank()) {
                    return new Response("Error: Attachment missing required fields (id or contentURL). Cannot download.");
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
                    return new Response("Failed to download file: " + attachment.getFileName());
                }

                TFileContentInfo file = downloaded.get(0);
                LOGGER.info("Attachment downloaded: {} ({} bytes)", file.fileName(), file.bytes().length);

                return new ToolCallResult(FUNCTION_NAME, List.of(file));

            } catch (Exception e) {
                LOGGER.error("Download failed for attachment #{} ({}): {}", 
                            attachment.getId(), attachment.getFileName(), e.getMessage(), e);
                return new Response("Download error: " + e.getMessage());
            }
        });
    }
}