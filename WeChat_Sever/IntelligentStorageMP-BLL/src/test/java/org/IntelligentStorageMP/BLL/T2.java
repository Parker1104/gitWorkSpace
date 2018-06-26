package org.IntelligentStorageMP.BLL;

import java.util.Date;

import com.hzdongcheng.components.toolkits.utils.DateUtils;

public class T2 {
	 public Integer id;
     public String name;
     public Date dateeeeeees;
     

	public T2( ) {
    	 
 	 
 	}
	public T2(Integer id, String name , Date dateeeeeees) {
 
		this.id = id;
		this.name = name;
		this.dateeeeeees = dateeeeeees;
	}
	
	
	
    public Date getDateeeeeees() {
		return dateeeeeees;
	}
	public void setDateeeeeees(Date dateeeeeees) {
		this.dateeeeeees = dateeeeeees;
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
 
     
	
	public static void main(String[] args) {
		DateUtils DateUtilssss=new DateUtils();
	}
     
     
     
     
}
