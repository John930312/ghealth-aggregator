package com.todaysoft.ghealth.portal.mgmt.facade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Agency;
import com.todaysoft.ghealth.base.response.model.AgencyDetails;
import com.todaysoft.ghealth.mgmt.request.MaintainAgencyRequest;
import com.todaysoft.ghealth.mgmt.request.QueryAgenciesRequest;
import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.AgencyBill;
import com.todaysoft.ghealth.mybatis.model.AgencyPrepay;
import com.todaysoft.ghealth.mybatis.model.AgencyProduct;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.searcher.AgencyProductSearcher;
import com.todaysoft.ghealth.mybatis.searcher.AgencySearcher;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IAgencyProductService;
import com.todaysoft.ghealth.service.IAgencyService;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.wrapper.AgencyProductWrapper;
import com.todaysoft.ghealth.service.wrapper.AgencyWrapper;
import com.todaysoft.ghealth.utils.DataStatus;
import com.todaysoft.ghealth.utils.IdGen;

@Component
public class AgencyMgmtFacade
{
    @Autowired
    private IAgencyService agencyService;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IAgencyProductService agencyProductService;
    
    @Autowired
    private AgencyWrapper agencyWrapper;
    
    @Autowired
    private AgencyProductWrapper agencyProductWrapper;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private AgencyBillFacade agencyBillFacade;
    
    @Autowired
    private AgencyPrepayFacade agencyPrepayFacade;
    
