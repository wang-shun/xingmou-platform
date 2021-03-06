package com.gi.ctdn.api.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gi.ctdn.biz.BusinessLineMappingIndustryBiz;
import com.gi.ctdn.biz.EchartsBiz;
import com.gi.ctdn.biz.IndexHeaderStatBiz;
import com.gi.ctdn.biz.IndustryBiz;
import com.gi.ctdn.biz.OrgInfoBiz;
import com.gi.ctdn.biz.ProjectListBiz;
import com.gi.ctdn.biz.me.UserIndustryBiz;
import com.gi.ctdn.pojo.BusinessLineMappingIndustry;
import com.gi.ctdn.pojo.EchartsData;
import com.gi.ctdn.pojo.IndexHeaderStat;
import com.gi.ctdn.pojo.Industry;
import com.gi.ctdn.pojo.IndustryMonth;
import com.gi.ctdn.pojo.IndustryRoundMerger;
import com.gi.ctdn.pojo.OrgInfo;
import com.gi.ctdn.pojo.ProjectList;
import com.gi.ctdn.pojo.me.UserIndustry;
import com.gi.ctdn.view.common.ListUtil;
import com.gi.ctdn.view.common.MessageInfo;
import com.gi.ctdn.view.common.MessageStatus;

/**
 * Created by zcy on 17-9-5.
 */
@Controller
@RequestMapping("index")
public class IndexController implements EnvironmentAware{
	
	private Logger loger = LoggerFactory.getLogger(IndexController.class);


    @Autowired
    private UserIndustryBiz userIndustryBiz;

    @Autowired
    private IndexHeaderStatBiz indexHeaderStatBiz;
    
    @Autowired
    private ProjectListBiz projectListBiz;
    
    @Autowired
   	private OrgInfoBiz orgInfoBiz;
    
    @Autowired
    private IndustryBiz industryBiz;

    
	private String orgCodes = null;
	
	@Autowired
	private EchartsBiz echartsBiz;

