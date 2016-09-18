package com.gi.xm.platform.view;

import java.math.BigDecimal;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class IndustryDataHalfYearInfo implements Serializable {

	/**   
	 * @Fields serialVersionUID : 序列化版本号 
	 */ 
	private static final long serialVersionUID = 1L;

			
	/**
	 * @Fields id : 
	 */
	private Long id;
			
	/**
	 * @Fields yaar : 年份
	 */
	private Integer yaar;
			
	/**
	 * @Fields type : 1上半年 2下半年
	 */
	private Integer type;
			
	/**
	 * @Fields industryId : 一级行业id
	 */
	private Integer industryId;
			
	/**
	 * @Fields industryName : 一级行业名称(冗余)
	 */
	private String industryName;
			
	/**
	 * @Fields industrySubId : 二级行业id
	 */
	private Integer industrySubId;
			
	/**
	 * @Fields industrySubName : 二级行业名称（冗余）
	 */
	private String industrySubName;
			
	/**
	 * @Fields projectNum : 项目数
	 */
	private Integer projectNum;
			
	/**
	 * @Fields investedNum : 已获投项目数
	 */
	private Integer investedNum;
			
	/**
	 * @Fields investedNumRate : 获投增长率(笔数增长率)
	 */
	private Integer investedNumRate;
			
	/**
	 * @Fields investedMoney : 投资金额(元)
	 */
	private Long investedMoney;
			
	/**
	 * @Fields investedMoneyRate : 投资金额增长率
	 */
	private Integer investedMoneyRate;
			
	/**
	 * @Fields investedPerMoney : 平均融资金额(元)
	 */
	private Long investedPerMoney;
			
	/**
	 * @Fields investedMedianMoney : 融资金额中位数(元)
	 */
	private Long investedMedianMoney;
			
	/**
	 * @Fields investedMaxMoney : 最大融资金额（元）
	 */
	private Long investedMaxMoney;
			
	/**
	 * @Fields addTime : 
	 */
	private Timestamp addTime;
			
	/**
	 * @Fields updateTime : 
	 */
	private Timestamp updateTime;
		
    
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
    
	public Integer getYaar(){
		return yaar;
	}
	
	public void setYaar(Integer yaar){
		this.yaar = yaar;
	}
    
	public Integer getType(){
		return type;
	}
	
	public void setType(Integer type){
		this.type = type;
	}
    
	public Integer getIndustryId(){
		return industryId;
	}
	
	public void setIndustryId(Integer industryId){
		this.industryId = industryId;
	}
    
	public String getIndustryName(){
		return industryName;
	}
	
	public void setIndustryName(String industryName){
		this.industryName = industryName;
	}
    
	public Integer getIndustrySubId(){
		return industrySubId;
	}
	
	public void setIndustrySubId(Integer industrySubId){
		this.industrySubId = industrySubId;
	}
    
	public String getIndustrySubName(){
		return industrySubName;
	}
	
	public void setIndustrySubName(String industrySubName){
		this.industrySubName = industrySubName;
	}
    
	public Integer getProjectNum(){
		return projectNum;
	}
	
	public void setProjectNum(Integer projectNum){
		this.projectNum = projectNum;
	}
    
	public Integer getInvestedNum(){
		return investedNum;
	}
	
	public void setInvestedNum(Integer investedNum){
		this.investedNum = investedNum;
	}
    
	public Integer getInvestedNumRate(){
		return investedNumRate;
	}
	
	public void setInvestedNumRate(Integer investedNumRate){
		this.investedNumRate = investedNumRate;
	}
    
	public Long getInvestedMoney(){
		return investedMoney;
	}
	
	public void setInvestedMoney(Long investedMoney){
		this.investedMoney = investedMoney;
	}
    
	public Integer getInvestedMoneyRate(){
		return investedMoneyRate;
	}
	
	public void setInvestedMoneyRate(Integer investedMoneyRate){
		this.investedMoneyRate = investedMoneyRate;
	}
    
	public Long getInvestedPerMoney(){
		return investedPerMoney;
	}
	
	public void setInvestedPerMoney(Long investedPerMoney){
		this.investedPerMoney = investedPerMoney;
	}
    
	public Long getInvestedMedianMoney(){
		return investedMedianMoney;
	}
	
	public void setInvestedMedianMoney(Long investedMedianMoney){
		this.investedMedianMoney = investedMedianMoney;
	}
    
	public Long getInvestedMaxMoney(){
		return investedMaxMoney;
	}
	
	public void setInvestedMaxMoney(Long investedMaxMoney){
		this.investedMaxMoney = investedMaxMoney;
	}
    
	public Timestamp getAddTime(){
		return addTime;
	}
	
	public void setAddTime(Timestamp addTime){
		this.addTime = addTime;
	}
    
	public Timestamp getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Timestamp updateTime){
		this.updateTime = updateTime;
	}
    }