    public PagerResponse<Agency> pager(QueryAgenciesRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        AgencySearcher searcher = new AgencySearcher();
        agencyWrapper.wrapperSearcher(searcher,request);
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Agency> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Agency>(agencyService);
        Pager<com.todaysoft.ghealth.mybatis.model.Agency> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<Agency> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), agencyWrapper.wrap(pager.getRecords()));
        return new PagerResponse<Agency>(result);
    }
    
    public ObjectResponse<AgencyDetails> getDetails(QueryDetailsRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new IllegalArgumentException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Agency agency = agencyService.get(request.getId());
        
        if (null == agency)
        {
            return new ObjectResponse<AgencyDetails>(null);
        }
        
        AgencyProductSearcher searcher = new AgencyProductSearcher();
        searcher.setAgencyId(agency.getId());
        List<AgencyProduct> records = agencyProductService.query(searcher, 0, -1);
        
        AgencyDetails details = agencyWrapper.wrap(agency);
        List<String> names = agencyService.getSubAccount(details.getId());
        details.setSubAccount(names);
        details.setAgentProducts(agencyProductWrapper.wrap(records));
        return new ObjectResponse<AgencyDetails>(details);
    }
    
    public ObjectResponse<Boolean> isUsernameUnique(MaintainAgencyRequest request)
    {
        boolean unique = agencyService.isUsernameUnique(request.getPrimaryUsername());
        return new ObjectResponse<Boolean>(unique);
    }
    
    public ObjectResponse<Boolean> isCodeUnique(MaintainAgencyRequest request)
    {
        boolean unique = agencyService.isCodeUnique(request.getId(), request.getCode());
        return new ObjectResponse<Boolean>(unique);
    }
    
    public void create(MaintainAgencyRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Agency agency = new com.todaysoft.ghealth.mybatis.model.Agency();
        BeanUtils.copyProperties(request, agency);
        agency.setId(IdGen.uuid());
        agency.setAccountAmount(BigDecimal.ZERO);
        agency.setAmountSignature("A");
        agency.setDeleted(false);
        agency.setCreateTime(new Date());
        agency.setCreatorName(account.getName());
        
        AgencyAccount agencyAccount = new AgencyAccount();
        agencyAccount.setId(IdGen.uuid());
        agencyAccount.setAgencyId(agency.getId());
        agencyAccount.setName(agency.getContactName());
        agencyAccount.setPhone(agency.getContactPhone());
        agencyAccount.setUsername(agency.getPrimaryUsername());
        agencyAccount.setPassword(request.getPrimaryAccountPassword());
        agencyAccount.setEnabled(true);
        agencyAccount.setPrimaryAccount(true);
        agencyAccount.setDeleted(false);
        agencyAccount.setCreateTime(new Date());
        agencyAccount.setCreatorName(account.getName());
        agencyService.create(agency, agencyAccount);
    }
    
    public void modify(MaintainAgencyRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new IllegalArgumentException();
        }
        
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Agency agency = agencyService.get(request.getId());
        agency.setCode(request.getCode());
        agency.setAbbr(request.getAbbr());
        agency.setName(request.getName());
        agency.setProvince(request.getProvince());
        agency.setCity(request.getCity());
        agency.setAddress(request.getAddress());
        agency.setContactName(request.getContactName());
        agency.setContactPhone(request.getContactPhone());
        agency.setContactEmail(request.getContactEmail());
        agency.setRemark(request.getRemark());
        agency.setUpdateTime(new Date());
        agency.setUpdatorName(account.getName());
        agency.setAuthorizationAmount(request.getAuthorizationAmount());
        agency.setAuthorizationAmountFlag(request.isAuthorizationAmountFlag());

        AgencyAccount agencyAccount = agencyService.getPrimaryAccount(request.getId());
        if (null != agencyAccount)
        {
            if (StringUtils.isNotEmpty(request.getPrimaryAccountPassword()))
            {
                agencyAccount.setPassword(request.getPrimaryAccountPassword());
            }
            else
            {
                agencyAccount.setPassword(null);
            }
            if (StringUtils.isNotEmpty(request.getContactName()))
            {
                agencyAccount.setName(request.getContactName());
            }
            if (StringUtils.isNotEmpty(request.getContactPhone()))
            {
                agencyAccount.setPhone(request.getContactPhone());
            }
            agencyAccount.setUpdateTime(new Date());
            agencyAccount.setUpdatorName(account.getName());
        }
        agencyService.modify(agency, agencyAccount);
    }
    
    public ObjectResponse<Boolean> delete(MaintainAgencyRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new IllegalArgumentException();
        }
        
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        OrderSearcher searcher = new OrderSearcher();
        searcher.setAgencyId(request.getId());
        List<Order> orders = orderService.list(searcher);
        if (CollectionUtils.isEmpty(orders))
        {
            com.todaysoft.ghealth.mybatis.model.Agency agency = agencyService.get(request.getId());
            agency.setDeleted(true);
            agency.setDeleteTime(new Date());
            agency.setDeletorName(account.getName());
            AgencyAccount agencyAccount = new AgencyAccount();
            agencyAccount.setDeleted(true);
            agencyAccount.setDeletorName(account.getName());
            agencyAccount.setDeleteTime(new Date());
            agencyService.modify(agency, agencyAccount);
            return new ObjectResponse<>(true);
        }
        return new ObjectResponse<>(false);
    }
    
    public ListResponse<Agency> list(QueryAgenciesRequest request)
    {
        AgencySearcher searcher = new AgencySearcher();
        searcher.setCode(request.getCode());
        searcher.setName(request.getName());
        return new ListResponse<Agency>(agencyWrapper.wrap(agencyService.list(searcher)));
    }
    
    @Transactional
    public void recharge(MaintainAgencyRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        if (null == account)
        {
            throw new IllegalStateException();
        }
        if (null == request.getAccountAmount())
        {
            throw new ServiceException("amount can not be null");
        }
        if (null == request.getId())
        {
            throw new ServiceException("id can not be null");
        }
        com.todaysoft.ghealth.mybatis.model.Agency agency = agencyService.get(request.getId());
        AgencyPrepay agencyPrepay = insertAgencyPrepay(request, account);
        insertAgencyBill(agency, request.getAccountAmount(), account, agencyPrepay.getId(),request.getRechargeType());
        BigDecimal amount = request.getAccountAmount().add(agency.getAccountAmount());
        agency.setAccountAmount(amount);
        agency.setUpdatorName(account.getName());
        agency.setUpdateTime(new Date());
        request.setId(null);
        agencyService.modify(agency, null);
    }
    
    public AgencyPrepay insertAgencyPrepay(MaintainAgencyRequest request, ManagementAccountDetails account)
    {
        AgencyPrepay agencyPrepay = new AgencyPrepay();
        agencyPrepay.setAgencyId(request.getId());
        agencyPrepay.setCreatorName(account.getName());
        agencyPrepay.setAmount(request.getAccountAmount());
        agencyPrepayFacade.create(agencyPrepay);
        return agencyPrepay;
    }
    
    public void insertAgencyBill(com.todaysoft.ghealth.mybatis.model.Agency agency, BigDecimal balance, ManagementAccountDetails account, String agencyPrepayId,String rechargeType)
    {
        if (balance.compareTo(new BigDecimal(0)) != 0)
        {
            BigDecimal accountAmount = agency.getAccountAmount();
            BigDecimal accountAmountAfter = balance.add(accountAmount);
            AgencyBill agencyBill = new AgencyBill();
            agencyBill.setAgency(agency);
            agencyBill.setBillType(DataStatus.BILL_ADVANCE);
            agencyBill.setIncreased(DataStatus.BALANCE_PLUS);
            agencyBill.setAmountAfter(accountAmountAfter);
            agencyBill.setAmountBefore(accountAmount);
            agencyBill.setEventDetails(agencyPrepayId);
            switch (Integer.parseInt(rechargeType))
            {
                case 1:
                    agencyBill.setTitle("现金充值" + balance + "元");
                    break;
                case 2:
                    agencyBill.setTitle("销账充值" + balance + "元");
                    break;
                case 3:
                    agencyBill.setTitle("退款充值" + balance + "元");
                    break;
            }
            agencyBill.setRechargeType(rechargeType);
            agencyBill.setBillTime(new Date());
            agencyBill.setOperateName(account.getName());
            agencyBillFacade.create(agencyBill);
        }
    }
}
