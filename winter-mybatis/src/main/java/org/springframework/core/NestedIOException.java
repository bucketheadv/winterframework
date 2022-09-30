package org.springframework.core;

/**
 * @author qinglinl
 * Created on 2022/9/30 2:16 PM
 */
public class NestedIOException extends Exception {
	public NestedIOException()
	{
		super();
	}

	public NestedIOException(String message)
	{
		super(message);
	}

	public NestedIOException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public NestedIOException(Throwable cause)
	{
		super(cause);
	}

	protected NestedIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
