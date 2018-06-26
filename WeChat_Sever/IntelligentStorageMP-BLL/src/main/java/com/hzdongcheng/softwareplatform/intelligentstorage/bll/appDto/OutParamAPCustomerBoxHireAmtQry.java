package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 格口租金标准查询
*/

public class OutParamAPCustomerBoxHireAmtQry implements java.io.Serializable
{
	public String ChargeType = ""; //收费类型（4-按时，5-按天，6-按周，7-按月，9-按年）
	public int Amount; //金额，单位：分
	public int Num; //金额对应的租用时长（默认1）
	public String NumUnit = ""; //租用时长单位：根据收费类型，小时，天，周，月，年
	public String ModifyFlag = ""; //租用时长是否允许修改标志，0-不允许，1-允许用户设置租用时长
	public String Remark = ""; //备注

	public String getChargeType()
	{
		return ChargeType;
	}
	public void setChargeType(String ChargeType)
	{
		this.ChargeType = ChargeType;
	}

	public int getAmount()
	{
		return Amount;
	}
	public void setAmount(int Amount)
	{
		this.Amount = Amount;
	}

	public int getNum()
	{
		return Num;
	}
	public void setNum(int Num)
	{
		this.Num = Num;
	}

	public String getNumUnit()
	{
		return NumUnit;
	}
	public void setNumUnit(String NumUnit)
	{
		this.NumUnit = NumUnit;
	}

	public String getModifyFlag()
	{
		return ModifyFlag;
	}
	public void setModifyFlag(String ModifyFlag)
	{
		this.ModifyFlag = ModifyFlag;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String Remark)
	{
		this.Remark = Remark;
	}

}