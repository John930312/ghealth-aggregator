# 药物表
DROP TABLE IF EXISTS `GHEALTH_DRUG`;
CREATE TABLE `GHEALTH_DRUG`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `INGREDIENT_CN`         varchar(256)                        COMMENT '药物成分-中文',
    `INGREDIENT_EN`         varchar(256)                        COMMENT '药物成分-英文',
    `PRODUCT_NAME`          varchar(256)                        COMMENT '药品名',
    `CATEGORY`              varchar(64)                         COMMENT '分类',
    `ADULT_USED`            tinyint(1)                          COMMENT '是否成人用药 0-否 1-是',
    `CHILDREN_USED`         tinyint(1)                          COMMENT '是否儿童用药 0-否 1-是',
    `CREATE_TIME`           datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`          varchar(64)         NOT NULL        COMMENT '创建人姓名',
    `UPDATE_TIME`           datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`          varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`               tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`           datetime                            COMMENT '删除时间',
    `DELETOR_NAME`          varchar(64)                         COMMENT '删除人姓名',
    CONSTRAINT PK_DRUG PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_DRUG` COMMENT '药物表';

# 药物基因表
DROP TABLE IF EXISTS `GHEALTH_DRUG_GENE`;
CREATE TABLE `GHEALTH_DRUG_GENE`
(
    `DRUG_ID`               varchar(64)         NOT NULL        COMMENT '药物ID',
    `GENE_ID`               varchar(64)         NOT NULL        COMMENT '基因ID'
)
;
ALTER TABLE `GHEALTH_DRUG_GENE` COMMENT '药物基因表';

ALTER TABLE `GHEALTH_TESTING_ITEM` ADD COLUMN `EVAL_ALGORITHM`          varchar(64)     COMMENT '评估算法'          AFTER `ENABLED`;
ALTER TABLE `GHEALTH_TESTING_ITEM` ADD COLUMN `EVAL_ALGORITHM_DETAILS`  text            COMMENT '评估算法配置明细'     AFTER `EVAL_ALGORITHM`;
ALTER TABLE `GHEALTH_TESTING_ITEM` ADD COLUMN `EVAL_GRADE_DETAILS`      text            COMMENT '评估分级配置明细'     AFTER `EVAL_ALGORITHM_DETAILS`;
