package com.kamilu.kompozytor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class KompozytorServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse response)
			throws IOException {
		response.setContentType("image/jpeg");
		ServletOutputStream out;
		out = response.getOutputStream();
		FileInputStream fin = new FileInputStream("c:\\test\\java.jpg");

		BufferedInputStream bin = new BufferedInputStream(fin);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		int ch = 0;
		;
		while ((ch = bin.read()) != -1) {
			bout.write(ch);
		}

		bin.close();
		fin.close();
		bout.close();
		out.close();
	}
}
