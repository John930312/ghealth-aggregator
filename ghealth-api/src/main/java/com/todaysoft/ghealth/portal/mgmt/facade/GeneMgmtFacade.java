package com.todaysoft.ghealth.portal.mgmt.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Gene;
import com.todaysoft.ghealth.base.response.model.GeneDetails;
import com.todaysoft.ghealth.mgmt.request.MaintainGeneRequest;
import com.todaysoft.ghealth.mgmt.request.QueryGenesRequest;
import com.todaysoft.ghealth.mybatis.model.GeneDocument;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.GeneDocumentSearcher;
import com.todaysoft.ghealth.mybatis.searcher.GeneSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IGeneDocumentService;
import com.todaysoft.ghealth.service.IGeneService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.GeneWrapper;
import com.todaysoft.ghealth.utils.IdGen;

@Component
public class GeneMgmtFacade
{
    @Autowired
    private GeneWrapper geneWrapper;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IGeneService service;
    
    @Autowired
    private IGeneDocumentService geneDocumentService;
    
    public PagerResponse<Gene> pager(@RequestBody QueryGenesRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        
        GeneSearcher searcher = new GeneSearcher();
        searcher.setSymbol(request.getSymbol());
        
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Gene> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Gene>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.Gene> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<Gene> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), geneWrapper.wrap(pager.getRecords()));
        return new PagerResponse<Gene>(result);
    }
    
    public GeneDetails get(QueryDetailsRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new IllegalArgumentException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Gene gene = service.get(request.getId());
        
        GeneDetails details = geneWrapper.wrap(gene);
        
        if (null == details)
        {
            return null;
        }
        
        GeneDocumentSearcher searcher = new GeneDocumentSearcher();
        searcher.setGeneId(details.getId());
        List<GeneDocument> geneDocuments = geneDocumentService.list(searcher);
        List<com.todaysoft.ghealth.base.response.model.GeneDocument> geneDocumentss = new ArrayList<com.todaysoft.ghealth.base.response.model.GeneDocument>();
        if (!CollectionUtils.isEmpty(geneDocuments))
        {
            for (GeneDocument data : geneDocuments)
            {
                com.todaysoft.ghealth.base.response.model.GeneDocument geneDocument = new com.todaysoft.ghealth.base.response.model.GeneDocument();
                BeanUtils.copyProperties(data, geneDocument);
                geneDocumentss.add(geneDocument);
                
            }
        }
        details.setGeneDocuments(geneDocumentss);
        return details;
    }
    
    public void create(MaintainGeneRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Gene gene = new com.todaysoft.ghealth.mybatis.model.Gene();
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        gene.setId(IdGen.uuid());
        gene.setSymbol(request.getSymbol());
        gene.setName(request.getName());
        gene.setCreateTime(new Date());
        gene.setCreatorName(account.getName());
        gene.setDeleted(false);
        gene.setDescription(request.getDescription());
        
        service.create(gene);
        if (!CollectionUtils.isEmpty(request.getGeneTitle()))
        {
            for (String title : request.getGeneTitle())
            {
                com.todaysoft.ghealth.mybatis.model.GeneDocument geneDocument = new com.todaysoft.ghealth.mybatis.model.GeneDocument();
                geneDocument.setGeneId(gene.getId());
                geneDocument.setId(IdGen.uuid());
                geneDocument.setTitle(title);
                geneDocumentService.create(geneDocument);
                
            }
        }
        
    }
    
    public void modify(MaintainGeneRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Gene gene = new com.todaysoft.ghealth.mybatis.model.Gene();
        BeanUtils.copyProperties(request, gene);
        gene.setUpdatorName(account.getName());
        gene.setUpdateTime(new Date());
        service.modify(gene);
        
        com.todaysoft.ghealth.mybatis.model.GeneDocument geneDocument = new com.todaysoft.ghealth.mybatis.model.GeneDocument();
        geneDocument.setGeneId(gene.getId());
        geneDocumentService.delete(geneDocument);
        if (!CollectionUtils.isEmpty(request.getGeneTitle()))
        {
            for (String title : request.getGeneTitle())
            {
                geneDocument.setGeneId(gene.getId());
                geneDocument.setId(IdGen.uuid());
                geneDocument.setTitle(title);
                geneDocumentService.create(geneDocument);
            }
        }
    }
    
    public void delete(MaintainGeneRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Gene gene = service.get(request.getId());
        gene.setDeleted(true);
        gene.setDeletorName(account.getName());
        gene.setDeleteTime(new Date());
        service.modify(gene);
    }

    public ObjectResponse<Boolean> isNameUnique(MaintainGeneRequest request)
    {
        boolean unique = service.isNameUnique(request.getId(), request.getName());
        return new ObjectResponse<Boolean>(unique);
    }

    public ObjectResponse<Boolean> isSymbolUnique(MaintainGeneRequest request)
    {
        boolean unique = service.isSymbolUnique(request.getId(), request.getSymbol());
        return new ObjectResponse<Boolean>(unique);
    }
    
}
