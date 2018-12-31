package com.hit.exception;

public class HardDiskException extends Exception   {
	
	private static final long serialVersionUID = 1L;

	public HardDiskException() {}
	
	public HardDiskException(String msg)
	{
		super(msg);
	}

}
