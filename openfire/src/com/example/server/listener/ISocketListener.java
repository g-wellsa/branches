package com.example.server.listener;

import java.nio.channels.SelectionKey;

public interface ISocketListener {
	public void handleAccept(SelectionKey key);
	public void handleWrite(SelectionKey key);
	public void handleRead(SelectionKey key);
}
