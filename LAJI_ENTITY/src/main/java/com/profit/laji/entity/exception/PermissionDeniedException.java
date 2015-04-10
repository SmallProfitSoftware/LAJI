package com.profit.laji.entity.exception;

/**
 * 授权异常
 * @author heyang
 * 2015-03-25
 */
public class PermissionDeniedException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 6417641452178955756L;

	public PermissionDeniedException() {
		super();
	}

	public PermissionDeniedException(String message) {
		super(message);
	}

	public PermissionDeniedException(Throwable cause) {
		super(cause);
	}

	public PermissionDeniedException(String message, Throwable cause) {
		super(message, cause);
	}
}
