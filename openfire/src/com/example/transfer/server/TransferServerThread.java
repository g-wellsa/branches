package com.example.transfer.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;

import com.example.constant.Constants;
import com.example.helper.Logger;

/**
 * Description: 文件传输接收类
 */
public class TransferServerThread implements Runnable {
	private AsyncTransferServer asTransferServer;
	private SyncTransferServer sTransferServer;
	private SocketChannel socketChannel;
	private Selector selector;
	private Socket socket;
	
	private File tempFile;
	private FileOutputStream fOStream ;
	private RandomAccessFile accessFile;

	private byte[] fileBuffer;
	private ByteBuffer receiveBuffer;
	private int receivedLen,completedSize,fileSize,blockCount;

	public TransferServerThread(AsyncTransferServer asTransferServer,
			SocketChannel socketChannel) {
		Logger.println(this, "construct");
		this.socketChannel = socketChannel;
		this.asTransferServer = asTransferServer;
		this.receiveBuffer = ByteBuffer.allocate(16+Constants.BYTE_BUFFER_SIZE);
	}

	public TransferServerThread(SyncTransferServer sTransferServer,
			Socket socket) {
		this.socket = socket;
		this.fileBuffer = new byte[1024 * 8];
		this.sTransferServer = sTransferServer;
	}

