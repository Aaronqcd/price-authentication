/*
 * created 2008-9-14
 *
 */
package com.pemng.serviceSystem.base.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author luohanbin
 * 
 */
public class SimpleCSVWriter {

	private BufferedOutputStream bufStream;

	private StringBuffer buf = null;

	private static final char SPLIT = ',';

	private int bufSize = 500;

	private static final char TAB = '\t';

	private static final String END = "\r\n";

	private static final String NULL_VALUE = "";

	public SimpleCSVWriter(OutputStream outputStream) {
		bufStream = new BufferedOutputStream(outputStream);
		buf = new StringBuffer(bufSize);
	}

	private void testFlush() throws IOException {
		if (buf.length() >= bufSize) {
			bufStream.write(buf.toString().getBytes());
			buf = new StringBuffer(bufSize);
		}
	}

	public void writeEnd(String val) throws IOException {
		buf.append(maskNull(val));
		this.endLine();
	}

	public void write(String val) throws IOException {
		buf.append(maskNull(val) + SPLIT);
	}

	public void writeEndWithTab(String val) throws IOException {
		buf.append(maskNull(val) + TAB + SPLIT);
	}

	/**
	 * 清理缓冲区
	 * 
	 * @throws IOException
	 */
	public void flush() throws IOException {
		if (buf.length() > 0) {
			bufStream.write(buf.toString().getBytes("UTF-8"));
			buf = new StringBuffer(bufSize);
		}
	}

	/**
	 * 结束一行
	 * 
	 * @throws IOException
	 */
	public void endLine() throws IOException {
		buf.append(END);
		testFlush();
	}

	/**
	 * 结束CSV的写入
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (buf.length() > 0) {
			bufStream.write(buf.toString().getBytes());
		}
		bufStream.close();
	}

	private String maskNull(String val) {
		if (val == null)
			return NULL_VALUE;
		return val;
	}

	public int getBufSize() {
		return bufSize;
	}

	public void setBufSize(int bufSize) {
		this.bufSize = bufSize;
	}

}
