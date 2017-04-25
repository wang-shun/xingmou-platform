package com.gi.ctdn.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.gi.xm.platform.view.common.PojoInfo;

public class ListingType extends PojoInfo {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String TYPEID = "typeId";
        public static final String TYPENAME = "typeName";
        public static final String PARENTID = "parentId";
        public static final String VAL = "val";
        public static final String DATA = "data";
    
	
	/**
	 * @Fields id : 
	 */
	private Integer id;
	
	/**
	 * @Fields name : 
	 */
	private String name;
	
	/**
	 * @Fields typeId : 
	 */
	private Integer typeId;
	
	/**
	 * @Fields typeName : 
	 */
	private String typeName;
	
	/**
	 * @Fields parentId : 
	 */
	private Integer parentId;
	
	/**
	 * @Fields val : 
	 */
	private Integer val;

	/**
	 * @Fields listingTypeList :
	 */
	private List<ListingType> listingTypeList;
	
    
	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}
    
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
    
	public Integer getTypeId(){
		return typeId;
	}

	public void setTypeId(Integer typeId){
		this.typeId = typeId;
	}
    
	public String getTypeName(){
		return typeName;
	}

	public void setTypeName(String typeName){
		this.typeName = typeName;
	}
    
	public Integer getParentId(){
		return parentId;
	}

	public void setParentId(Integer parentId){
		this.parentId = parentId;
	}
    
	public Integer getVal(){
		return val;
	}

	public void setVal(Integer val){
		this.val = val;
	}

	public List getListingTypeList() { return listingTypeList; }

	public void setListingTypeList(List list) { this.listingTypeList = list; }
    }