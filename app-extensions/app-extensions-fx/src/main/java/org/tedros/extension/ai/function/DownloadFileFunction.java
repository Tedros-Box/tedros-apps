package org.tedros.extension.ai.function;

import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.common.domain.MimeType;
import org.tedros.common.model.TFileContentInfo;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TFileEntityController;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.extension.ai.model.FileParam;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;

public class DownloadFileFunction extends TFunction<FileParam> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(DownloadFileFunction.class);

	public static final String NAME = "download_tedros_file";
	public static final String DESCRIPTION = "Retrieves and reads the content of a specific file from the Tedros system database. " +
            "Use this tool when the user asks to read, analyze, or download a document. " +
            "CRITICAL: Requires a known numeric 'id' (from a previous search result). Do NOT guess or fabricate the ID.";
	
	public DownloadFileFunction() {
		super(NAME, DESCRIPTION, FileParam.class, v-> {
			
			LOGGER.info("Downloading file with id: {}", v.id());
			
			try(TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
				
				TFileEntity entity = new TFileEntity();
				entity.setId(v.id());
				
				TFileEntityController fileService =locator.lookup(TFileEntityController.JNDI_NAME);
				TResult<TFileEntity> fileResult = fileService.findByIdWithBytesLoaded(TedrosContext.getLoggedUser().getAccessToken(), entity);
				if(fileResult.getState().equals(TState.SUCCESS)) {
					entity = fileResult.getValue();
					
					return ToolCallResult.builder()
							.message("File downloaded successfully.")
							.result(Map.of(
				                    STATUS, SUCCESS,
				                    ACTION, "file_downloaded",
				                    SYSTEM_INSTRUCTION, "The system has downloaded the file successfully. "
					                    	+ "Do not retry again. Inform the user to check the downloaded file."
				                ))
							.filesContentInfo(new TFileContentInfo(
											entity.getFileName(), 
											MimeType.fromExtension(entity.getFileExtension()).getMimeType(), 
											entity.getByteEntity().getBytes()))
							.build();
				}
				
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return ToolCallResult.builder()
						.message("Error downloading file: " + e.getMessage())
						.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "file_download_failed",
			                    ERROR_MESSAGE, "Error downloading file: " + e.getMessage()
			                ))
						.build();
			}
			
			return ToolCallResult.builder()
					.message("File not found.")
					.result(Map.of(
		                    STATUS, ERROR,
		                    ACTION, "file_not_found",
		                    ERROR_MESSAGE, "File not found with the provided ID."
		                ))
					.build();
			
		});
	}
}
