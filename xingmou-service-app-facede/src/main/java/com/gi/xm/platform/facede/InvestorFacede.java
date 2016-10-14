package com.gi.xm.platform.facede;


import java.util.List;
import java.util.Date;
import com.gi.xm.platform.view.common.MessageInfo;
import com.gi.xm.platform.view.common.QueryResultInfo;
import com.gi.xm.platform.view.InvestorInfo;
import com.gi.xm.platform.view.InvestorQueryInfo;

public interface InvestorFacede {

	MessageInfo<InvestorInfo> getInvestor(Long id);
		

    MessageInfo<QueryResultInfo<InvestorInfo>> queryInvestor(InvestorQueryInfo investorQueryInfo);
	

}