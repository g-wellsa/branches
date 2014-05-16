package com.example.transfer.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.example.constant.Constants;

public class UploadClient {
	private ByteBuffer readBuffer,writeBuffer;
	private SocketChannel clientChannel;
	public UploadClient() {
		readBuffer=ByteBuffer.allocate(1024);
		writeBuffer=ByteBuffer.allocate(1024);
		initTransferClient();
	}
	
	private void initTransferClient(){
		try {
			Selector selector = Selector.open();
			SocketAddress address = new InetSocketAddress("localhost", Constants.SERVER_FILE_PORT);
			// 定义一个服务器地址的对象
			clientChannel = SocketChannel.open(address);
			// 将客户端设定为异步
			clientChannel.configureBlocking(false);
			clientChannel.register(selector, SelectionKey.OP_CONNECT);
//			startListener(selector);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	public void startListener(Selector selector) {
		try {
			// 利用循环来读取服务器发回的数据
			while (true) {
//				// 如果客户端连接没有打开就退出循环
//				if (!clientChannel.isOpen())
//					break;
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
					if (key.isReadable()) {
						// 将临时客户端对象实例为本事件的socket对象
						SocketChannel sc = (SocketChannel) key.channel();
						// 定义一个用于存储所有服务器发送过来的数据
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						// 将缓冲区清空以备下次读取
						readBuffer.clear();
						// 此循环从本事件的客户端对象读取服务器发送来的数据到缓冲区中
						while (sc.read(readBuffer) > 0) {
							// 将本次读取的数据存到byte流中
							bos.write(readBuffer.array());
							// 将缓冲区清空以备下次读取
							readBuffer.clear();
						}
						// 如果byte流中存有数据
						if (bos.size() > 0) {
							// 建立一个普通字节数组存取缓冲区的数据
							byte[] b = bos.toByteArray();

							System.out.println("接收数据: " + new String(b));
							// 关闭客户端连接,此时服务器在read读取客户端信息的时候会返回-1
							clientChannel.close();
							System.out.println("连接关闭!");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writting(Object obj){
		try {
			writeBuffer = ByteBuffer.allocate(512);
			writeBuffer.put(obj.toString().getBytes("GB2312"));
			writeBuffer.flip();
			clientChannel.write(writeBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("发送数据: " + new String(writeBuffer.array()));
	}

	public static void main(String args[]) {
		UploadClient client=new UploadClient();
		File[] files=new File("d:\\upload").listFiles();
		for(File file:files){
			client.writting(file.getAbsolutePath());
		}
	}
}
