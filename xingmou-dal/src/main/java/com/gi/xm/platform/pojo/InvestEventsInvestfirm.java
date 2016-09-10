package com.gi.xm.platform.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Timestamp;
import java.util.Date;

public class InvestEventsInvestfirm extends Pojo {

        public static final String ID = "id";
        public static final String EVENT_ID = "事件id";
        public static final String INVESTFIRM_ID = "机构id";
        public static final String INVESTFIRM_NAME = "冗余：机构名称";
        public static final String ADD_TIME = "addTime";
        public static final String UPDATE_TIME = "updateTime";
    
	
	/**
	 * @Fields id : 
	 */
	private Long id;
	
	/**
	 * @Fields eventId : 事件id
	 */
	private Long eventId;
	
	/**
	 * @Fields investfirmId : 机构id
	 */
	private Long investfirmId;
	
	/**
	 * @Fields investfirmName : 冗余：机构名称
	 */
	private String investfirmName;
	
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
    
	public Long getEventId(){
		return eventId;
	}

	public void setEventId(Long eventId){
		this.eventId = eventId;
	}
    
	public Long getInvestfirmId(){
		return investfirmId;
	}

	public void setInvestfirmId(Long investfirmId){
		this.investfirmId = investfirmId;
	}
    
	public String getInvestfirmName(){
		return investfirmName;
	}

	public void setInvestfirmName(String investfirmName){
		this.investfirmName = investfirmName;
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