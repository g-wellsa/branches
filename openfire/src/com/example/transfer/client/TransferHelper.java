package com.example.transfer.client;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.example.constant.Constants;

public class TransferHelper {

	/**
	 * 上传命令占4个字节;
	 * 用户ID长度占4个字节;
	 * 文件名长度占4个字节;
	 * 文件长度占4个字节;
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
		ByteBuffer buffer = ByteBuffer.allocate(16+userBytes.length+nameBytes.length);
		buffer.clear();
		buffer.putInt(Constants.CMD_UPLOAD_FILE);
		buffer.putInt((int) uploadFile.length());
		buffer.putInt(userBytes.length);
		buffer.put(userBytes,0,userBytes.length);
		buffer.putInt(nameBytes.length);
		buffer.put(nameBytes,0,nameBytes.length);
		userBytes=null;
		nameBytes=null;
		buffer.flip();
//		System.out.println("getFileInfo1--->" + buffer.getInt());
//		System.out.println("getFileInfo2--->" +buffer.getLong());
//		byte[] userBytes1=new byte[buffer.getInt()];
//		buffer.get(userBytes1);
//		byte[] nameBytes1=new byte[buffer.getInt()];
//		buffer.get(nameBytes1);
//		String string=new String(userBytes1, "GB2312").trim();
//		String string1=new String(nameBytes1, "GB2312").trim();
//		System.out.println("getFileInfo3--->" +string+" / "+string1);
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
	 * @throws UnsupportedEncodingException 
	 */
	public static byte[] getFileData(int fileToken, int blockIndex,
			int blockSize, byte[] blockData) throws UnsupportedEncodingException {
		ByteBuffer buffer = ByteBuffer.allocate(16+blockSize);
		byte[] dataBytes=new byte[12+blockSize];
		buffer.clear();
//		buffer.putInt(Constants.CMD_TRANSFER_FILE);
		buffer.putInt(fileToken);
		buffer.putInt(blockIndex);
		buffer.putInt(blockSize);
		buffer.put(blockData,0,blockSize);
		buffer.flip();
		buffer.get(dataBytes);
		buffer.compact();
//		System.out.println("getFileData1--->" + buffer.getInt());
//		System.out.println("getFileData2--->" +buffer.getInt());
//		System.out.println("getFileData3--->" +buffer.getInt());
//		byte[] blockData1=new byte[buffer.getInt()];
//		buffer.get(blockData1);
//		String string=new String(blockData1, "GB2312").trim();
//		System.out.println("getFileData4--->" +string);
		return dataBytes;
	}
	
	public static byte[] getFileData(int fileToken, byte[] blockData) throws UnsupportedEncodingException {
		ByteBuffer buffer = ByteBuffer.allocate(4+blockData.length);
		byte[] dataBytes=new byte[4+blockData.length];
		buffer.clear();
		buffer.putInt(fileToken);
		buffer.put(blockData,0,blockData.length);
		buffer.flip();
		buffer.get(dataBytes);
		buffer.compact();
		return dataBytes;
	}

	public static void main(String args[]) {
//		File uploadFile = new File("d:\\upload\\工作交接.bmp");
//		try {
////			getFileInfo("553755454", uploadFile);
//			getFileData(553755, 1,31,"sfjsdfk是打发哪里开始的发放内裤".getBytes("GB2312"));
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
	}
}
