package com.example.client.listener;

import java.nio.channels.SelectionKey;

public interface ISocketListener {
	public void isAcceptable(SelectionKey selectionKey);
	public void isWritable(SelectionKey key);
	public void isReadable(SelectionKey key);
}
