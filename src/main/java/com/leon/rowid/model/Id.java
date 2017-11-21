package com.leon.rowid.model;

import java.util.List;

public class Id {
	
	private String idType;
	
	private List<String> generateId;
	
	private String currentVal;

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public List<String> getGenerateId() {
		return generateId;
	}

	public void setGenerateId(List<String> generateId) {
		this.generateId = generateId;
	}

	public String getCurrentVal() {
		return currentVal;
	}

	public void setCurrentVal(String currentVal) {
		this.currentVal = currentVal;
	}

}
