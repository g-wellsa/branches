package com.example.transfer.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.example.helper.Logger;

/**
 * 
 * @author Administrator
 * 
 */
public class CopyOfTransferClientThread implements Runnable {
	
	private int blockIndex; // 文件块索引跟访问令牌
	private File uploadFile;
	private SocketChannel socketChannel;
	private SyncTransferClient syncTClient;
	private AsyncTransferClient asyncTClient;
	
	private StringBuffer fileBuffer;
	private ByteBuffer sendBuffer;
	private Socket socket;

	public CopyOfTransferClientThread(AsyncTransferClient asyncTClient, SocketChannel socketChannel,
			File uploadFile) {
		this.uploadFile=uploadFile;
		this.asyncTClient = asyncTClient;
		this.fileBuffer=new StringBuffer();
		this.socketChannel = socketChannel;
		this.sendBuffer=ByteBuffer.allocate(1040);
	}

	public CopyOfTransferClientThread(SyncTransferClient syncTClient, Socket socket,
			TransferEntity transferEntity) {
		this.socket = socket;
		this.syncTClient = syncTClient;
	}

	public void run() {
		if (socket != null) {
			syncUploadFile(uploadFile);
		} else {
			asyncUploadFile(uploadFile);
		}
	}

	private void asyncUploadFile(File uploadFile) {
		try {
			int blockSize=0;
			FileInputStream fileStream = new FileInputStream(uploadFile); // 包装一个文件输出流
			byte[] blockData = new byte[1024]; // 定义一个文件块(字节数组)
			while (true) {// 循环读取文件
				byte[] dataBytes=TransferHelper.getFileData(uploadFile.getAbsolutePath().hashCode(),
						uploadFile.getAbsolutePath().getBytes("utf-8"));
//				Logger.println(this, "asyncUploadFile~",blockIndex+"/ "+blockSize+" / "+blockData);
				sendBuffer.clear();
				sendBuffer.put(dataBytes);
				sendBuffer.flip();
//				while ( (blockSize -= this.socketChannel.write(sendBuffer)) > 0) {}
				while (sendBuffer.hasRemaining()) {
					socketChannel.write(sendBuffer);
				}
				sendBuffer.compact();
				blockIndex++;
			}
//			fileStream.close();
//			fileStream=null;
//			Logger.println(this, "asyncUploadFile~", "上传文件成功!");
		} catch (Exception err) {
			Logger.println(this, "asyncUploadFile err " + err);
			asyncTClient.onState(-1, "上传文件失败!");
		}
	}

	/**
	 * 同步上传文件
	 */
	private void syncUploadFile(File uploadFile) {
		try {
			DataInputStream diStream = new DataInputStream(new BufferedInputStream(
					new FileInputStream(uploadFile)));
			DataOutputStream doStream = new DataOutputStream(
					socket.getOutputStream());

			doStream.writeUTF(uploadFile.getName());
			doStream.flush();
			doStream.writeLong(uploadFile.length());
			doStream.flush();

			int read = 0;
			int passedlen = 0;
			long length = uploadFile.length(); // 获得要发送文件的长度
			byte[] fileBuffer = new byte[1024*8];
			while ((read = diStream.read(fileBuffer)) != -1) {
				passedlen += read;
				Logger.println(this, "run 已经完成文件 [" + uploadFile.getName()
						+ "]百分比: " + passedlen * 100L / length + "%");
				doStream.write(fileBuffer, 0, read);
			}

			doStream.flush();
			diStream.close();
			doStream.close();
			socket.close();
			Logger.println(this, "文件 " + uploadFile.getAbsolutePath() + "传输完成!");
		} catch (Exception e) {
			e.printStackTrace();
			syncTClient.onState(-1, "同步上传文件失败!");
		}
		finally{
			
		}
	}
}
