package com.gi.ctdn.api.rest;

import java.util.List;


import com.gi.ctdn.biz.OrgMediaInfoBiz;
import com.gi.ctdn.pojo.OrgMediaInfo;
import com.gi.ctdn.view.common.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("orgMediaInfo")
public class OrgMediaInfoController {

    @Autowired
	private OrgMediaInfoBiz orgMediaInfoBiz;


    @RequestMapping("getAll")
    @ResponseBody
    public MessageInfo<List<OrgMediaInfo>> getAllOrgMediaInfo(){
		MessageInfo<List<OrgMediaInfo>>  messageInfo = orgMediaInfoBiz.getAllOrgMediaInfo();
		return messageInfo;
	}


		
    @RequestMapping("getListByOrgId")
    @ResponseBody
	public MessageInfo<List<OrgMediaInfo>> getListByOrgId(String orgId){
		MessageInfo<List<OrgMediaInfo>> messageInfo = orgMediaInfoBiz.getListByOrgId(orgId);
		return messageInfo;
	}
		
}
