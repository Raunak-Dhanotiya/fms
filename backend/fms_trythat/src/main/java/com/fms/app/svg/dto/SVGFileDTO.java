package com.fms.app.svg.dto;

public class SVGFileDTO {
	private String fileName;
	private String fileContent;
	
	public SVGFileDTO(String fileName, String fileContent) {
		super();
		this.fileName = fileName;
		this.fileContent = fileContent;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
}
