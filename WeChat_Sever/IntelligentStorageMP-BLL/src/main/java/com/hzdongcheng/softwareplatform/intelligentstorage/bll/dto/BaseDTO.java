package com.hzdongcheng.softwareplatform.intelligentstorage.bll.dto;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class BaseDTO implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 8060234670333577527L;
	
	
	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);  
        Field[] fields = this.getClass().getDeclaredFields();  
        try {  
            for (Field f : fields) {  
                f.setAccessible(true);  
                builder.append(f.getName(), f.get(this)).append("\n");  
            }  
        } catch (Exception e) { // Suppress  
            builder.append("toString builder encounter an error");  
        }  
        return builder.toString();  
	}
}
