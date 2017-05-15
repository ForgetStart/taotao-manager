package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtil;

public class FTPTest {

	@Test
	public void testFtpClient() throws Exception{
		//创建一个FtpClient对象
		FTPClient ftpClient = new FTPClient();
		
		//创建ftp链接。默认是21端口
		ftpClient.connect("192.168.126.130", 21);
		
		//登陆ftp服务器，使用用户名密码
		ftpClient.login("ftpuser", "123456");
		
		//上传文件
		//读取本地文件
		FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\shamo.jpg"));
		
		//设置上传的路径
		ftpClient.changeWorkingDirectory("/home/ftpuser/Music");
		
		//设置上传文件的格式(设置成二进制格式)
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		
		//第一个参数：服务器端文件名称
		//第二个参数：上传文档的inputStream
		ftpClient.storeFile("hello.jpg", inputStream);
		
		//关闭连接
		ftpClient.logout();
	}
	
	@Test
	public void testFtpUtil() throws Exception{
		FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\shamo.jpg"));
		FtpUtil.uploadFile("192.168.126.130", 21, "ftpuser", "123456", "/home/ftpuser/Music", "/2016/02", "hello.jpg", inputStream);
	}
}
