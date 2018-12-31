package com.hit.exception;

public class InconsistencePagesAndData extends Exception {


	private static final long serialVersionUID = 1L;
	
	public InconsistencePagesAndData() {}
	
	public InconsistencePagesAndData(String msg)
	{
		super(msg);
	}

}
