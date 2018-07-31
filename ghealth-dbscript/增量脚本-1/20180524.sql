# 客户账号表
DROP TABLE IF EXISTS `GHEALTH_CUSTOMER_ACCOUNT`;
CREATE TABLE `GHEALTH_CUSTOMER_ACCOUNT`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `PHONE`             varchar(16)         NOT NULL        COMMENT '手机号',
    `NAME`              varchar(64)                         COMMENT '客户姓名',
    `OPENID`            varchar(64)                         COMMENT '客户微信OPENID',
    `ENABLED`           tinyint(1)          NOT NULL        COMMENT '账号状态：0-禁用 1-启用',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`      varchar(64)                         COMMENT '创建人姓名',
    `UPDATE_TIME`       datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`      varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`           tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`       datetime                            COMMENT '删除时间',
    `DELETOR_NAME`      varchar(64)                         COMMENT '删除人姓名',
    CONSTRAINT PK_WECHAT_ACCOUNT PRIMARY KEY (`ID`)
)
;

# 客户账号登录token表
DROP TABLE IF EXISTS `GHEALTH_CUSTOMER_ACCOUNT_TOKEN`;
CREATE TABLE `GHEALTH_CUSTOMER_ACCOUNT_TOKEN`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ACCOUNT_ID`        varchar(64)         NOT NULL        COMMENT '账号ID',
    `TOKEN`             varchar(128)        NOT NULL        COMMENT '令牌',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `EXPIRE_TIME`       datetime                            COMMENT '失效时间',
    CONSTRAINT PK_CUSTOMER_ACCOUNT_TOKEN PRIMARY KEY (`ID`)
)
;