package edu.fudan.util.exception;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LoadModelException extends Exception {
	
	private static final long serialVersionUID = -3933859344026018386L;

	public LoadModelException(Exception e, String file) {
		super(e);
		if( e instanceof FileNotFoundException) {
			System.out.println("模型文件不存在： "+ file);
		} else if (e instanceof ClassNotFoundException) {			
			System.out.println("模型文件版本错误。");
		} else if (e instanceof IOException) {
			System.out.println("模型文件读入错误： "+file);
			
		}
		e.printStackTrace();
	}

	public LoadModelException(String msg) {
		super(msg);
		printStackTrace();
	}
}
