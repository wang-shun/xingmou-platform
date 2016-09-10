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
import com.gi.xm.platform.view.AreaInfo;
import com.gi.xm.platform.view.AreaQueryInfo;
import com.gi.xm.platform.facede.AreaFacede;

@Controller
@RequestMapping("area")
public class AreaController {

    @Reference
	private AreaFacede areaFacede;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView index() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		return new ModelAndView("area/index", modelMap);
	}

	@RequestMapping("query")
	@ResponseBody
	public MessageInfo<QueryResultInfo<AreaInfo>>  queryArea (AreaQueryInfo areaQueryInfo) {
		MessageInfo<QueryResultInfo<AreaInfo>> resultMessageInfo = areaFacede.queryArea(areaQueryInfo);
		return resultMessageInfo;
	}

    @RequestMapping("create")
    @ResponseBody
    public MessageInfo<Long> createArea(AreaInfo areaInfo){
        MessageInfo<Long> messageInfo =  areaFacede.createArea(areaInfo);
        return messageInfo;
    }

    @RequestMapping("update")
    @ResponseBody
	public MessageInfo<Integer> updateArea(AreaInfo areaInfo){
		MessageInfo<Integer> messageInfo =  areaFacede.updateArea(areaInfo);
		return messageInfo;
	}

    @RequestMapping("get")
    @ResponseBody
	public MessageInfo<AreaInfo> getArea( Long id ){
		MessageInfo<AreaInfo> messageInfo =  areaFacede.getArea(id);
		return messageInfo;
	}

    @RequestMapping("getAll")
    @ResponseBody
    public MessageInfo<List<AreaInfo>> getAllArea(){
		MessageInfo<List<AreaInfo>>  messageInfo = areaFacede.getAllArea();
		return messageInfo;
	}


	
}