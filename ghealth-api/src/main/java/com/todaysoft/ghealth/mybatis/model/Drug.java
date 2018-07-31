package com.todaysoft.ghealth.mybatis.model;

public class Drug extends PrimaryEntity{
    private String ingredientCn;

    private String ingredientEn;

    private String productName;

    private String category;

    private String geneId;

    private boolean adultUsed;

    private boolean childrenUsed;

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

    public void setGeneId(String gengId) {
        this.geneId = gengId;
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

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }
}
