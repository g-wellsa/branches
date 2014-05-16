package com.example.transfer.server;



public class TransferManager {

	private static TransferManager transferManager;
	private AsyncTransferServer asyncServer;
	private SyncTransferServer syncServer;

	public static TransferManager getInstance() {
		if (transferManager == null) {
			transferManager = new TransferManager();
		}
		return transferManager;
	}

	public TransferManager() {
//		syncServer = new SyncTransferServer();
		asyncServer = new AsyncTransferServer(this);
	}
	
	public static void main(String args[]){
		TransferManager.getInstance();
	}
}