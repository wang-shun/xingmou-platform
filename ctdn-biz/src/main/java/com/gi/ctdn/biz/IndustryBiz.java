

package com.gi.ctdn.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gi.ctdn.dao.IndustryDAO;
import com.gi.xm.platform.view.common.MessageStatus;
import com.gi.xm.platform.view.common.MessageInfo;

import com.gi.ctdn.pojo.Industry;

@Service("industryBiz")
public class IndustryBiz  {


	private static final Logger LOGGER = LoggerFactory.getLogger(IndustryBiz.class);

    @Autowired
	IndustryDAO industryDAO;


	


    public MessageInfo<List<Industry>> getAllIndustry(){

		MessageInfo<List<Industry>> messageInfo = new MessageInfo<List<Industry>>();
		try {
			List<Industry> industryList = industryDAO.selectAll();
			messageInfo.setData( industryList);
		} catch (Exception e) {
			LOGGER.error("getAllIndustry","查询全部Industry失败", e);
			messageInfo.setStatus(10001);
		}
		return messageInfo;
	}
}