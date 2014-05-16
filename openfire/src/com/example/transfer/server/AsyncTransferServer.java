package com.example.transfer.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.constant.Constants;
import com.example.helper.Logger;
import com.example.server.listener.ITransferListener;

public class AsyncTransferServer implements ITransferListener {
	private Hashtable<Integer, TransferEntity> transferTables;
	private TransferManager transferManager;
	private ServerSocketChannel serverSocket;
	private ExecutorService executorService;
	private Selector selector;

	public AsyncTransferServer(TransferManager transferManager) {
		this.transferManager = transferManager;
		initTransferServer();
	}

	private void initTransferVaribles() {
		executorService = Executors
				.newFixedThreadPool(Constants.THREAD_POOL_COUNT);
		transferTables = new Hashtable<Integer, TransferEntity>();
	}

	private void initTransferServer() {
		Logger.println(this, "initTransferServer");
		try {
			serverSocket = ServerSocketChannel.open();
			serverSocket.socket().bind(
					new InetSocketAddress(Constants.SERVER_FILE_PORT));
			serverSocket.configureBlocking(false);
			selector = Selector.open();
			serverSocket.register(selector, SelectionKey.OP_ACCEPT);
			initTransferVaribles();
			transferListener();
		} catch (IOException e) {
			e.printStackTrace();
			Logger.println(this, "initTransferServer err", e);
		}
	}

	public TransferEntity getTransferEntity(int fileToken) {
		return transferTables.get(fileToken);
	}

	/**
	 * 服务器端口监听
	 * 
	 * @throws IOException
	 */
	public void transferListener() throws IOException {
		Logger.println(this, "transferListener");
		new Thread(new Runnable() {

			public void run() {
				try {
					while (true) {
						int select = selector.select();
						if (select == 0) {
							continue;
						}
						Iterator<?> iterator = selector.selectedKeys()
								.iterator();
						while (iterator.hasNext()) {
							SelectionKey selectionKey = (SelectionKey) iterator
									.next();
							iterator.remove(); // 删除此消息
							if (selectionKey.isAcceptable()) {// 监听注册
								handleAccept(selectionKey);
							} 
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					Logger.println(this, "transferListener err", e);
				}
			}
		}).start();
	}

	public void handleAccept(SelectionKey key) {
		Logger.println(this, "handleAccept", key);
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		try {
			SocketChannel channel = server.accept();
			// 设置非阻塞模式(异步)
			channel.configureBlocking(false);
			channel.register(selector, SelectionKey.OP_READ);
			executorService.execute(new TransferServerThread(this,channel));
		} catch (IOException e) {
			e.printStackTrace();
			Logger.println(this, "handleAccept err", e);
		}
	}

	public void putTransferEntity(int token, String fileName, int fileSize,File tempFile,
			FileOutputStream fOStream, RandomAccessFile rAccessFile) {
		TransferEntity transferEntity = new TransferEntity();
		transferEntity.setFileSize(fileSize);
		transferEntity.setTempFile(tempFile);
		transferEntity.setfOStream(fOStream);
		transferEntity.setAccessFile(rAccessFile);
		transferTables.put(token, transferEntity);
	}

	/**
	 * 移除文件令牌对应的单个实体
	 * @param fileToken
	 */
	public void removeTransferEntity(int fileToken) {
		transferTables.remove(fileToken);
	}

	/**
	 * 移除上传记录中的所有实体
	 * 
	 * @param fileName
	 */
	public void removeAllTransferEntity() {
		transferTables.clear();
	}

	/**
	 * 上传进度回调
	 */
	public void progress(int pid, int progress) {
		Logger.println(this, "progress", progress);
		// transferManager.progress(pid, progress);
	}

	/**
	 * 上传状态回调
	 */
	public void onState(int state, Object obj) {
		Logger.println(this, "onState", obj.toString());
		// transferManager.onState(state, obj);
	}

	/**
	 * 关闭服务serverSocket
	 */
	public void closeServer() {
		Logger.println(this, "closeSocket");
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
