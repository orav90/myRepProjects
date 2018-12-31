package com.hit.memoryunits;

import java.util.Arrays;
import java.util.Objects;

/////////////////////////
//Page Class
//Handle page methods
/////////////////////////
public class Page<T> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	//generic page content
	private T content;
	private Long id;
	
	public Page(java.lang.Long id,T content) {
		this.content=content;
		this.id=id;
	}
	
	public Long getPageId()
	{return id;}
	
	public void setPageId(java.lang.Long pageId)
	{ this.id=pageId;}
	
	public T getContent()
	{return content;}
	
	public void setContent(T content)
	{this.content=content;}
	
	public int hashCode()
	{
		return Objects.hash(id);
	}
	
	//compare two pages
	public boolean equals(java.lang.Object obj)
	{ if (this == obj)
        return true;
    // null check
    if (obj == null)
        return false;
    // type check and cast
    if (getClass() != obj.getClass())
        return false;
    @SuppressWarnings("unchecked")
	Page<T> p = (Page<T>) obj;
    // field comparison
    return this.id.equals(p.id);
	}
	
	//overloading 
	public String toString() {
		byte[] dataArray = (byte[])content;
		return new String(id + " " + Arrays.toString(dataArray));
	}

}
