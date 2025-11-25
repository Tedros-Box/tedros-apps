package org.tedros.extension.ai.model;

import org.tedros.extension.ai.function.DownloadFileFunction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonClassDescription("File information")
public class FileInfo {
	
	@JsonPropertyDescription("the file id, use this id to download the file from function "+DownloadFileFunction.NAME)
	private Long id;

	@JsonPropertyDescription("the file name")
	private String fileName;
	
	@JsonPropertyDescription("the file size in bytes")
	private Long fileSize;
	
	@JsonPropertyDescription("the file extension")
	private String fileExtension;
	
	@JsonPropertyDescription("the file owner")
	private String owner;
}
