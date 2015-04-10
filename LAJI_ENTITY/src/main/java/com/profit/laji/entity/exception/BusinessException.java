package com.profit.laji.entity.exception;

/**
 * 系统业务异常
 * @author heyang
 * 2015-01-28
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 2332608236621015980L;

	private int code;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
