package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Drug;
import com.todaysoft.ghealth.mgmt.request.MaintainDrugRequest;
import com.todaysoft.ghealth.mgmt.request.QueryDrugsRequest;

import com.todaysoft.ghealth.portal.mgmt.facade.DrugMgmtFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mgmt/drug")
public class DrugMgmtController {

    @Autowired
    private DrugMgmtFacade facade;

    @RequestMapping("/pager")
    public PagerResponse<Drug> pager(@RequestBody QueryDrugsRequest request)
    {
        return facade.pager(request);
    }

    @RequestMapping("/create")
    public void create(@RequestBody MaintainDrugRequest request)
    {
        facade.create(request);
    }
    @RequestMapping("/details")
    public ObjectResponse<Drug> details(@RequestBody QueryDetailsRequest request)
    {
        return facade.get(request);
    }
    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainDrugRequest request)
    {
        facade.modify(request);
    }

    @RequestMapping("/delete")
    public void delete(@RequestBody MaintainDrugRequest request)
    {
        facade.delete(request);
    }
    @RequestMapping("/unique/ingredientCn")
    public ObjectResponse<Boolean> isIngredientCnUnique(@RequestBody MaintainDrugRequest request)
    {
        return facade.isIngredientCnUnique(request);
    }
}
