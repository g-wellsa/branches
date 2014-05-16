package com.example.transfer.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.constant.Constants;
import com.example.helper.Logger;

public class SyncTransferServer {
	private ExecutorService executorService; // 线程池
	private ServerSocket serverSocket; // 服务端套接字
	private int tryBindCount = 0; // 绑定端口的次数设定为0

	public SyncTransferServer() {
		try {
			initServerSocket(Constants.SERVER_FILE_PORT);
		} catch (Exception e) {
			Logger.println(this, "绑定端口失败!");
		}
	}

	public SyncTransferServer(int port) {
		try {
			initServerSocket(port);
		} catch (Exception e) {
			Logger.println(this, "绑定端口失败!");
		}
	}

	private void initServerSocket(int port) {
		try {
			serverSocket = new ServerSocket(port);
			executorService = Executors.newCachedThreadPool();
			Logger.println(this, "服务启动成功!",tryBindCount);
			this.tryBindCount=0;
		} catch (Exception e) {
			this.tryBindCount++;
			port = port + this.tryBindCount;
			if (this.tryBindCount <= Constants.SERVER_CONN_COUNT) {
				// 递归绑定端口
				initServerSocket(port);
				return;
			}
		}
	}

	private void startListener() {
		Logger.println(this, "startListener");
		while (true) {
			try {
				final Socket socket = serverSocket.accept();
				handleAccept(socket);
			} catch (Exception e) {
				e.printStackTrace();
				Logger.println(this, "startListener err",e);
			}
		}
	}
	
	/**
	 * 处理客户端请求
	 * @param socket
	 */
	private void handleAccept(Socket socket){
		Logger.println(this, "handleAccept", socket);
		executorService.execute(new TransferServerThread(
				SyncTransferServer.this, socket));
	}

	public static void main(String[] args) throws Exception {
		new SyncTransferServer().startListener();
	}
}
