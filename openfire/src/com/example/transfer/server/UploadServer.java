package com.example.transfer.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.constant.Constants;
import com.example.helper.Logger;
import com.example.server.listener.ISocketListener;

public class UploadServer implements ISocketListener{
	private ServerSocketChannel serverSocket;
	private ExecutorService executorService;
	private CharsetEncoder encoder;
	private CharsetDecoder decoder;
	private ByteBuffer byteBuffer;
	private Selector selector;

	public UploadServer() {
		initTransferVaribles();
		initTransferServer();
	}

	private void initTransferVaribles() {
		executorService=Executors.newFixedThreadPool(Constants.THREAD_POOL_COUNT);
		byteBuffer = ByteBuffer.allocate(Constants.BYTE_BUFFER_SIZE);
		Charset charset = Charset.forName("GB2312");
		encoder = charset.newEncoder();
		decoder = charset.newDecoder();
	}

	private void initTransferServer() {
		try {
			selector = Selector.open();
			serverSocket = ServerSocketChannel.open();
			serverSocket.socket().bind(
					new InetSocketAddress(Constants.SERVER_FILE_PORT));
			serverSocket.configureBlocking(false);
			serverSocket.register(selector, SelectionKey.OP_ACCEPT);
			startListener(selector);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 监听端口
	public void startListener(Selector selector) {
		Logger.println(this, "startListener", selector);
		try {
		    while(selector.isOpen())
            {
                int nKeys=selector.select();
                if(nKeys>0)
                {
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while (it.hasNext()) 
                    {
                        SelectionKey key = it.next();
                        it.remove();
                        if (!key.isValid() || !key.channel().isOpen())
                            continue;
                        if(key.isAcceptable())
                        {
                            SocketChannel sc = serverSocket.accept();
                            if (sc != null)
                            {
                                sc.configureBlocking(false);
                                sc.register(selector, SelectionKey.OP_READ, new Reader(executorService));
                            }
                        }
                        else if(key.isReadable())
                        {
                        	Reader reader=(Reader) key.attachment();
                        	reader.execute(key);
                        }
                        else if(key.isWritable()){
                        	Writer writer=(Writer) key.attachment();
                        	writer.execute(key);
						}
                    }
                }
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handleAccept(SelectionKey key) {
		Logger.println(this, "handleAccept", key);
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		try {
			SocketChannel channel = server.accept();
			// 设置非阻塞模式(异步)
			channel.configureBlocking(false);
			channel.register(selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleWrite(SelectionKey key) {
		Logger.println(this, "handleWrite", key);
		SocketChannel channel = (SocketChannel) key.channel();
		String name = (String) key.attachment();
		try {
			ByteBuffer block = encoder
					.encode(CharBuffer.wrap("Hello !" + name));
			System.out.println(this+"isWritable Hello !" + name);
			channel.write(block);
			channel.close();
			block.clear();
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	public void handleRead(SelectionKey key) {
		Logger.println(this, "handleRead", key);
		SocketChannel channel = (SocketChannel) key.channel();
		try {
			int count = channel.read(byteBuffer);
			if (count > 0) {
				byteBuffer.flip();
				CharBuffer charBuffer = decoder.decode(byteBuffer);
				String name = charBuffer.toString();
				System.out.println(this+"isReadable " + name);
				SelectionKey sKey = channel.register(selector,
						SelectionKey.OP_WRITE);
				sKey.attach(name);
			} else {
				channel.close();
			}
			byteBuffer.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	//结束时释放资源
	public void finalize() {
		try {
			this.serverSocket.close();
			this.selector.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		UploadServer fileServer=new UploadServer();
	}
	
}
