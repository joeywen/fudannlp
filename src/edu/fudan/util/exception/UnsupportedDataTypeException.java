package edu.fudan.util.exception;

/**
 * 不支持数据类型
 * @author xpqiu
 *
 */
public class UnsupportedDataTypeException extends Exception {

	private static final long serialVersionUID = -7879174759276938120L;

	
	public UnsupportedDataTypeException(String msg) {
		super(msg);
		printStackTrace();
	}
}
