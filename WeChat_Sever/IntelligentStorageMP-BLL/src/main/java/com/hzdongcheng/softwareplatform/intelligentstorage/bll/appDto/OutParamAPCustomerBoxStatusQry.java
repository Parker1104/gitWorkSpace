package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 箱状态查询
*/

public class OutParamAPCustomerBoxStatusQry implements java.io.Serializable
{
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String BoxNo = ""; //箱门编号
	public String BoxName = ""; //箱门名称
	public String BoxType = ""; //箱类型编号
	public String BoxTypeName = ""; //箱类型名称
	public int DeskNo; //副柜编号
	public int DeskBoxNo; //箱门副柜编号
	public String BoxStatus = ""; //箱状态
	public String BoxStatusName = ""; //箱状态名称
	public String LockStatus = ""; //锁定状态
	public String FaultStatus = ""; //故障状态
	public int Boxtapystatus; //预支付状态
	
	public int getPaystatus() {
		return Boxtapystatus;
	}
	public void setPaystatus(int boxtapystatus) {
		Boxtapystatus = boxtapystatus;
	}
	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getTerminalName()
	{
		return TerminalName;
	}
	public void setTerminalName(String TerminalName)
	{
		this.TerminalName = TerminalName;
	}

	public String getBoxNo()
	{
		return BoxNo;
	}
	public void setBoxNo(String BoxNo)
	{
		this.BoxNo = BoxNo;
	}

	public String getBoxName()
	{
		return BoxName;
	}
	public void setBoxName(String BoxName)
	{
		this.BoxName = BoxName;
	}

	public String getBoxType()
	{
		return BoxType;
	}
	public void setBoxType(String BoxType)
	{
		this.BoxType = BoxType;
	}

	public String getBoxTypeName()
	{
		return BoxTypeName;
	}
	public void setBoxTypeName(String BoxTypeName)
	{
		this.BoxTypeName = BoxTypeName;
	}

	public int getDeskNo()
	{
		return DeskNo;
	}
	public void setDeskNo(int DeskNo)
	{
		this.DeskNo = DeskNo;
	}

	public int getDeskBoxNo()
	{
		return DeskBoxNo;
	}
	public void setDeskBoxNo(int DeskBoxNo)
	{
		this.DeskBoxNo = DeskBoxNo;
	}

	public String getBoxStatus()
	{
		return BoxStatus;
	}
	public void setBoxStatus(String BoxStatus)
	{
		this.BoxStatus = BoxStatus;
	}

	public String getBoxStatusName()
	{
		return BoxStatusName;
	}
	public void setBoxStatusName(String BoxStatusName)
	{
		this.BoxStatusName = BoxStatusName;
	}

	public String getLockStatus()
	{
		return LockStatus;
	}
	public void setLockStatus(String LockStatus)
	{
		this.LockStatus = LockStatus;
	}

	public String getFaultStatus()
	{
		return FaultStatus;
	}
	public void setFaultStatus(String FaultStatus)
	{
		this.FaultStatus = FaultStatus;
	}

}