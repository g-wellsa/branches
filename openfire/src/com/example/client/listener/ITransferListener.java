package com.example.client.listener;



public interface ITransferListener {
	public void uploadFile(boolean async,String filePath);
	public void downloadFile(boolean async,String fileName);
}
