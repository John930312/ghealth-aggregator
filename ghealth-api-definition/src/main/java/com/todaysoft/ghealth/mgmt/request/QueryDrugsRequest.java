package com.todaysoft.ghealth.mgmt.request;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

public class QueryDrugsRequest extends SignatureTokenListRequest {

    private String ingredientCn;

    private String ingredientEn;

    private String geneName;

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

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }
}
