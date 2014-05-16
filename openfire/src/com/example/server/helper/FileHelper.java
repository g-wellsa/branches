package com.example.server.helper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.example.constant.Constants;

public class FileHelper {

	/**
	 * 上传命令占4个字节;
	 * 用户ID长度占4个字节;
	 * 文件名长度占4个字节;
	 * 文件长度占8个字节;
	 * 用户ID最大占32个字节;
	 * 文件名最大占255个字节
	 * 
	 * @param userId
	 * @param uploadFile
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static ByteBuffer getFileInfo(String userId, File uploadFile)
			throws UnsupportedEncodingException {
		byte[] userBytes=userId.getBytes("GB2312");
		byte[] nameBytes=uploadFile.getName().getBytes("GB2312");
		ByteBuffer buffer = ByteBuffer.allocate(20+userBytes.length+nameBytes.length);
		buffer.clear();
//		byte[] userBytes=new byte[32];
//		byte[] nameBytes=new byte[265];
//		System.arraycopy(userId.getBytes("GB2312"), 0, userBytes, 0, userId.length());
//		System.arraycopy(uploadFile.getName().getBytes("GB2312"), 0, nameBytes, 0, uploadFile.getName().length());
		buffer.putInt(Constants.CMD_UPLOAD_FILE);
		buffer.putLong(uploadFile.length());
		buffer.putInt(userBytes.length);
		buffer.put(userBytes);
		buffer.putInt(nameBytes.length);
		buffer.put(nameBytes);
		buffer.flip();
		userBytes=null;
		nameBytes=null;
		System.out.println("main1--->" + buffer.getInt());
		System.out.println("main2--->" +buffer.getLong());
		byte[] userBytes1=new byte[buffer.getInt()];
		buffer.get(userBytes1);
		byte[] nameBytes1=new byte[buffer.getInt()];
		buffer.get(nameBytes1);
		String string=new String(userBytes1, "GB2312").trim();
		String string1=new String(nameBytes1, "GB2312").trim();
		System.out.println("main3--->" +string+" / "+string1);
		return buffer;
	}

	/**
	 * 传输命令占4个字节；
	 * 文件令牌占4个字节； 
	 * 文件块序号4个字节； 
	 * 文件块大小占4个字节； 
	 * 文件数据大小占1024个字节
	 * 
	 * @param fileToken
	 * @param fileIndex
	 * @param blockSize
	 * @param fileData
	 * @return
	 */
	public static ByteBuffer getFileData(int fileToken, int fileIndex,
			int fileSize, byte[] fileData) {
		ByteBuffer buffer = ByteBuffer.allocate(1040);
		buffer.clear();
		buffer.putInt(Constants.CMD_TRANSFER_FILE);
		buffer.putInt(fileToken);
		buffer.putInt(fileIndex);
		buffer.putInt(fileSize);
		buffer.put(fileData, 0, fileSize);
		return buffer;
	}

	public static byte[] getFileInfoPack(String FileName, long FileSize) {
		ByteBuffer buf = ByteBuffer.allocate(1024);
		byte[] infopack = new byte[300];
		byte[] filename = new byte[255];

		System.arraycopy(FileName.getBytes(), 0, filename, 0, FileName.length());
		buf.clear();
		buf.put((byte) 0x1);
		buf.putLong(FileSize);
		buf.put(filename);
		buf.flip();
//		buf.get(infopack);
		Logger.println("getFileInfoPack--->", new String(infopack));
		return infopack;
	}

	public static void main(String args[]) {
		// getFileData("");
		File uploadFile = new File("d:\\upload\\Java NIO 28中文版.pdf");
		try {
			Logger.println("main--->", "Java NIO 28中文版.".length());
			Logger.println("main--->", "Java NIO 28中文版.pdf".length());
			getFileInfo("553755454.pdf", uploadFile);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}
}