	@Resource(name="stringRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private BusinessLineMappingIndustryBiz businessLineMappingIndustryBiz;

	/**
     *查询用户关注行业
     * @return messageInfo
     */
    @RequestMapping("queryUserIndustry/{userCode}")
    @ResponseBody
    public MessageInfo<List<Integer>> getUserIndustry (@PathVariable("userCode")String userCode, HttpServletResponse response, @CookieValue(name = "_uid_")String uid, @CookieValue(name = "s_")String s){
		MessageInfo<List<Integer>> messageInfo = new MessageInfo<>();
		//查询redis中的用户
		String key = "ctdn:"+s+":"+uid;
		String userJson = (String)stringRedisTemplate.opsForValue().get(key);
		if(userJson != null){
			try {
				JSONObject jsonObject = (JSONObject) JSONObject.parse(URLDecoder.decode(userJson,"UTF-8"));
				Long  departmentId =null;
				if(jsonObject.containsKey("departmentId")){
					 departmentId = jsonObject.getLong("departmentId");
				}
				messageInfo = userIndustryBiz.getUserIndustry(userCode,departmentId);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				messageInfo = new MessageInfo<>(MessageStatus.ERROR_CODE,MessageStatus.ERROR_MESSAGE);
			}
		}else{
			messageInfo =  new MessageInfo<>(MessageStatus.NO_LOGIN,MessageStatus.NO_LOGIN_MESSAGE);
		}
        return messageInfo;
    }

	/**
	 * 重置关注行业
	 */
	@RequestMapping("resetUserIndustry/{userCode}")
	@ResponseBody
	public MessageInfo<List<Integer>> resetUserIndustry (@PathVariable("userCode")String userCode, HttpServletResponse response, @CookieValue(name = "_uid_")String uid, @CookieValue(name = "s_")String s){
		MessageInfo<List<Integer>> messageInfo = new MessageInfo<>();
		//查询redis中的用户
		String key = "ctdn:"+s+":"+uid;
		String userJson = (String)stringRedisTemplate.opsForValue().get(key);
		if(userJson != null){
			try {
				JSONObject jsonObject = (JSONObject) JSONObject.parse(URLDecoder.decode(userJson,"UTF-8"));
				Long  departmentId =null;
				if(jsonObject.containsKey("departmentId")){
					departmentId = jsonObject.getLong("departmentId");
					List<Integer> idList = userIndustryBiz.getDefaultIndustry(departmentId);
					if(userIndustryBiz.selectUserIndustry(userCode) != null && ListUtil.isNotEmpty(userIndustryBiz.selectUserIndustry(userCode))){
						userIndustryBiz.deleteUserIndustry(userCode);
					}
					//更改为重置状态
					businessLineMappingIndustryBiz.updateIsEmptyStatus(departmentId,"0");
					messageInfo.setData(idList);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				messageInfo = new MessageInfo<>(MessageStatus.ERROR_CODE,MessageStatus.ERROR_MESSAGE);
			}
		}else{
			messageInfo =  new MessageInfo<>(MessageStatus.NO_LOGIN,MessageStatus.NO_LOGIN_MESSAGE);
		}
		return messageInfo;
	}

	/**
     *设置用户关注行业
     * @return messageInfo
     */
    @RequestMapping("saveUserIndustry")
    @ResponseBody
    public MessageInfo<UserIndustry> updateUserIndustry ( @CookieValue(name = "_uid_")String uid, @CookieValue(name = "s_")String s,@RequestBody UserIndustry userIndustry){
    	String key = "ctdn:"+s+":"+uid;
    	String userJson = stringRedisTemplate.opsForValue().get(key);
        MessageInfo messageInfo = userIndustryBiz.saveOrUpdateUerIndustry(userJson,userIndustry);
		return messageInfo;
	}

    @RequestMapping("queryIndexHeaderStat")
    @ResponseBody
   // @Cacheable(value = "queryIndexHeaderStat",keyGenerator = "baseKG")
    public MessageInfo<IndexHeaderStat> queryIndexHeaderStat (){
        MessageInfo messageInfo = indexHeaderStatBiz.getIndexHeaderStat();
        return messageInfo;
    }
    

    /**
     * 获取最新发现项目
     * @return
     */
    @RequestMapping("queryLastestLoadProject/{userCode}")
    @ResponseBody
    public MessageInfo<List<ProjectList>> queryLastestLoadProject (@CookieValue(name = "_uid_")String uid, @CookieValue(name = "s_")String s,@PathVariable("userCode")String userCode){
		MessageInfo<List<ProjectList>> resultMessageInfo = new MessageInfo<List<ProjectList>>();
		try {
			//查询redis中的用户
			String key = "ctdn:"+s+":"+uid;
			String userJson = (String)stringRedisTemplate.opsForValue().get(key);
			Long  departmentId =null;
			if(userJson != null){
				JSONObject jsonObject = (JSONObject) JSONObject.parse(URLDecoder.decode(userJson,"UTF-8"));
				if(jsonObject.containsKey("departmentId")){
					 departmentId = jsonObject.getLong("departmentId");
				}
			}
			List<ProjectList> projectLists  = projectListBiz.selectByLoadDate(userCode,departmentId);
			resultMessageInfo.setData(projectLists);
		} catch (Exception e) {
			loger.error(" 获取最新发现项目,error:" + e.getMessage());
			resultMessageInfo.setStatus(0);
		}
		return resultMessageInfo;
    }

    /**
     * 获取最新获投项目
     * @return
     */
    @RequestMapping("queryLastestFinanceProject/{userCode}")
    @ResponseBody
    public MessageInfo<List<ProjectList>> queryLastestFinanceProject (@CookieValue(name = "_uid_")String uid, @CookieValue(name = "s_")String s,@PathVariable("userCode")String userCode){
		MessageInfo<List<ProjectList>> resultMessageInfo = new MessageInfo<List<ProjectList>>();
		try {
			//查询redis中的用户
			String key = "ctdn:"+s+":"+uid;
			String userJson = (String)stringRedisTemplate.opsForValue().get(key);
			Long  departmentId =null;
			if(userJson != null){
				JSONObject jsonObject = (JSONObject) JSONObject.parse(URLDecoder.decode(userJson,"UTF-8"));
				if(jsonObject.containsKey("departmentId")){
					 departmentId = jsonObject.getLong("departmentId");
				}
			}
			List<ProjectList> projectLists  = projectListBiz.queryLastestFinanceProject(userCode,departmentId);
			resultMessageInfo.setData(projectLists);
		} catch (Exception e) {
			e.printStackTrace();
			loger.error("获取最新获投项目,error:" + e.getMessage());
			resultMessageInfo.setStatus(0);
		}
		return resultMessageInfo;
    }
    
    /**
     * 获取上个月活跃机构
     * @return
     */
    @RequestMapping("queryLastestOrg")
    @ResponseBody
    public MessageInfo<List<OrgInfo>> getAllOrgInfo(){
		MessageInfo<List<OrgInfo>>  messageInfo = new MessageInfo<List<OrgInfo>>();
		try {
			List<OrgInfo> orgList = orgInfoBiz.getLatestOrg();
			messageInfo.setData(orgList);
		} catch (Exception e) {
			loger.error("获取上个月活跃机构,error:" + e.getMessage());
			messageInfo.setStatus(0);
		}
		return messageInfo;
	}
    
    
    /**
     * 获取一级行业机构
     * @return
     */
    @RequestMapping("getParentIndustrys")
    @ResponseBody
    public MessageInfo<List<Industry>> getParentIndustrys(@CookieValue(name = "_uid_")String uid, @CookieValue(name = "s_")String s,@RequestBody UserIndustry userIndustry){
		MessageInfo<List<Industry>>  messageInfo = new MessageInfo<List<Industry>>();
		try {
			String key = "ctdn:"+s+":"+uid;
//			StringRedisTemplate  tempStringRedisTemplate= new StringRedisTemplate();
//			BeanUtils.copyProperties(tempStringRedisTemplate, stringRedisTemplate);
//			tempStringRedisTemplate.setValueSerializer(new StringRedisSerializer());
//			tempStringRedisTemplate.afterPropertiesSet();
			String userJson = stringRedisTemplate.opsForValue().get(key);
			Long  departmentId =null;
			if(userJson!=null){
				JSONObject jsonObject = (JSONObject) JSONObject.parse(URLDecoder.decode(userJson,"UTF-8"));
				if(jsonObject.containsKey("departmentId")){
					 departmentId = jsonObject.getLong("departmentId");
				}
			}
			List<Industry> industryList = industryBiz.getParentindustrys(userIndustry,departmentId);
			messageInfo.setData(industryList);
		} catch (Exception e) {
			loger.error("获取投资经理首页一级行业失败,error:" + e.getMessage());
			messageInfo.setStatus(0);
		}
		return messageInfo;
	}

    /**
     * 保存或更新用户关注行业
     * @return
     */
    @RequestMapping("saveOrUpdateUerIndustry")
    @ResponseBody
    public MessageInfo<UserIndustry> saveOrUpdateUerIndustry(@CookieValue(name = "_uid_")String uid, @CookieValue(name = "s_")String s,@RequestBody UserIndustry userIndustry){
		MessageInfo<UserIndustry>  messageInfo = new MessageInfo<UserIndustry>();
		try {
		  	String key = "ctdn:"+s+":"+uid;
	    	String userJson = stringRedisTemplate.opsForValue().get(key);
		    userIndustryBiz.saveOrUpdateUerIndustry(userJson,userIndustry);
		} catch (Exception e) {
			loger.error("保存或更新用户关注行业,error:" + e.getMessage());
			messageInfo.setStatus(0);
		}
		return messageInfo;
	}
    
    
//=========================================高管============================================================
    
    
    /**
     * 获取竞争动态
     * @return
     */
    @RequestMapping("getCompeteInfo")
    @ResponseBody
    public MessageInfo<List<OrgInfo>> getCompeteInfo(){
		MessageInfo<List<OrgInfo>>  messageInfo = new MessageInfo<List<OrgInfo>>();
		try {
			List<OrgInfo> orgInfoList = orgInfoBiz.getCompeteInfo(orgCodes);
			messageInfo.setData(orgInfoList);
		} catch (Exception e) {
			loger.error("获取高管数据模块,error:" + e.getMessage());
			messageInfo.setStatus(0);
		}
		return messageInfo;
	}

    @RequestMapping("queryGGCurMonthHeaderStat")
    @ResponseBody
    public MessageInfo<IndexHeaderStat> queryGGHeaderStat (){
        MessageInfo<IndexHeaderStat> messageInfo = indexHeaderStatBiz.getGGCurMonthHeaderStat();
        return messageInfo;
    }

	/**
	 * 传值可通用
	 * @param indexHeaderStat
	 * @return
	 */
	@RequestMapping("queryHeaderStatCommon")
	@ResponseBody
	public MessageInfo<IndexHeaderStat> queryHeaderStatCommon (@RequestBody IndexHeaderStat indexHeaderStat){
		if(indexHeaderStat.getType()== null){
			return new MessageInfo<>(MessageStatus.MISS_PARAMETER_CODE,MessageStatus.MISS_PARAMETER_MESSAGE);
		}
		MessageInfo<IndexHeaderStat> messageInfo = indexHeaderStatBiz.queryHeaderStatCommon(indexHeaderStat);
		return messageInfo;
	}
    
    @RequestMapping("queryIndustryMonthForEchart")
    @ResponseBody
    public MessageInfo<EchartsData<IndustryMonth>> queryIndustryMonthForEchart (){
    	MessageInfo<EchartsData<IndustryMonth>> messageInfo = new MessageInfo<EchartsData<IndustryMonth>>();
		try {
			messageInfo = echartsBiz.getIndustryMonthForEchart();
		} catch (Exception e) {
			e.printStackTrace();
			loger.error("获取高管行业投资趋势,error:" + e.getMessage());
			messageInfo.setStatus(9999);
		}
        return messageInfo;
    }
    
    /**
     * 高管首页 ---行业融资对比
     * @return
     */
    @RequestMapping("queryIndustryMonthMergerForEchart")
    @ResponseBody
    public MessageInfo<EchartsData<IndustryRoundMerger>> queryIndustryMonthMergerForEchart (){
    	MessageInfo<EchartsData<IndustryRoundMerger>> messageInfo = new MessageInfo<EchartsData<IndustryRoundMerger>>();
		try {
			messageInfo = echartsBiz.getIndustryRoundMergerForEcharts();
		} catch (Exception e) {
			e.printStackTrace();
			loger.error("获取高管行业融资对比,error:" + e.getMessage());
			messageInfo.setStatus(9999);
		}
        return messageInfo;
    }
    
    
    

	@Override
	public void setEnvironment(Environment environment) {
		orgCodes = environment.getProperty("fixed_orgCode");
	}
	
	@RequestMapping("getUserIndustry")
	@ResponseBody
    public MessageInfo<UserIndustry> getUserIndustry (@CookieValue(name = "_uid_")String uid, @CookieValue(name = "s_")String s,@RequestBody UserIndustry userIndustry){
    	MessageInfo<UserIndustry> messageInfo = new MessageInfo<UserIndustry>();
		try {
			String key = "ctdn:"+s+":"+uid;
//			StringRedisTemplate  tempStringRedisTemplate= new StringRedisTemplate();
//			BeanUtils.copyProperties(tempStringRedisTemplate, stringRedisTemplate);
//			tempStringRedisTemplate.setValueSerializer(new StringRedisSerializer());
//			tempStringRedisTemplate.afterPropertiesSet();
			String userJson = stringRedisTemplate.opsForValue().get(key);
//			tempStringRedisTemplate = null;
			Long  departmentId =null;
			if(userJson!=null){
				JSONObject jsonObject = (JSONObject) JSONObject.parse(URLDecoder.decode(userJson,"UTF-8"));
				if(jsonObject.containsKey("departmentId")){
					 departmentId = jsonObject.getLong("departmentId");
				}
			}
			messageInfo = userIndustryBiz.getUserIndustryIdsAndNames(userIndustry,departmentId);
		} catch (Exception e) {
			e.printStackTrace();
			loger.error("获取高管行业融资对比,error:" + e.getMessage());
			messageInfo.setStatus(9999);
		}
        return messageInfo;
    }
	
	   
    //获取高管事业线
    @RequestMapping("getBusinessLineMappingIndustry")
    @ResponseBody
    public MessageInfo<List<BusinessLineMappingIndustry>> getBusinessLineMappingIndustry (){
    	MessageInfo<List<BusinessLineMappingIndustry>> messageInfo = new MessageInfo<List<BusinessLineMappingIndustry>>();
		try {
			messageInfo = businessLineMappingIndustryBiz.getBusinessLineMappingIndustry();
		} catch (Exception e) {
			e.printStackTrace();
			loger.error("获取高管事业线失败,error:" + e.getMessage());
			messageInfo.setStatus(9999);
		}
        return messageInfo;
    }
}
