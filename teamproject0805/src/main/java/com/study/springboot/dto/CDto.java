package com.study.springboot.dto;

import java.sql.Timestamp;

public class CDto {
	
	private int qId;
	private String qName;
	private String qkind;
	private String qTitle;
	private String qContent;
	private String cPhoto1;
	private String cPhoto2;
	private Timestamp qDate;
	private String anwser;
	private int qStep;
	private int qIndent;
	private int qGroup;
	
	public CDto() { }
	
	public CDto(int qId, String qName, String qkind, String qTitle, String qContent, String cPhoto1, String cPhoto2,
			Timestamp qDate, String anwser, int qStep, int qIndent, int qGroup) {
		super();
		this.qId = qId;
		this.qName = qName;
		this.qkind = qkind;
		this.qTitle = qTitle;
		this.qContent = qContent;
		this.cPhoto1 = cPhoto1;
		this.cPhoto2 = cPhoto2;
		this.qDate = qDate;
		this.anwser = anwser;
		this.qStep = qStep;
		this.qIndent = qIndent;
		this.qGroup = qGroup;
	}

	public int getqId() {
		return qId;
	}

	public void setqId(int qId) {
		this.qId = qId;
	}

	public String getqName() {
		return qName;
	}

	public void setqName(String qName) {
		this.qName = qName;
	}

	public String getQkind() {
		return qkind;
	}

	public void setQkind(String qkind) {
		this.qkind = qkind;
	}

	public String getqTitle() {
		return qTitle;
	}

	public void setqTitle(String qTitle) {
		this.qTitle = qTitle;
	}

	public String getqContent() {
		return qContent;
	}

	public void setqContent(String qContent) {
		this.qContent = qContent;
	}

	public String getcPhoto1() {
		return cPhoto1;
	}

	public void setcPhoto1(String cPhoto1) {
		this.cPhoto1 = cPhoto1;
	}

	public String getcPhoto2() {
		return cPhoto2;
	}

	public void setcPhoto2(String cPhoto2) {
		this.cPhoto2 = cPhoto2;
	}

	public Timestamp getqDate() {
		return qDate;
	}

	public void setqDate(Timestamp qDate) {
		this.qDate = qDate;
	}

	public String getAnwser() {
		return anwser;
	}

	public void setAnwser(String anwser) {
		this.anwser = anwser;
	}

	public int getqStep() {
		return qStep;
	}

	public void setqStep(int qStep) {
		this.qStep = qStep;
	}

	public int getqIndent() {
		return qIndent;
	}

	public void setqIndent(int qIndent) {
		this.qIndent = qIndent;
	}

	public int getqGroup() {
		return qGroup;
	}

	public void setqGroup(int qGroup) {
		this.qGroup = qGroup;
	}
}
