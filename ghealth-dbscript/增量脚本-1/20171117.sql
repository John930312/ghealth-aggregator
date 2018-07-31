# 订单-检测评估-项目评估结果
DROP TABLE IF EXISTS `GHEALTH_ORDER_EVAL_ITEM`;
CREATE TABLE `GHEALTH_ORDER_EVAL_ITEM`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_ID`          varchar(64)         NOT NULL        COMMENT '订单ID',
    `ITEM_ID`           varchar(64)         NOT NULL        COMMENT '检测项目ID',
    `EVAL_VALUE`        decimal(10,4)       NOT NULL        COMMENT '评估结果',
    CONSTRAINT PK_ORDER_EVAL_ITEM PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_ORDER_EVAL_ITEM` COMMENT '订单-检测评估-项目评估结果表';

# 订单-检测评估-项目位点评估结果
DROP TABLE IF EXISTS `GHEALTH_ORDER_EVAL_ITEM_LOCUS`;
CREATE TABLE `GHEALTH_ORDER_EVAL_ITEM_LOCUS`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_ID`          varchar(64)         NOT NULL        COMMENT '订单ID',
    `ITEM_ID`           varchar(64)         NOT NULL        COMMENT '检测项目ID',
    `LOCUS_ID`          varchar(64)         NOT NULL        COMMENT '位点ID',
    `EVAL_VALUE`        decimal(10,4)       NOT NULL        COMMENT '评估结果',
    CONSTRAINT PK_ORDER_EVAL_ITEM_LOCUS PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_ORDER_EVAL_ITEM_LOCUS` COMMENT '订单-检测评估-项目位点评估结果表';


ALTER TABLE `GHEALTH_ORDER` ADD COLUMN `REPORT_GENERATE_TASK`  varchar(64)  COMMENT '报告生成任务' AFTER `REPORT_GENERATE_TIME`;

# 订单-报告生成任务表
DROP TABLE IF EXISTS `GHEALTH_ORDER_REPORT_GENERATE_TASK`;
CREATE TABLE `GHEALTH_ORDER_REPORT_GENERATE_TASK`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `STATUS`            tinyint(1)          NOT NULL        COMMENT '任务状态',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`      varchar(64)                         COMMENT '创建人姓名',
    `FINISH_TIME`       datetime                            COMMENT '完成时间',
    `WORD_FILE_URL`     varchar(256)                        COMMENT 'WORD报告路径',
    `PDF_FILE_URL`      varchar(256)                        COMMENT 'PDF报告路径',
    CONSTRAINT PK_ORDER_REPORT_GENERATE_TASK PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_ORDER_REPORT_GENERATE_TASK` COMMENT '订单-报告生成任务表';