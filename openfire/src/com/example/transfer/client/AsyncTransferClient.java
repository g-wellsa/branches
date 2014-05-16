package com.example.transfer.client;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.client.listener.IProgressListener;
import com.example.constant.Constants;
import com.example.helper.Logger;

public class AsyncTransferClient implements IProgressListener {
	private Hashtable<Integer, TransferEntity> uploadTables;
	private TransferManager transferManager;
	private ExecutorService executorService;
	private SocketChannel clientChannel;
	private CharsetDecoder decoder;
	private CharsetEncoder encoder;
	private ByteBuffer clientBuffer;
	private Selector selector;

	public AsyncTransferClient(TransferManager transferManager) {
		this.transferManager = transferManager;
		initTransferVarible();
		initTransferClient();
	}

	private void initTransferVarible() {
		executorService = Executors
				.newFixedThreadPool(Constants.THREAD_POOL_COUNT);
		clientBuffer = ByteBuffer.allocate(Constants.BYTE_BUFFER_SIZE);
		uploadTables = new Hashtable<Integer, TransferEntity>();
		decoder = Charset.forName("UTF-8").newDecoder();
		encoder = Charset.forName("UTF-8").newEncoder();
	}

	private void initTransferClient() {
		try {
			// 定义一个记录套接字通道事件的对象
			selector = Selector.open();
			// 定义一个服务器地址的对象
			SocketAddress address = new InetSocketAddress(
					Constants.SERVER_SOCKET_IP, Constants.SERVER_FILE_PORT);
			clientChannel = SocketChannel.open(address);
			// 将客户端设定为异步
			clientChannel.configureBlocking(false);
			// 在轮讯对象中注册此客户端的读取事件(就是当服务器向此客户端发送数据的时候)
			clientChannel.register(selector, SelectionKey.OP_READ);
			startTransferListener();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 监听服务器
	 */
	public void startTransferListener() {
		new Thread(new Runnable() {
			
			public void run() {
				try {
					// 利用循环来读取服务器发回的数据
					while (true) {
						// 此方法为查询是否有事件发生如果没有就阻塞,有的话返回事件数量
						int select = selector.select();
						// 如果没有事件返回循环
						if (select == 0) {
							continue;
						}
						// 遍例所有的事件
						for (SelectionKey key : selector.selectedKeys()) {
							// 删除本次事件
							selector.selectedKeys().remove(key);
							// 如果本事件的类型为read时,表示服务器向本客户端发送了数据
							if (key.isConnectable()) {
								SocketChannel channel = (SocketChannel) key.channel();
								// 如果正在连接，则完成连接
								if (channel.isConnectionPending()) {
									channel.finishConnect();
								}
								// 设置成非阻塞
								channel.configureBlocking(false);
								// 在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
								channel.register(selector, SelectionKey.OP_READ);

								// 获得了可读的事件
							} else if (key.isReadable()) {
								handleRead(key);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	
	}

	/**
	 * 处理来自服务器的消息
	 * 
	 * @param key
	 * @throws IOException
	 *   命令解析14000：序号0：上传命令；序号1：上传令牌 ；序号2：文件名
	 *   命令解析-14000：序号0：错误代号；序号1：传输错误信息
	 */
	private byte[] bytes;
	private void handleRead(SelectionKey skey) throws IOException {
		Logger.println(this, "handleRead", skey);
		clientBuffer.clear();
		SocketChannel socketChannel = (SocketChannel) skey.channel();
		int read = socketChannel.read(clientBuffer);
		if (read > 0) {
			clientBuffer.flip();
			int command =clientBuffer.getInt();
			Logger.println(this, "handleRead~", command);
			switch (command) {
				case Constants.CMD_CREATE_FILE_SUCCESS:
					int fileToken = clientBuffer.getInt();
					bytes=new byte[clientBuffer.getInt()];
					clientBuffer.get(bytes);
					Logger.println(this, "handleRead~", new String(bytes, "GB2312"));
					uploadingFile(fileToken, new String(bytes, "GB2312"));
					break;
				case Constants.CMD_CREATE_FILE_FAILURE:
					bytes=new byte[clientBuffer.getInt()];
					transferManager.onState(-1, new String(bytes, "GB2312"));
				default:
					int token=clientBuffer.getInt();
					bytes=new byte[clientBuffer.getInt()];
					transferManager.onState(-1, new String(bytes, "GB2312"));
					break;
			}
			bytes=null;
		}
	}
	
	/**
	 * 编码
	 * @param charBuffer
	 * @return
	 * @throws CharacterCodingException
	 */
	public ByteBuffer encode(CharBuffer charBuffer) throws CharacterCodingException{
		return encoder.encode(charBuffer);
	}
	/**
	 * 编码
	 * @param content
	 * @return
	 * @throws CharacterCodingException
	 */
	public ByteBuffer encode(String  encode,String content) throws CharacterCodingException{
		return Charset.forName(encode).encode(content);
	}
	
	/**
	 * 解码
	 * @param byteBuffer
	 * @return
	 * @throws CharacterCodingException
	 */
	public CharBuffer decode(ByteBuffer byteBuffer) throws CharacterCodingException{
		return decoder.decode(byteBuffer);
	}

	/**
	 * 预备上传文件，请求获取访问令牌
	 * 
	 * @param socketChannel
	 * @param filePath
	 */
	public void uploadFile(String filePath) {
		Logger.println(this, "uploadFile", filePath);
		File uploadFile = new File(filePath);
		if (uploadFile.exists()) {
				Logger.println(this, "uploadFile~", uploadFile.length());
//				ByteBuffer byteBuffer =TransferHelper.getFileInfo("553755454", uploadFile);
//				while (byteBuffer.hasRemaining()) {
//					clientChannel.write(byteBuffer);
//				}
//				Logger.println(this, "uploadFile~", new String(byteBuffer.array()));
				 executorService.execute(new CopyOfTransferClientThread(this, clientChannel,
						 uploadFile));
		} else {
			transferManager.onState(-1, "文件不存在!");
		}

	}

	/**
	 * 获取到访问令牌，开始上传
	 * 
	 * @param fileToken
	 * @param fileName
	 */
	private void uploadingFile(int fileToken, String fileName) {
		Logger.println(this, "uploadingFile", fileToken + "/" + fileName);
		TransferEntity transferEntity = getTransferEntity(fileToken, fileName);
		 executorService.execute(new TransferClientThread(this, clientChannel,
		 transferEntity));
	}

	public void downloadFile(String fileName) {
	}

	/**
	 * 把文件放入上传记录器中
	 * 
	 * @param uploadFile
	 */
	public void putTransferEntity(File uploadFile) {
		TransferEntity transferEntity = new TransferEntity();
		transferEntity.setUploadFile(uploadFile);
		uploadTables.put(uploadFile.getName().hashCode(), transferEntity);
	}

	/**
	 * 把令牌放入上传记录器中并返回
	 * 
	 * @param fileToken
	 * @param fileName
	 * @return
	 */
	public TransferEntity getTransferEntity(int fileToken, String fileName) {
		TransferEntity transferEntity = uploadTables.get(fileName.hashCode());
		transferEntity.setFileToken(fileToken);
		return transferEntity;
	}

	/**
	 * 移除上传记录中的单个实体
	 * 
	 * @param fileName
	 */
	public void removeTransferEntity(String fileName) {
		uploadTables.remove(fileName.hashCode());
	}

	/**
	 * 移除上传记录中的所有实体
	 * 
	 * @param fileName
	 */
	public void removeAllTransferEntity() {
		uploadTables.clear();
	}

	/**
	 * 上传进度回调
	 */
	public void progress(int pid, int progress) {
		Logger.println(this, "progress", progress);
		transferManager.progress(pid, progress);
	}

	/**
	 * 上传状态回调
	 */
	public void onState(int state, Object obj) {
		Logger.println(this, "onState", obj.toString());
		transferManager.onState(state, obj);
	}

	/**
	 * 关闭服务serverSocket
	 */
	public void closeServer() {
		Logger.println(this, "closeSocket");
		try {
			this.clientChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}