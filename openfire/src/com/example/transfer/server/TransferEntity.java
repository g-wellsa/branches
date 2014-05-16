package com.example.transfer.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class TransferEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private int fileSize;
	private int blockCount;
	private int receivedLen;
	private File tempFile;
	private FileOutputStream fOStream;
	private RandomAccessFile accessFile;

	public synchronized int getFileSize() {
		return fileSize;
	}

	public synchronized void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	

	public int getReceivedLen() {
		return receivedLen;
	}

	public void setReceivedLen(int receivedLen) {
		this.receivedLen = receivedLen;
	}

	public File getTempFile() {
		return tempFile;
	}

	public void setTempFile(File tempFile) {
		this.tempFile = tempFile;
	}

	public FileOutputStream getfOStream() {
		return fOStream;
	}

	public void setfOStream(FileOutputStream fOStream) {
		this.fOStream = fOStream;
	}

	public RandomAccessFile getAccessFile() {
		return accessFile;
	}

	public void setAccessFile(RandomAccessFile accessFile) {
		this.accessFile = accessFile;
	}

	public int getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}

	@Override
	public String toString() {
		return "TransferEntity [fileSize=" + fileSize + ", blockCount="
				+ blockCount + ", receivedLen=" + receivedLen + ", tempFile="
				+ tempFile + ", fOStream=" + fOStream + ", accessFile="
				+ accessFile + "]";
	}

	public void destory(){
		try {
			if (fOStream!=null) {
				fOStream.close();
			}
			fOStream=null;
			if (accessFile!=null) {
				accessFile.close();
			}
			accessFile=null;
			if (tempFile!=null && tempFile.exists()) {
				tempFile.delete();
			}
			tempFile=null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

}
