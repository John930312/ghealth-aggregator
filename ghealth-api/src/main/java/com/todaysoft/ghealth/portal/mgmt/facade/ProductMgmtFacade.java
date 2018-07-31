package com.todaysoft.ghealth.portal.mgmt.facade;

import com.todaysoft.ghealth.mybatis.model.AgencyProductOldPrice;
import com.todaysoft.ghealth.mybatis.searcher.AgencyProductOldPriceSearcher;
import com.todaysoft.ghealth.service.IAgencyProductOldPriceService;
import com.todaysoft.ghealth.service.ITestingProductService;
import com.todaysoft.ghealth.service.wrapper.AgencyProductOldPriceWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.AgencyProduct;
import com.todaysoft.ghealth.mgmt.request.MaintainProductAgencyRequest;
import com.todaysoft.ghealth.mgmt.request.QueryProductAgencyRequest;
import com.todaysoft.ghealth.mybatis.model.Agency;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.model.Product;
import com.todaysoft.ghealth.mybatis.searcher.AgencyProductSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IAgencyProductService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.AgencyProductWrapper;
import com.todaysoft.ghealth.utils.IdGen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class ProductMgmtFacade
{
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private AgencyProductWrapper wrapper;
    
    @Autowired
    private IAgencyProductService service;

    @Autowired
    private ITestingProductService testingProductService;

    @Autowired
    private IAgencyProductOldPriceService agencyProductOldPriceService;

    @Autowired
    private AgencyProductOldPriceWrapper agencyProductOldPriceWrapper;
    
    public PagerResponse<AgencyProduct> pager(QueryProductAgencyRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        AgencyProductSearcher searcher = new AgencyProductSearcher();
        searcher.setProductName(request.getProductName());
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyProduct> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyProduct>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.AgencyProduct> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<AgencyProduct> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<AgencyProduct>(result);
    }
    
    public void allocate(MaintainProductAgencyRequest request)
    {
        
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        if (StringUtils.isNotEmpty(request.getAgencyId()))
        {
            String[] agencyIds = request.getAgencyId().split(",");
            for (String agenctId : agencyIds)
            {
                AgencyProductSearcher searcher = new AgencyProductSearcher();
                searcher.setAgencyId(agenctId);
                searcher.setProductId(request.getTestingProductId());
                service.delete(searcher);
                com.todaysoft.ghealth.mybatis.model.AgencyProduct data = new com.todaysoft.ghealth.mybatis.model.AgencyProduct();
                data.setId(IdGen.uuid());
                Product p = new Product();
                p.setId(request.getTestingProductId());
                data.setProduct(p);
                Agency a = new Agency();
                a.setId(agenctId);
                data.setAgency(a);
                data.setAgencyPrice(request.getAgencyPrice());
                service.allocate(data);
            }
        }
    }
    
    public ListResponse<AgencyProduct> getProductAgentsByProductId(QueryProductAgencyRequest request)
    {
        AgencyProductSearcher searcher = new AgencyProductSearcher();
        searcher.setProductId(request.getProductId());
        return new ListResponse<>(wrapper.wrap(service.list(searcher)));
    }
    
    public void modify(MaintainProductAgencyRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        if (StringUtils.isNotEmpty(request.getAgencyIds()))
        {
            String split = "-";
            for (String agencyId : request.getAgencyIds().split(split))
            {
                AgencyProductSearcher searchder = new AgencyProductSearcher();
                searchder.setProductId(request.getTestingProductId());
                searchder.setAgencyId(agencyId);
                com.todaysoft.ghealth.mybatis.model.AgencyProduct data = service.getBySearch(searchder);
                if(request.getAgencyPrice()!=null){
                    data.setAgencyPrice(request.getAgencyPrice());
                }
                Agency a = new Agency();
                a.setId(agencyId);
                data.setAgency(a);
                Product p = new Product();
                p.setId(request.getTestingProductId());
                data.setProduct(p);
                if(StringUtils.isNotEmpty(request.getDiscountPrice())){
                    data.setDiscountPrice(request.getDiscountPrice());
                    data.setDiscount(request.isDiscount());
                    data.setStartTime(new Date(request.getStartTime()));
                    if (request.getEndTime()!=null){
                        String endTime= transferLongToDate("yyyy-MM-dd 23:59:59", request.getEndTime());
                        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
                        Date ddate;
                        try {
                            ddate =formatter.parse(endTime);
                            data.setEndTime(ddate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                }
                if (request.getChangeType().equals("4")&&!Objects.isNull(request.isDiscount()))
                {
                    data.setDiscount(request.isDiscount());
                }

                if (request.getChangeType().equals("1")) {
                    AgencyProductSearcher searcher = new AgencyProductSearcher();
                    searcher.setAgencyId(agencyId);
                    searcher.setProductId(request.getTestingProductId());
                    com.todaysoft.ghealth.mybatis.model.AgencyProduct product = service.getBySearch(searcher);
                    AgencyProductOldPrice agencyProductOldPrice = new AgencyProductOldPrice();
                    agencyProductOldPrice.setId(IdGen.uuid());
                    agencyProductOldPrice.setAgencyProductId(product.getId());
                    agencyProductOldPrice.setOldAgencyPrice(product.getAgencyPrice());
                    agencyProductOldPrice.setChangedAgencyPrice(request.getAgencyPrice());
                    agencyProductOldPrice.setDetail(request.getDetail());
                    agencyProductOldPrice.setOperateTime(new Date());
                    agencyProductOldPrice.setOperator(account.getName());
                    agencyProductOldPriceService.create(agencyProductOldPrice);
                }

                service.modify(data);
            }
        }
        
    }


    private String transferLongToDate(String dateFormat, Long millSec)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }



    public void delete(MaintainProductAgencyRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());

        if (null == account)
        {
            throw new IllegalStateException();
        }

        if (StringUtils.isNotEmpty(request.getAgencyIds()))
        {
            String split = "-";
            for (String agencyId : request.getAgencyIds().split(split))
            {

                com.todaysoft.ghealth.mybatis.model.AgencyProduct data = new com.todaysoft.ghealth.mybatis.model.AgencyProduct();
                AgencyProductSearcher searcher = new AgencyProductSearcher();
                searcher.setAgencyId(agencyId);
                searcher.setProductId(request.getTestingProductId());
                service.delete(searcher);
            }
        }

    }



    public void addPro(MaintainProductAgencyRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());

        if (null == account)
        {
            throw new IllegalStateException();
        }

        if (StringUtils.isNotEmpty(request.getTestingProductIds()))
        {
            String split = "-";
            for (String testingProductId : request.getTestingProductIds().split(split))
            {

                com.todaysoft.ghealth.mybatis.model.AgencyProduct data = new com.todaysoft.ghealth.mybatis.model.AgencyProduct();
                data.setId(IdGen.uuid());
                Product p = new Product();
                p.setId(testingProductId);
                data.setProduct(p);
                Agency a = new Agency();
                a.setId(request.getAgencyId());
                data.setAgency(a);
                data.setAgencyPrice(request.getAgencyPrice());
                data.setAgencyPrice(testingProductService.get(testingProductId).getGuidingPrice());
                service.allocate(data);
            }
        }

    }


    public void deletePro(MaintainProductAgencyRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());

        if (null == account)
        {
            throw new IllegalStateException();
        }

        if (StringUtils.isNotEmpty(request.getTestingProductIds()))
        {
            String split = "-";
            for (String testingProductId : request.getTestingProductIds().split(split))
            {

                com.todaysoft.ghealth.mybatis.model.AgencyProduct data = new com.todaysoft.ghealth.mybatis.model.AgencyProduct();
                AgencyProductSearcher searcher = new AgencyProductSearcher();
                searcher.setAgencyId(request.getAgencyId());
                searcher.setProductId(testingProductId);
                service.delete(searcher);
            }
        }

    }


    public ListResponse<com.todaysoft.ghealth.base.response.model.AgencyProductOldPrice> getOldPriceDetails(QueryProductAgencyRequest request)
    {
        AgencyProductOldPriceSearcher searcher = new AgencyProductOldPriceSearcher();
        searcher.setAgencyId(request.getAgencyId());
        searcher.setProductId(request.getProductId());
        return new ListResponse<>(agencyProductOldPriceWrapper.wrap(agencyProductOldPriceService.list(searcher)));
    }
}
