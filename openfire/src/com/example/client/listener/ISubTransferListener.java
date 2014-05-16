package com.example.client.listener;



public interface ISubTransferListener {
	public void progress(int pid,int progress);
	public void onState(int state,Object obj);
}
