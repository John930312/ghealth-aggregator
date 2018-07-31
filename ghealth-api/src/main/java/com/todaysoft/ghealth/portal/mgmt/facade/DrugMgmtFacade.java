package com.todaysoft.ghealth.portal.mgmt.facade;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Drug;
import com.todaysoft.ghealth.mgmt.request.MaintainDrugRequest;
import com.todaysoft.ghealth.mgmt.request.QueryDrugsRequest;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.DrugSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IDrugService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.DrugWrapper;
import com.todaysoft.ghealth.utils.IdGen;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Component
public class DrugMgmtFacade
{
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IDrugService service;
    
    @Autowired
    private DrugWrapper wrapper;
    
    public PagerResponse<Drug> pager(@RequestBody QueryDrugsRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        
        DrugSearcher searcher = new DrugSearcher();
        searcher.setIngredientEn(request.getIngredientEn());
        searcher.setIngredientCn(request.getIngredientCn());
        searcher.setGeneName(request.getGeneName());
        
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Drug> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Drug>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.Drug> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<Drug> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<Drug>(result);
    }
    
    public void create(MaintainDrugRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Drug drug = new com.todaysoft.ghealth.mybatis.model.Drug();
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        drug.setId(IdGen.uuid());
        drug.setIngredientCn(request.getIngredientCn());
        drug.setIngredientEn(request.getIngredientEn());
        drug.setProductName(request.getProductName());
        drug.setCategory(request.getCategory());
        drug.setAdultUsed(request.isAdultUsed());
        drug.setChildrenUsed(request.isChildrenUsed());
        drug.setDeleted(false);
        drug.setCreatorName(account.getName());
        drug.setCreateTime(new Date());
        service.create(drug);
        
        com.todaysoft.ghealth.mybatis.model.DrugGene drugGene = new com.todaysoft.ghealth.mybatis.model.DrugGene();
        for (String geneId : request.getGeneId().split(","))
        {
            drugGene.setDrugId(drug.getId());
            drugGene.setGeneId(geneId);
            service.createDrugGene(drugGene);
        }
    }
    
    public ObjectResponse<Drug> get(QueryDetailsRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Drug drug = service.get(request.getId());
        
        Drug data = wrapper.wrap(drug);
        return new ObjectResponse<Drug>(data);
    }
    
    public void modify(MaintainDrugRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Drug drug = new com.todaysoft.ghealth.mybatis.model.Drug();
        BeanUtils.copyProperties(request, drug);
        drug.setUpdatorName(account.getName());
        drug.setUpdateTime(new Date());
        service.modify(drug);
        com.todaysoft.ghealth.mybatis.model.DrugGene drugGene = new com.todaysoft.ghealth.mybatis.model.DrugGene();
        drugGene.setDrugId(drug.getId());
        service.deleteDrugGene(drugGene);
        for (String geneId : request.getGeneId().split(","))
        {
            drugGene.setDrugId(drug.getId());
            drugGene.setGeneId(geneId);
            service.createDrugGene(drugGene);
        }
    }
    
    public void delete(MaintainDrugRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Drug drug = service.get(request.getId());
        drug.setDeleted(true);
        drug.setDeletorName(account.getName());
        drug.setDeleteTime(new Date());
        service.modify(drug);
    }
    
    public ObjectResponse<Boolean> isIngredientCnUnique(MaintainDrugRequest request)
    {
        boolean unique = service.isIngredientCnUnique(request.getId(), request.getIngredientCn());
        return new ObjectResponse<Boolean>(unique);
    }
    
    public List<Drug> getDrugByCategory(DrugSearcher searcher)
    {
        return wrapper.wrap(service.getDrugByCategory(searcher));
    }
}
