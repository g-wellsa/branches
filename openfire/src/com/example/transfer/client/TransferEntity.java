package com.example.transfer.client;

import java.io.File;
import java.io.Serializable;

public class TransferEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private int fileToken;
	private File uploadFile;

	public int getFileToken() {
		return fileToken;
	}

	public void setFileToken(int fileToken) {
		this.fileToken = fileToken;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	@Override
	public String toString() {
		return "TransferEntity [fileToken=" + fileToken + ", uploadFile="
				+ uploadFile + "]";
	}

}
