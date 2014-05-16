package com.example.transfer.server;

import java.nio.channels.SelectionKey;

public interface IUploadListener {
	void execute(SelectionKey key);
}
