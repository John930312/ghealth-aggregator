package com.todaysoft.ghealth.mgmt.request;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

import java.util.Map;

public class MaintainDrugRequest extends SignatureTokenRequest {
    private String id;

    private String ingredientCn;

    private String ingredientEn;

    private String productName;

    private String category;

    private String geneId;

    private boolean adultUsed;

    private boolean childrenUsed;

    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("ingredientCn", ingredientCn);
        fields.put("ingredientEn", ingredientEn);
        fields.put("productName", productName);
        fields.put("category", category);
        fields.put("adultUsed", String.valueOf(adultUsed));

        fields.put("childrenUsed", String.valueOf(childrenUsed));
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIngredientCn() {
        return ingredientCn;
    }

    public void setIngredientCn(String ingredientCn) {
        this.ingredientCn = ingredientCn;
    }

    public String getIngredientEn() {
        return ingredientEn;
    }

    public void setIngredientEn(String ingredientEn) {
        this.ingredientEn = ingredientEn;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public boolean isAdultUsed() {
        return adultUsed;
    }

    public void setAdultUsed(boolean adultUsed) {
        this.adultUsed = adultUsed;
    }

    public boolean isChildrenUsed() {
        return childrenUsed;
    }

    public void setChildrenUsed(boolean childrenUsed) {
        this.childrenUsed = childrenUsed;
    }
}
