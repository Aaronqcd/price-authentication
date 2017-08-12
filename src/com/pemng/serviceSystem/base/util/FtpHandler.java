package com.pemng.serviceSystem.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpHandler {
	Logger logger;

	/**
	 * @$comment ftp客户端对象
	 */
	private FTPClient client;

	public FtpHandler() {
		// 初始化成员变量
		client = new FTPClient();
	}

	public String getReplyString(){
		return client.getReplyString();
	}
	
	/**
	 * 登录FTP服务器
	 * 
	 * @param userName
	 *            用户名
	 * @param passWord
	 *            密码
	 * @param server
	 *            服务器地址
	 * @throws Exception
	 */
	public boolean login(String userName, String passWord, String server, Long timeoutMilliSec)
			throws Exception {
		int reply = 230;
		try {
			// 联接服务器
			int hasport = server.lastIndexOf(":");
			String[] servmes;
			if (hasport > 0) {
				servmes = server.split(":");
				client.connect(servmes[0], Integer.parseInt(servmes[1]));
			} else {
				client.connect(server);
			}
			client.enterLocalPassiveMode();
			// 登录
			client.login(userName, passWord);
			// 获得回持码
			reply = client.getReplyCode();

			// 验证回持码,登录失败
			if (!FTPReply.isPositiveCompletion(reply)) {
				client.disconnect();
				throw new Exception("FTP Login failed, error code:" + client.getReplyString());
			}

			client.setDefaultTimeout(timeoutMilliSec != null ? timeoutMilliSec
					.intValue() : 100000);
			client.setSoTimeout(100000);
			client.setDataTimeout(100000);
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			client.setBufferSize(5000);
			client.setReaderThread(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// 连接没有关闭
			if (client.isConnected()) {
				try {
					// 关闭连接
					client.disconnect();
				} catch (IOException e1) {
					e.printStackTrace();
				}
			}
			throw new Exception("FTP Login failed, error:" + e.getMessage(),e);
		}
	}

	/**
	 * @$comment 获得ftp服务器相应目录的文件列表
	 * @param sourceDir
	 *            FTP目录
	 * @return 相应目录下面的文件列表
	 * @throws DirPathException
	 * @throws DirPathException
	 *             路径异常
	 * @throws IOException
	 */
	public FTPFile[] getFileList(String sourceDir) throws IOException {

		FTPFile[] fileList = null;
		// 改变ftp服务器的工作目录
		client.changeWorkingDirectory(sourceDir);
		// 获得当前目录的文件列表
		fileList = client.listFiles();
		return fileList;
	}

	/**
	 * @$comment 获得ftp服务器相应目录的文件列表
	 * @param sourceDir
	 *            FTP目录
	 * @return 相应目录下面的文件列表
	 * @throws DirPathException
	 * @throws DirPathException
	 *             路径异常
	 * @throws IOException
	 */
	public String[] getFilenameList(String sourceDir) throws IOException {

		String[] str;
		// 改变ftp服务器的工作目录
		client.changeWorkingDirectory(sourceDir);

		str = client.listNames(sourceDir);
		return str;
	}

	/**
	 * 从FTP服务器取得文件输入流
	 * 
	 * @param SourceFile
	 *            源文件
	 * @throws DownLoadException
	 *             文件下载异常
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public InputStream getFileStream(String SourceFileName) throws IOException,
			InterruptedException {
		InputStream in = null;
		client.noop();
		in = client.retrieveFileStream(SourceFileName);
		// 防止连接远程FTP时响应过慢，等待1秒
		Thread.sleep(1000);
		return in;
	}

	/**
	 * 开输出流到ftp
	 * 
	 * @param remote
	 *            服务器端的文件名称
	 * @param local
	 *            本地输入流
	 * @return 操作真值
	 * @throws Exception 
	 */
	public OutputStream upFileStream(String remote, boolean isAppend) throws Exception {
		OutputStream outs;
		if(isAppend){
			outs = client.appendFileStream(remote);
		}else{
			outs = client.storeFileStream(remote);
		}
		if(outs == null){
			throw new Exception(client.getReplyString());
		}
		return outs;
	}

	/**
	 * 修改制定文件的文件名
	 * 
	 * @param oldname
	 *            原文件名
	 * @param newname
	 *            新文件名
	 * @return 操作真值
	 */
	public boolean rename(String oldname, String newname) throws IOException {
		boolean value = false;
		value = client.rename(oldname, newname);
		return value;
	}

	/**
	 * 删除当前操作路径下的文件
	 * 
	 * @param filename
	 *            文件名
	 * @return 操作真值
	 * @throws IOException
	 */
	public boolean delete(String filename) throws IOException {

		boolean value = client.deleteFile(filename);
		return value;
	}

	/**
	 * 创立一个指定文件名的文件
	 * 
	 * @param filename
	 * @return
	 */
	public boolean create(String filename) throws IOException {
		boolean creasuc = false;
		OutputStream outs = client.appendFileStream(filename);
		if (outs != null)
			outs.close();
		return creasuc;
	}

	/**
	 * 退出登陆，关闭连接
	 * 
	 * @throws IOException
	 * 
	 */
	public void logout() throws IOException {
		if (client.isConnected()) {
			try {
				client.logout();
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				throw new IOException("退出登录失败");
			}
		}
	}

	/**
	 * 检测是否完成文件提取操作
	 * 
	 * @return 操作真值
	 */
	public boolean complete() throws IOException {
		boolean temp;
		temp = client.completePendingCommand();
		return temp;
	}

	public static void main(String[] args) {
	}

	/**
	 * @return the client
	 */
	public FTPClient getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(FTPClient client) {
		this.client = client;
	}

}