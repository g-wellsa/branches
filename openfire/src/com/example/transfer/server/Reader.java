package com.example.transfer.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.util.concurrent.ExecutorService;

import com.example.constant.Constants;
import com.example.helper.Logger;

public class Reader implements IUploadListener {
	private ExecutorService executor;
	private byte[] byteContents;

	public Reader(ExecutorService executor) {
		this.executor = executor;
		Logger.println(this, "construct");
	}

	@Override
	public void execute(SelectionKey key) {
		// Logger.println(this, "execute",key);
		SocketChannel sChannel = (SocketChannel) key.channel();
		try {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int len = -1;
			int command = 0;
			while (sChannel.isConnected()
					&& (len = sChannel.read(buffer))>0) {
				buffer.flip();
				// byteContents= new byte[buffer.limit()];
				command = buffer.getInt();
				// Logger.println(this, "execute while",command);
				buffer.clear();
				len=-1;
			}
//			Logger.println(this, "execute", len);
			if (len == 0) {
				key.interestOps(SelectionKey.OP_READ);
				key.selector().wakeup();
			} else if (len == -1) {
				// Callable<byte[]> call=new ProcessCallable(bytes);
				// Future<byte[]> task=executor.submit(call);
				ByteBuffer sendBuffer = ByteBuffer.allocate(4);
				sendBuffer.putInt(command);
				sChannel.register(key.selector(), SelectionKey.OP_WRITE,
						new Writer(sendBuffer));
				Logger.println(this, "execute -1", new String(byteContents));
			}
		} catch (Exception e) {
		}
	}

	private void handleRespone(SocketChannel socketChannel, int command,
			Object obj) {
		byte[] sendBytes;
		ByteBuffer sendBuffer = null;
		try {
			switch (command) {
			case Constants.CMD_CREATE_FILE_SUCCESS:
				sendBytes = (byte[]) obj;
				sendBuffer = ByteBuffer.allocate(8 + sendBytes.length);
				sendBuffer.putInt(command);
				sendBuffer.putInt(sendBytes.length);
				sendBuffer.put(sendBytes);
				break;
			case Constants.CMD_CREATE_FILE_FAILURE:
				sendBytes = obj.toString().getBytes("GB2312");

				sendBuffer = ByteBuffer.allocate(8 + sendBytes.length);
				sendBuffer.putInt(command);
				sendBuffer.putInt(sendBytes.length);
				sendBuffer.put(sendBytes);
				break;
			case Constants.CMD_TRANSFER_FILE_ERROR:
				sendBytes = obj.toString().getBytes("GB2312");
				sendBuffer = ByteBuffer.allocate(8 + sendBytes.length);
				sendBuffer.putInt(command);
				sendBuffer.putInt(sendBytes.length);
				sendBuffer.put(sendBytes);
				break;
			default:
				break;
			}
			if (sendBuffer != null) {
				handleWrite(socketChannel, sendBuffer);
			}
			sendBytes = null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向客户端发送信息
	 * 
	 * @param key
	 */
	public void handleWrite(SocketChannel socketChannel, ByteBuffer byteBuffer) {
		Logger.println(this, "handleWrite", byteBuffer);
		try {
			byteBuffer.flip();
			while (byteBuffer.hasRemaining()) {
				socketChannel.write(byteBuffer);
			}
			// byteBuffer.compact();
		} catch (CharacterCodingException e) {
			e.printStackTrace();
			Logger.println(this, "handleWrite CharacterCodingException", e);
		} catch (IOException e) {
			e.printStackTrace();
			Logger.println(this, "handleWrite IOException", e);
		}

	}

}
