# 样本签收记录表
DROP TABLE IF EXISTS `GHEALTH_SAMPLE_SIGN`;
CREATE TABLE `GHEALTH_SAMPLE_SIGN`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `SAMPLE_COUNT`          integer             NOT NULL        COMMENT '签收样本数量',
    `OPERATOR_NAME`         varchar(32)         NOT NULL        COMMENT '签收人姓名',
    `OPERATE_TIME`          datetime            NOT NULL        COMMENT '签收时间',
    CONSTRAINT PK_SAMPLE_SIGN PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_SAMPLE_SIGN` COMMENT '样本签收记录表';

# 样本签收详情表
DROP TABLE IF EXISTS `GHEALTH_SAMPLE_SIGN_DETAILS`;
CREATE TABLE `GHEALTH_SAMPLE_SIGN_DETAILS`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `SIGN_RECORD_ID`        varchar(64)         NOT NULL        COMMENT '签收记录ID',
    `ORDER_ID`              varchar(64)         NOT NULL        COMMENT '订单ID',
    CONSTRAINT PK_SAMPLE_SIGN_DETAILS PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_SAMPLE_SIGN_DETAILS` COMMENT '样本签收详情表';

# 样本寄送记录表
DROP TABLE IF EXISTS `GHEALTH_SAMPLE_DELIVERY`;
CREATE TABLE `GHEALTH_SAMPLE_DELIVERY`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `SAMPLE_COUNT`          integer             NOT NULL        COMMENT '寄送样本数量',
    `OPERATOR_NAME`         varchar(32)         NOT NULL        COMMENT '寄送人姓名',
    `OPERATE_TIME`          datetime            NOT NULL        COMMENT '寄送时间',
    CONSTRAINT PK_SAMPLE_DELIVERY PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_SAMPLE_DELIVERY` COMMENT '样本寄送记录表';

# 样本寄送详情表
DROP TABLE IF EXISTS `GHEALTH_SAMPLE_DELIVERY_DETAILS`;
CREATE TABLE `GHEALTH_SAMPLE_DELIVERY_DETAILS`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `DELIVERY_RECORD_ID`    varchar(64)         NOT NULL        COMMENT '寄送记录ID',
    `ORDER_ID`              varchar(64)         NOT NULL        COMMENT '订单ID',
    CONSTRAINT PK_SAMPLE_DELIVERY_DETAILS PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_SAMPLE_DELIVERY_DETAILS` COMMENT '样本寄送详情表';

ALTER TABLE `GHEALTH_ORDER_REPORT_GENERATE_TASK` ADD COLUMN `ERROR_CODE`    varchar(64)  COMMENT '错误码'  AFTER `FINISH_TIME`;
ALTER TABLE `GHEALTH_ORDER_REPORT_GENERATE_TASK` ADD COLUMN `ERROR_MESSAGE` varchar(64)  COMMENT '错误信息' AFTER `ERROR_CODE`;