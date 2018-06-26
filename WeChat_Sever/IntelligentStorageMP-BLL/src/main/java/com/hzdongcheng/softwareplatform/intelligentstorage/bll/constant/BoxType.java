package com.hzdongcheng.softwareplatform.intelligentstorage.bll.constant;

/**
 * Created by ysk on 2016/10/28.
 */
public enum BoxType {
   /**      
                 
   */
	BoxType_status_0("正常", Byte.parseByte("0")),
	BoxType_status_1("锁定", Byte.parseByte("1")),
	BoxType_status_2("故障", Byte.parseByte("2")),
 
	
 
	
	
	;
    // 成员变量
    private String name;
    private Byte value;

    // 构造方法
    private BoxType(String name, Byte value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(Byte value) {
        for (BoxType c : BoxType.values()) {
            if (c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }
    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
 
    public Byte getValue() {
		return value;
	}
    
    public String getStrValue() {
		return value.toString();
	}
    
	public void setValue(Byte value) {
		this.value = value;
	}

	public static void main(String[] args) {
       System.out.println(BoxType.BoxType_status_0.getValue()+"     "+BoxType.BoxType_status_0.getName());
 
    }


}
