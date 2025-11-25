package org.tedros.extension.ai.function;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.common.domain.MimeType;
import org.tedros.common.model.TFileContentInfo;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TFileEntityController;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.extension.ai.model.FileParam;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

public class DownloadFileFunction extends TFunction<FileParam> {

	public static final String NAME = "download_tedros_file";
	private static final String DESCRIPTION = "Download a file saved on Tedros system by file id";
	
	public DownloadFileFunction() {
		super(NAME, DESCRIPTION, FileParam.class, v-> {
			TEjbServiceLocator locator = TEjbServiceLocator.getInstance();
			
			try {
				
				TFileEntity entity = new TFileEntity();
				entity.setId(v.id());
				
				TFileEntityController fileService =locator.lookup(TFileEntityController.JNDI_NAME);
				TResult<TFileEntity> fileResult = fileService.findByIdWithBytesLoaded(TedrosContext.getLoggedUser().getAccessToken(), entity);
				if(fileResult.getState().equals(TState.SUCCESS)) {
					entity = fileResult.getValue();
					return new TFileContentInfo(entity.getFileName(), 
							MimeType.fromExtension(entity.getFileExtension()).getMimeType(), 
							entity.getByteEntity().getBytes());
				}
				
			} catch (Exception e) {
				return new Response("Error: " + e.getMessage());
			}finally {
				locator.close();
			}
			
			return new Response("File not found");
		});
	}
}
