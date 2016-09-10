package com.gi.xm.platform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Reference;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gi.xm.platform.view.common.MessageInfo;
import com.gi.xm.platform.view.common.QueryResultInfo;
import com.gi.xm.platform.view.InvestorInfo;
import com.gi.xm.platform.view.InvestorQueryInfo;
import com.gi.xm.platform.facede.InvestorFacede;

@Controller
@RequestMapping("investor")
public class InvestorController {

    @Reference
	private InvestorFacede investorFacede;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView index() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		return new ModelAndView("investor/index", modelMap);
	}

	@RequestMapping("query")
	@ResponseBody
	public MessageInfo<QueryResultInfo<InvestorInfo>>  queryInvestor (InvestorQueryInfo investorQueryInfo) {
		MessageInfo<QueryResultInfo<InvestorInfo>> resultMessageInfo = investorFacede.queryInvestor(investorQueryInfo);
		return resultMessageInfo;
	}

    @RequestMapping("create")
    @ResponseBody
    public MessageInfo<Long> createInvestor(InvestorInfo investorInfo){
        MessageInfo<Long> messageInfo =  investorFacede.createInvestor(investorInfo);
        return messageInfo;
    }

    @RequestMapping("update")
    @ResponseBody
	public MessageInfo<Integer> updateInvestor(InvestorInfo investorInfo){
		MessageInfo<Integer> messageInfo =  investorFacede.updateInvestor(investorInfo);
		return messageInfo;
	}

    @RequestMapping("get")
    @ResponseBody
	public MessageInfo<InvestorInfo> getInvestor( Long id ){
		MessageInfo<InvestorInfo> messageInfo =  investorFacede.getInvestor(id);
		return messageInfo;
	}

    @RequestMapping("getAll")
    @ResponseBody
    public MessageInfo<List<InvestorInfo>> getAllInvestor(){
		MessageInfo<List<InvestorInfo>>  messageInfo = investorFacede.getAllInvestor();
		return messageInfo;
	}


	
}