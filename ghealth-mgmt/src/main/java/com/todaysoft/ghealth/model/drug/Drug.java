package com.todaysoft.ghealth.model.drug;

import java.util.Date;
import java.util.List;

public class Drug {
    private String id;

    private String ingredientCn;

    private String ingredientEn;

    private String productName;

    private String category;

    private String geneId;

    private boolean adultUsed;

    private boolean childrenUsed;

    private String geneName;

    private Date createTime;

    private String creatorName;

    private Date updateTime;

    private String updatorName;

    private boolean deleted;

    private Date deleteTime;

    private String deletorName;

    private List<String> gene;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getDeletorName() {
        return deletorName;
    }

    public void setDeletorName(String deletorName) {
        this.deletorName = deletorName;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public List<String> getGene() {
        return gene;
    }

    public void setGene(List<String> gene) {
        this.gene = gene;
    }
}
