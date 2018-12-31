package com.hit.exception;

public class AlgoRamUnsync extends Exception{

	private static final long serialVersionUID = 1L;

	public AlgoRamUnsync() {}
	
	public AlgoRamUnsync(String msg)
	{
		super(msg);
	}
}
