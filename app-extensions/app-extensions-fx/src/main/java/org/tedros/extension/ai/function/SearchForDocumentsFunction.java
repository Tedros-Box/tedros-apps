package org.tedros.extension.ai.function;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.extension.ai.model.DocumentInfo;
import org.tedros.extension.ai.model.DocumentSearchParam;
import org.tedros.extension.ai.model.DocumentTypeInfo;
import org.tedros.extension.ai.model.DomainInfo;
import org.tedros.extension.ai.model.FileInfo;
import org.tedros.extension.ejb.controller.IDocumentController;
import org.tedros.extension.model.Document;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TJoinType;
import org.tedros.server.query.TLogicOp;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;

public class SearchForDocumentsFunction extends TFunction<DocumentSearchParam> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchForDocumentsFunction.class);

	public static final String NAME = "search_for_documents";
	public static final String DESCRIPTION = "Search for documents based on given parameters";
	
	public SearchForDocumentsFunction() {
		super(NAME, DESCRIPTION, DocumentSearchParam.class, v-> {
			
			LOGGER.info("Searching for documents with parameters: {}", v);
			
			try(TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
				
				TSelect<Document> select = new TSelect<>(Document.class, "doc");
				select.addJoin(TJoinType.INNER, "doc", "type", "tp");
				select.addJoin(TJoinType.INNER, "doc", "status", "st");
				select.addJoin(TJoinType.INNER, "doc", "file", "f");
				
				if(v.getId() != null) {
					select.addCondition(TLogicOp.AND, "doc", "id", TCompareOp.EQUAL, v.getId());
				}
				
				if(StringUtils.isNotBlank(v.getCode())) {
					select.addCondition(TLogicOp.AND, "doc", "code", TCompareOp.EQUAL, v.getCode());
				}
				
				if(StringUtils.isNotBlank(v.getName())) {
					select.addCondition(TLogicOp.AND, "doc", "name", TCompareOp.LIKE, v.getName());
				}
				
				IDocumentController service = locator.lookup(IDocumentController.JNDI_NAME);
				TResult<List<Document>> result = service.search(TedrosContext.getLoggedUser().getAccessToken(), select);
				
				if(result.getState().equals(TState.SUCCESS)) {
					List<Document> lst = result.getValue();
					if(lst!=null && !lst.isEmpty()) {
						List<DocumentInfo> docs = lst.stream().map(SearchForDocumentsFunction::convert).toList();
						return ToolCallResult.builder()
								.message("Documents found successfully.")
								.result(Map.of(
					                    STATUS, SUCCESS,
					                    ACTION, "documents_found",
					                    SYSTEM_INSTRUCTION, "Documents found successfully. "
					                    		+ "Do not retry again. Proceed with the user's request.",
					                    "documents", docs
					                ))
								.build();
					}
				}
				
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return ToolCallResult.builder()
						.message("Error searching for documents: " + e.getMessage())
						.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "document_search_error",
			                    ERROR_MESSAGE, e.getMessage()
			                ))
						.build();
			}
			
			return ToolCallResult.builder()
					.message("No documents found matching the criteria.")
					.result(Map.of(
		                    STATUS, ERROR,
		                    ACTION, "no_documents_found",
		                    ERROR_MESSAGE, "No documents found matching the criteria."
		                ))
					.build();
		});
		
	}

	private static DocumentInfo convert(Document doc) {
		DocumentTypeInfo typeInfo = null;
		if(doc.getType()!=null) {
			typeInfo = DocumentTypeInfo.builder()
					.documentType(doc.getType().getDocType())
					.build();
			typeInfo.setName(doc.getType().getName());
			typeInfo.setCode(doc.getType().getCode());
			typeInfo.setDescription(doc.getType().getDescription());
		}
		
		DomainInfo statusInfo = doc.getStatus()!=null ? 
				new DomainInfo(doc.getStatus().getCode(), 
						doc.getStatus().getName(), 
						doc.getStatus().getDescription())
				: null;
		
		FileInfo fileInfo = null;
		if(doc.getFile()!=null) {
			fileInfo = FileInfo.builder()
					.id(doc.getFile().getId())
					.fileName(doc.getFile().getFileName())
					.fileSize(doc.getFile().getFileSize())
					.fileExtension(doc.getFile().getFileExtension())
					.owner(doc.getFile().getOwner())
					.build();
		}
		
		return DocumentInfo.builder().id(doc.getId())
				.code(doc.getCode())
				.name(doc.getName())
				.type(typeInfo)
				.status(statusInfo)
				.observation(doc.getObservation())
				.file(fileInfo)
				.build();
	}

}
