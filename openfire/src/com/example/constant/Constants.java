package com.example.constant;

public class Constants {
	
	public static final int BYTE_BUFFER_SIZE = 1024;
	
	
	public static final String SERVER_SOCKET_IP = "localhost";
	public static final String SERVER_BASEURL = "localhost";
	public static final String SERVER_NAME = "uploadServer";
	public static final String UPLOAD_PATH = "E:\\upload\\";
	
	public static final int SERVER_CONN_TIMEOUT = 500;
	public static final int SERVER_VIDEO_PORT = 5000;
	public static final int SERVER_AUDIO_PORT = 5001;
	public static final int SERVER_CHAT_PORT = 5222;
	public static final int SERVER_FILE_PORT = 9000;
	
	public static final int FILE_BLOCK_SIZE =1024;//标准文件传输大小
	public static final int SERVER_CONN_COUNT =5;//尝试绑定服务次数
	public static final int THREAD_POOL_COUNT =64;//最大线程池数
	
	public static final int CMD_UPLOAD_FILE=14000;
	public static final int CMD_TRANSFER_FILE=14001;
	public static final int CMD_DOWNLOAD_FILE=14002;
	public static final int CMD_CREATE_FILE_SUCCESS=14003;
	public static final int CMD_CREATE_FILE_FAILURE=-14000;
	public static final int CMD_TRANSFER_FILE_ERROR=-14001;
	
	 public static boolean isSSL;
	
}
