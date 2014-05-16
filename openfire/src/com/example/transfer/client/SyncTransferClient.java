package com.example.transfer.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.client.listener.ITransferListener;
import com.example.helper.Logger;

public class SyncTransferClient implements ITransferListener {
    private static ArrayList<String> fileList = new ArrayList<String>();  
    private TransferManager transferManager;
    
    public SyncTransferClient(TransferManager transferManager){  
    	this.transferManager=transferManager;
    }  
      
    public void service(){  
        ExecutorService executorService = Executors.newCachedThreadPool();  
        Vector<Integer> vector = getRandom(fileList.size());  
        for(Integer integer : vector){  
            String filePath = fileList.get(integer.intValue());  
            executorService.execute(sendFile(filePath));  
        }  
    }  
      
    private Vector<Integer> getRandom(int size){  
        Vector<Integer> v = new Vector<Integer>();  
        Random r = new Random();  
        boolean b = true;  
        while(b){  
            int i = r.nextInt(size);  
            if(!v.contains(i))  
                v.add(i);  
            if(v.size() == size)  
                b = false;  
        }  
        return v;  
    }      
      
    private static Runnable sendFile(final String filePath){  
        return new Runnable(){  
              
            private Socket socket = null;  
            private String ip ="localhost";  
            private int port = 10000;  
              
            public void run() {  
                System.out.println("开始发送文件:" + filePath);  
                File file = new File(filePath);  
                if(createConnection()){  
                    int bufferSize = 8192;  
                    byte[] buf = new byte[bufferSize];  
                    try {  
                        DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));  
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());  
                          
                        dos.writeUTF(file.getName());  
                        dos.flush();  
                        dos.writeLong(file.length());  
                        dos.flush();  
                          
                        int read = 0;  
                        int passedlen = 0;  
                        long length = file.length();    //获得要发送文件的长度  
                        while ((read = fis.read(buf)) != -1) {  
                            passedlen += read;  
                            System.out.println("已经完成文件 [" + file.getName() + "]百分比: " + passedlen * 100L/ length + "%");  
                            dos.write(buf, 0, read);  
                        }  
  
                       dos.flush();  
                       fis.close();  
                       dos.close();  
                       socket.close();  
                       System.out.println("文件 " + filePath + "传输完成!");  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
              
            private boolean createConnection() {  
                try {  
                    socket = new Socket(ip, port);  
                    System.out.println("连接服务器成功！");  
                    return true;  
                } catch (Exception e) {  
                    System.out.println("连接服务器失败！");  
                    return false;  
                }   
            }  
              
        };  
    }  

	public void progress(int pid, int progress) {
		Logger.println(this, "progress",progress);
//		transferManager.progress(pid, progress);
	}

	public void onState(int state, Object obj) {
		Logger.println(this, "onState", obj.toString());
//		transferManager.onState(state, obj);
	}

	public void uploadFile(boolean async, String filePath) {
		
	}

	public void downloadFile(boolean async, String fileName) {
		
	}

}