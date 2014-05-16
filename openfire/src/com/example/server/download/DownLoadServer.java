//package com.example.server.download;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.URL;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.example.server.helper.Logger;
//
//
//@SuppressWarnings("serial")
//public class DownLoadServer extends HttpServlet {
//	public DownLoadServer() {
//		super();
//	}
//
//	public void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String filePath = this.getServletContext().getRealPath("/images");
//		String fileName = request.getParameter("file");
//		Logger.println(this, "doGet", fileName);
//		downLoadFile(filePath + "\\" + fileName, response, false);
//	}
//
//	public void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//	}
//
//	public void downLoadFile(String filePath, HttpServletResponse response,
//			boolean isOnLoad) throws IOException {
//		File file = new File(filePath);
//		Logger.println(this, "downLoadFile", file);
//		BufferedInputStream br = new BufferedInputStream(new FileInputStream(
//				file));
//		byte[] buf = new byte[1024];
//		int len = 0;
//		response.reset();
//		response.setContentLength((int) file.length());
//		if (isOnLoad) {
//			URL url = new URL("file:///" + filePath);
//			response.setContentType(url.openConnection().getContentType());
//			response.setHeader("Content-Disposition",
//					"inline;filename=" + file.getName());
//		} else {
//			response.setContentType("application/x-msdownload");
//			response.setHeader("Content-Disposition", "attachment;filename="
//					+ file.getName());
//		}
//		OutputStream out = response.getOutputStream();
//		int completed = 0;
//		while ((len = br.read(buf)) > 0){
//			out.write(buf, 0, len);
//			completed+=len;
//			Logger.println(this, "downLoadFile while", completed);
//		}
//		br.close();
//		out.close();
//	}
//
//}
