package org.IntelligentStorageMP.BLL;

import java.util.Date;

public class T1 {
	 public Integer id;
     public String name;
     public Date dates;
     
     
     public T1( ) {
 
 	}
     
	public T1(Integer id, String name, Date dates) {
 
		this.id = id;
		this.name = name;
		this.dates = dates;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDates() {
		return dates;
	}
	public void setDates(Date dates) {
		this.dates = dates;
	}
     
     
     
     
     
}
