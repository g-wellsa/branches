package com.example.transfer.server;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.example.helper.Logger;

public class Writer implements IUploadListener {
	private ByteBuffer output;

	public Writer(ByteBuffer output) {
		this.output = output;
		Logger.println(this, "construct");
	}

	public void execute(SelectionKey key) {
		Logger.println(this, "execute");
		SocketChannel sc = (SocketChannel) key.channel();
		try {
			while (sc.isConnected() && output.hasRemaining()) {
				int len = sc.write(output);
				if (len < 0) {
					throw new EOFException();
				}
				if (len == 0) {
					key.interestOps(SelectionKey.OP_WRITE);
					key.selector().wakeup();
					break;
				}
			}
			if (!output.hasRemaining()) {
				output.clear();
				key.cancel();
				sc.close();
			}
		} catch (IOException e) {
		}
	}
}
