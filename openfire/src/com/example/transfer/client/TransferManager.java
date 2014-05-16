package com.example.transfer.client;

import java.io.File;

import com.example.client.listener.IProgressListener;
import com.example.client.listener.ITransferListener;
import com.example.helper.Logger;

public class TransferManager implements ITransferListener,IProgressListener {
	public static final int FILE_BLOCK_SIZE = 1024; // /规定文件块大小为1024

	private static TransferManager transferManager;
	private AsyncTransferClient asyncTClient;
	private SyncTransferClient syncTClient;

	public static TransferManager getInstance() {
		if (transferManager == null) {
			transferManager = new TransferManager();
		}
		return transferManager;
	}

	public TransferManager() {
//		syncTClient = new SyncTransferClient(this);
		asyncTClient = new AsyncTransferClient(this);
//		asyncTClient.startTransferListener();
	}

	public void uploadFile(boolean async,String filePath) {
		Logger.println(this, "uploadFile", async+"/"+filePath);
		if (async)
			asyncTClient.uploadFile(filePath);
//		else
//			syncTClient.uploadFile(filePath);
	}

	public void downloadFile(boolean async, String fileName) {
		Logger.println(this, "downloadFile", async + "/" + fileName);
		// if (async)
		// asyncTClient.downloadFile(fileName);
		// else
		// syncTClient.downloadFile(fileName);
	}

	/**
	 * 传输进度回调
	 */
	public void progress(int pid, int progress) {
		Logger.println(this, "progress", pid + "/" + progress);
	}

	/**
	 * 传输状态回调
	 */
	public void onState(int state, Object obj) {
		Logger.println(this, "onState", state + "/" + obj);
	}

	public static void main(String args[]){
		TransferManager manager=TransferManager.getInstance();
		File[] files=new File("d:\\upload").listFiles();
		for(File file:files){
			manager.uploadFile(true, file.getAbsolutePath());
		}
	}
}