	public void run() {
		if (socket != null) {
			syncReceiveFile();
		} else {
			try {
				ayncReceiveFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void ayncReceiveFile() throws IOException {
		Logger.println(this, "ayncReceiveFile",Thread.currentThread()
				.getName());
		receiveBuffer.clear();
		selector = Selector.open();
		SelectionKey key = socketChannel.register(selector,
				SelectionKey.OP_READ);
		while (key.selector().isOpen()) {
			int select = selector.select();
			if (select == 0) {
				handleRead(key);
			}
		}
	}

	/**
	 * 处理客户端消息
	 * 
	 * @param key
	 * @throws IOException
	 */

	public void handleRead(SelectionKey skey) {
		SocketChannel socketChannel = (SocketChannel) skey.channel();
		try {
			int readLen = socketChannel.read(receiveBuffer);
			if (readLen  >= 1) {
				receiveBuffer.rewind();
				int command = receiveBuffer.getInt();
				switch (command) {
				case Constants.CMD_UPLOAD_FILE:
					fileSize = receiveBuffer.getInt();
					byte[] userBytes = new byte[receiveBuffer.getInt()];
					receiveBuffer.get(userBytes);
					byte[] nameBytes = new byte[receiveBuffer.getInt()];
					receiveBuffer.get(nameBytes);
					receiveBuffer.clear();
					String userId = new String(userBytes, "GB2312").trim();
					String fileName = new String(nameBytes, "GB2312").trim();
					int token = (userId + fileName + System.currentTimeMillis())
							.hashCode();
					boolean success = createNewFile(token, userId, fileName,
							fileSize);
					Logger.println(this, "handleRead~", success);
					if (success) {
						handleRespone(Constants.CMD_CREATE_FILE_SUCCESS, token, nameBytes);
					}
					else{
						handleRespone(Constants.CMD_CREATE_FILE_FAILURE, token,"创建文件失败!");
					}
					userBytes = null;
					nameBytes = null;
					break;
				case Constants.CMD_TRANSFER_FILE:
					writtingTempFile(readLen);
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				socketChannel.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Logger.println(this, "handleRead err", e);
		}

	}

	private synchronized boolean createNewFile(int token, String userId,
			String fileName, int fileSize) {
		Logger.println(this, "createNewFile", token + "/" + userId + "/"
				+ fileName + "/" + fileSize);
		File fileDir = new File(Constants.UPLOAD_PATH + "/" + userId);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		int exist = hasFileExist(fileDir, fileName);
		if (exist != 0) {
			fileName = fileName + exist;
		}
	
		try {
			fOStream = new FileOutputStream(fileDir.getAbsolutePath() + "/"
					+ fileName);
			tempFile = new File(fileDir.getAbsolutePath() + "/" + fileName+ ".tmp"); // 创建临时文件
			accessFile = new RandomAccessFile(tempFile, "rw"); // 向临时文件中写入数据
			Logger.println(this, "createNewFile~", "文件创建成功,等待写入数据...");
//			asTransferServer.putTransferEntity(token, fileName, fileSize,
//					tempFile, fOStream, rAccessFile);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (tempFile != null) {
			tempFile.delete();
		}
		if (fOStream != null) {
			try {
				fOStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	private int exist = 0;
	private int hasFileExist(File file, String fileName) {
		File[] files = file.listFiles();
		for (File filess : files) {
			if (filess.isFile()) {
				if (filess.getName().equals(fileName)) {
					exist++;
				}
			} else {
				hasFileExist(filess, fileName);
			}
		}
		return exist;
	}

	private void writtingTempFile(int readLen) {
		receivedLen+=readLen;
		if (receivedLen >= 16) {
			int fileToken = receiveBuffer.getInt();
			if (fileToken==0) {
				try {
					handleRespone(Constants.CMD_TRANSFER_FILE_ERROR, fileToken, "不存在该上传记录");
					disConnected();
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				return;
			}
			int blockIndex = receiveBuffer.getInt();
			int blockSize = receiveBuffer.getInt();
			if (receivedLen >= (16 + blockSize)) {
				byte[] blockData = new byte[blockSize];
				receiveBuffer.get(blockData);
//				Logger.println(this, "writtingNewFile",fileToken+" / "+blockIndex+" / "+blockSize+"/"+blockData);
				receiveBuffer.compact();
				receivedLen -= (16 + blockSize);
				receiveBuffer.position(receivedLen); // 数据指针指向receiveBuffer缓冲区最后位置
				try {
					if (blockSize < Constants.FILE_BLOCK_SIZE ) {
						// 如果收到的数据块大小小于定义的最大文件块大小
						byte[] tmpBytes = new byte[Constants.FILE_BLOCK_SIZE]; // /定义临时最大数据块
						System.arraycopy(blockData, 0, tmpBytes, 0, blockSize);
						accessFile.seek((Constants.FILE_BLOCK_SIZE + 4)
								* blockIndex);// 一个整型占4个字节，用于存放文件大小信息的
						// 文件块写入临时文件中将相应数据块放到相应的位置中去
						accessFile.writeInt(blockSize); // 向临时数据块中写入文件大小信息
						accessFile.write(tmpBytes); // 向临时数据块中写入文件块信息
						blockCount++; // 文件块计数器累加
					} else {
						accessFile.seek((Constants.FILE_BLOCK_SIZE + 4)
								* blockIndex);
						// 文件块写入临时文件中将相应数据块放到相应的位置中去
						accessFile.writeInt(blockSize);
						accessFile.write(blockData);
						blockCount++;// 文件块计数器累加
					}
					completedSize+=blockSize;
					Logger.println(this, "writtingNewFile", "写入临时文件" + blockCount+"/"+blockSize+"/"+completedSize);
					writtingNewFile(blockSize);
				} catch (IOException e) {
					e.printStackTrace();
					try {
						handleRespone(Constants.CMD_TRANSFER_FILE_ERROR, fileToken, "写入文件时异常"+e.getMessage());
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
				}
			}
			else {
				Logger.println(this, "writtingNewFile~", false);
				receiveBuffer.position(receivedLen);
			}
		} 
		else {
			Logger.println(this, "writtingNewFile~~", false);
			receiveBuffer.position(receivedLen); // /数据指针指向buf的最后位置
		}
		
	}
	
	private void writtingNewFile(int blockSize){
		if (fileSize== completedSize) { //如果减到0了，说明文件块已经收全了，可以关闭文件了。
			for (int vari = 0; vari < blockCount; vari++) {
				// 根据文件块个数循环将临时文件写入正式文件
				try {
					accessFile.seek((Constants.FILE_BLOCK_SIZE + 4) * vari);
					blockSize =accessFile.readInt();// 从临时文件中读取文件块大小
					byte[] tmpBytes = new byte[blockSize];
					accessFile.read(tmpBytes);// 从临时文件中读取文件块数据
					fOStream.write(tmpBytes);// 向正式文件中写入文件块
				} catch (IOException e) {
					e.printStackTrace();
					
				}
			}
			disConnected();
			Logger.println(this, "writtingNewFile", "文件已经接收完成了!");
		}
	}

	private void handleRespone(int command,int fileToken,Object obj) throws UnsupportedEncodingException{
		byte[] sendBytes;ByteBuffer sendBuffer = null;
		switch (command) {
			case Constants.CMD_CREATE_FILE_SUCCESS:
				sendBytes=(byte[]) obj;
				sendBuffer = ByteBuffer
				.allocate(12 + sendBytes.length);
				sendBuffer.putInt(command);
				sendBuffer.putInt(fileToken);
				sendBuffer.putInt(sendBytes.length);
				sendBuffer.put(sendBytes);
				break;
			case Constants.CMD_CREATE_FILE_FAILURE:
				sendBytes=obj.toString().getBytes("GB2312");
				sendBuffer= ByteBuffer
						.allocate(12 + sendBytes.length);
				sendBuffer.putInt(command);
				sendBuffer.putInt(fileToken);
				sendBuffer.putInt(sendBytes.length);
				sendBuffer.put(sendBytes);
				break;
			case Constants.CMD_TRANSFER_FILE_ERROR:
				sendBytes = obj.toString().getBytes("GB2312");
				sendBuffer = ByteBuffer
						.allocate(12 + sendBytes.length);
				sendBuffer.putInt(command);
				sendBuffer.putInt(fileToken);
				sendBuffer.putInt(sendBytes.length);
				sendBuffer.put(sendBytes);
				break;
			default:
				break;
		}
		if (sendBuffer!=null) {
			handleWrite(sendBuffer);
		}
		sendBytes=null;
	}
	
	/**
	 * 向客户端发送信息
	 * 
	 * @param key
	 */
	public void handleWrite(ByteBuffer byteBuffer) {
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
	
	private void disConnected(){
		try {
			if (fOStream!=null) {
				fOStream.close();
			}
			fOStream=null;
			if (accessFile!=null) {
				accessFile.close();
			}
			accessFile=null;
			if (tempFile!=null&&tempFile.exists()) {
				tempFile.delete();
			}
			tempFile=null;
//			if (socketChannel!=null) {
//				socketChannel.close();
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void syncReceiveFile() {
		DataInputStream dis = null;
		DataOutputStream dos = null;

		try {
			dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			String savePath = Constants.UPLOAD_PATH + dis.readUTF();
			long length = dis.readLong();
			dos = new DataOutputStream(new BufferedOutputStream(
					new FileOutputStream(savePath)));

			int read = 0;
			long passedlen = 0;
			while ((read = dis.read(fileBuffer)) != -1) {
				passedlen += read;
				dos.write(fileBuffer, 0, read);
				Logger.println(this, "文件[" + savePath + "]已经接收: " + passedlen
						* 100L / length + "%");
			}
			Logger.println(this, "文件: " + savePath + "接收完成!");

		} catch (Exception e) {
			e.printStackTrace();
			Logger.println(this, "接收文件失败!");
		} finally {
			try {
				if (dos != null) {
					dos.close();
				}
				if (dis != null) {
					dis.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
