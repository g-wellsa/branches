package com.example.server.listener;


public interface ITransferListener {
	public void progress(int pid,int progress);
	public void onState(int state,Object obj);
}
