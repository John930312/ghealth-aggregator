# 序列号生成表
DROP TABLE IF EXISTS `GHEALTH_SEQUENCE`;
CREATE TABLE `GHEALTH_SEQUENCE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `NAME`              varchar(64)                         COMMENT '序列名称',
    `CURRENT_VALUE`     varchar(512)                        COMMENT '当前值',
    `INCREMENT`         varchar(128)                        COMMENT '递增值',
    `BATCH_DATE`        varchar(512)                        COMMENT '日期',
    CONSTRAINT PK_SEQUENCE PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_SEQUENCE` COMMENT '序列号生成表';

# 地区表
DROP TABLE IF EXISTS `GHEALTH_DISTRICT`;
CREATE TABLE `GHEALTH_DISTRICT`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `PARENT_ID`         varchar(64)                         COMMENT '直属上级地区',
    `PARENT_IDS`        varchar(512)                        COMMENT '全部上级地区',
    `NAME`              varchar(128)                        COMMENT '名称',
    `FULL_NAME`         varchar(512)                        COMMENT '全称',
    `PY`                varchar(64)                         COMMENT '拼音首字母',
    `PINYIN`            varchar(512)                        COMMENT '拼音全拼',
    CONSTRAINT PK_DISTRICT PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_DISTRICT` COMMENT '地区表';

# 用户表
DROP TABLE IF EXISTS `GHEALTH_USER`;
CREATE TABLE `GHEALTH_USER`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `USERNAME`          varchar(32)         NOT NULL        COMMENT '用户名',
    `PASSWORD`          varchar(128)        NOT NULL        COMMENT '密码',
    `ENABLED`           tinyint(1)          NOT NULL        COMMENT '账号状态',
    `NAME`              varchar(32)                         COMMENT '姓名',
    `PHONE`             varchar(32)                         COMMENT '联系电话',
    `EMAIL`             varchar(256)                        COMMENT '电子邮箱',
    `BUILTIN`           tinyint(1)          NOT NULL        COMMENT '是否内置账号',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`      varchar(64)                         COMMENT '创建人姓名',
    `UPDATE_TIME`       datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`      varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`           tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`       datetime                            COMMENT '删除时间',
    `DELETOR_NAME`      varchar(64)                         COMMENT '删除人姓名',
    CONSTRAINT PK_USER PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_USER` COMMENT '用户表';

# 用户账号登录token表
DROP TABLE IF EXISTS `GHEALTH_USER_TOKEN`;
CREATE TABLE `GHEALTH_USER_TOKEN`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ACCOUNT_ID`        varchar(64)         NOT NULL        COMMENT '账号ID',
    `TOKEN`             varchar(128)        NOT NULL        COMMENT '令牌',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `EXPIRE_TIME`       datetime                            COMMENT '失效时间',
    CONSTRAINT PK_AGENCY_TOKEN PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_USER_TOKEN` COMMENT '用户账号登录token表';

# 角色表
DROP TABLE IF EXISTS `GHEALTH_ROLE`;
CREATE TABLE `GHEALTH_ROLE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `NAME`              varchar(32)         NOT NULL        COMMENT '角色名称',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`      varchar(64)         NOT NULL        COMMENT '创建人姓名',
    `UPDATE_TIME`       datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`      varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`           tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`       datetime                            COMMENT '删除时间',
    `DELETOR_NAME`      varchar(64)                         COMMENT '删除人姓名',
    CONSTRAINT PK_ROLE PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_ROLE` COMMENT '角色表';

# 用户角色关联表
DROP TABLE IF EXISTS `GHEALTH_USER_ROLE`;
CREATE TABLE `GHEALTH_USER_ROLE`
(
    `USER_ID`           varchar(64)         NOT NULL,
    `ROLE_ID`           varchar(64)         NOT NULL
)
;
ALTER TABLE `GHEALTH_USER_ROLE` COMMENT '用户角色关联表';

# 权限表
DROP TABLE IF EXISTS `GHEALTH_AUTHORITY`;
CREATE TABLE `GHEALTH_AUTHORITY`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `PARENT_ID`         varchar(64),
    `CODE`              varchar(128)        NOT NULL UNIQUE COMMENT '权限编号',
    `NAME`              varchar(128)        NOT NULL        COMMENT '权限名称',
    `SORT`              integer             NOT NULL        COMMENT '排序编号',
    CONSTRAINT PK_AUTHORITY PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_AUTHORITY` COMMENT '权限表';

# 角色权限关联表
DROP TABLE IF EXISTS `GHEALTH_ROLE_AUTHORITY`;
CREATE TABLE `GHEALTH_ROLE_AUTHORITY`
(
    `ROLE_ID`           varchar(64)         NOT NULL,
    `AUTHORITY_ID`      varchar(64)         NOT NULL
)
;
ALTER TABLE `GHEALTH_ROLE_AUTHORITY` COMMENT '角色权限关联表';

# 资源表
DROP TABLE IF EXISTS `GHEALTH_RESOURCE`;
CREATE TABLE `GHEALTH_RESOURCE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `NAME`              varchar(128)        NOT NULL,
    `URI`               varchar(256)        NOT NULL,
    CONSTRAINT PK_RESOURCE PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_RESOURCE` COMMENT '资源表';

# 权限资源关联表
DROP TABLE IF EXISTS `GHEALTH_AUTHORITY_RESOURCE`;
CREATE TABLE `GHEALTH_AUTHORITY_RESOURCE`
(
    `AUTHORITY_ID`      varchar(64)         NOT NULL,
    `RESOURCE_ID`       varchar(64)         NOT NULL
)
;
ALTER TABLE `GHEALTH_AUTHORITY_RESOURCE` COMMENT '权限资源关联表';

# 菜单表
DROP TABLE IF EXISTS `GHEALTH_MENU`;
CREATE TABLE `GHEALTH_MENU`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `PARENT_ID`         varchar(64)                         COMMENT '菜单上级编号',
    `NAME`              varchar(64)         NOT NULL        COMMENT '菜单名称',
    `URI`               varchar(128)                        COMMENT '菜单转向链接',
    `SORT`              integer                             COMMENT '菜单排序编号',
    `ICON`              varchar(64)                         COMMENT '图标',
    CONSTRAINT PK_MENU PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_MENU` COMMENT '菜单表';

# 系统参数表
DROP TABLE IF EXISTS `GHEALTH_CONFIG`;
CREATE TABLE `GHEALTH_CONFIG`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `CONFIG_KEY`        varchar(128)        NOT NULL        COMMENT '配置KEY',
    `CONFIG_NAME`       varchar(256)        NOT NULL        COMMENT '配置名称',
    `CONFIG_VALUE`      varchar(256)        NOT NULL        COMMENT '配置值',
    `CONFIG_VALUE_TYPE` tinyint(1)          NOT NULL        COMMENT '配置值类型：1-字符串 2-数值',
    CONSTRAINT PK_CONFIG PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_CONFIG` COMMENT '系统参数表';

# 数据字典表
DROP TABLE IF EXISTS `GHEALTH_DICT`;
CREATE TABLE `GHEALTH_DICT`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '编号',
    `PARENT_ID`         varchar(64)                         COMMENT '上级编号',
    `CATEGORY`          varchar(64)                         COMMENT '类目',
    `DICT_TEXT`         varchar(64)                         COMMENT '显示文本',
    `DICT_VALUE`        varchar(128)                        COMMENT '字典值',
    `SORT`              integer                             COMMENT '排序编号',
    `EDITABLE`          tinyint(1)                          COMMENT '是否可编辑',
    CONSTRAINT PK_DICT PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_DICT` COMMENT '系统参数表';

# 肿瘤档案
DROP TABLE IF EXISTS `GHEALTH_CANCER`;
CREATE TABLE `GHEALTH_CANCER`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `NAME`              varchar(128)        NOT NULL        COMMENT '名称',
    `RISK_MALE`         decimal(10,6)                       COMMENT '男性患病风险',
    `RISK_FEMALE`       decimal(10,6)                       COMMENT '女性患病风险',
    `DESCRIPTION`       text                                COMMENT '描述',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`      varchar(64)         NOT NULL        COMMENT '创建人姓名',
    `UPDATE_TIME`       datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`      varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`           tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`       datetime                            COMMENT '删除时间',
    `DELETOR_NAME`      varchar(64)                         COMMENT '删除人姓名',
    `SYNC_KEY`          varchar(64)                         COMMENT '历史数据同步KEY值，同步结束后删除该字段',
    CONSTRAINT PK_CANCER PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_CANCER` COMMENT '肿瘤档案表';

# 基因档案
DROP TABLE IF EXISTS `GHEALTH_GENE`;
CREATE TABLE `GHEALTH_GENE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `SYMBOL`            varchar(64)         NOT NULL        COMMENT '名称',
    `NAME`              varchar(512)                        COMMENT '全称',
    `DESCRIPTION`       text                                COMMENT '描述',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`      varchar(64)         NOT NULL        COMMENT '创建人姓名',
    `UPDATE_TIME`       datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`      varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`           tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`       datetime                            COMMENT '删除时间',
    `DELETOR_NAME`      varchar(64)                         COMMENT '删除人姓名',
    CONSTRAINT PK_GENE PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_GENE` COMMENT '基因档案表';

# 基因-文献
DROP TABLE IF EXISTS `GHEALTH_GENE_DOCUMENT`;
CREATE TABLE `GHEALTH_GENE_DOCUMENT`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `GENE_ID`           varchar(64)         NOT NULL        COMMENT '基因ID',
    `TITLE`             varchar(512)        NOT NULL        COMMENT '文献标题',
    CONSTRAINT PK_GENE_DOCUMENT PRIMARY KEY (`ID`)
)
;

# 位点档案
DROP TABLE IF EXISTS `GHEALTH_LOCUS`;
CREATE TABLE `GHEALTH_LOCUS`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `GENE_ID`           varchar(64)         NOT NULL        COMMENT '基因ID',
    `NAME`              varchar(256)                        COMMENT '位点名称',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`      varchar(64)         NOT NULL        COMMENT '创建人姓名',
    `UPDATE_TIME`       datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`      varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`           tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`       datetime                            COMMENT '删除时间',
    `DELETOR_NAME`      varchar(64)                         COMMENT '删除人姓名',
    `SYNC_KEY`          varchar(64)                         COMMENT '历史数据同步KEY值，同步结束后删除该字段',
    CONSTRAINT PK_LOCUS PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_LOCUS` COMMENT '位点档案表';

# 检测项目
DROP TABLE IF EXISTS `GHEALTH_TESTING_ITEM`;
CREATE TABLE `GHEALTH_TESTING_ITEM`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `CODE`              varchar(64)         NOT NULL        COMMENT '编号',
    `NAME`              varchar(128)        NOT NULL        COMMENT '名称',
    `CATEGORY`          varchar(64)         NOT NULL        COMMENT '分类',
    `CATEGORY_MAPPING`  varchar(64)                         COMMENT '分类具体关联对象',
    `SEX_RESTRAINT`     tinyint(1)                          COMMENT '性别约束',
    `ENABLED`           tinyint(1)          NOT NULL        COMMENT '启用状态：0-否 1-是',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`      varchar(64)         NOT NULL        COMMENT '创建人姓名',
    `UPDATE_TIME`       datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`      varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`           tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`       datetime                            COMMENT '删除时间',
    `DELETOR_NAME`      varchar(64)                         COMMENT '删除人姓名',
    `SYNC_KEY`          varchar(64)                         COMMENT '历史数据同步KEY值，同步结束后删除该字段',
    CONSTRAINT PK_TESTING_ITEM PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_TESTING_ITEM` COMMENT '检测项目表';

# 检测项目-位点影响系数
DROP TABLE IF EXISTS `GHEALTH_TESTING_ITEM_LOCUS`;
CREATE TABLE `GHEALTH_TESTING_ITEM_LOCUS`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `TESTING_ITEM_ID`   varchar(64)         NOT NULL        COMMENT '检测项目ID',
    `LOCUS_ID`          varchar(64)         NOT NULL        COMMENT '位点ID',
    `INFLUENCE_FACTORS` text                                COMMENT '位点基因型影响关系JSON文本',
    CONSTRAINT PK_TESTING_ITEM_LOCUS PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_TESTING_ITEM_LOCUS` COMMENT '检测项目-位点影响系数表';

# 检测产品
DROP TABLE IF EXISTS `GHEALTH_TESTING_PRODUCT`;
CREATE TABLE `GHEALTH_TESTING_PRODUCT`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `CODE`              varchar(64)         NOT NULL        COMMENT '编号',
    `NAME`              varchar(128)        NOT NULL        COMMENT '名称',
    `SEX_RESTRAINT`     tinyint(1)                          COMMENT '性别约束',
    `GUIDING_PRICE`     decimal(10,2)                       COMMENT '指导价格',
    `ENABLED`           tinyint(1)          NOT NULL        COMMENT '启用状态：0-否 1-是',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`      varchar(64)         NOT NULL        COMMENT '创建人姓名',
    `UPDATE_TIME`       datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`      varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`           tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`       datetime                            COMMENT '删除时间',
    `DELETOR_NAME`      varchar(64)                         COMMENT '删除人姓名',
    `SYNC_KEY`          varchar(64)                         COMMENT '历史数据同步KEY值，同步结束后删除该字段',
    CONSTRAINT PK_TESTING_PRODUCT PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_TESTING_PRODUCT` COMMENT '检测产品表';

# 检测产品-检测项目关联表
DROP TABLE IF EXISTS `GHEALTH_PRODUCT_ITEM`;
CREATE TABLE `GHEALTH_PRODUCT_ITEM`
(
    `PRODUCT_ID`        varchar(64)         NOT NULL        COMMENT '产品ID',
    `TESTING_ITEM_ID`   varchar(64)         NOT NULL        COMMENT '项目ID'
)
;
ALTER TABLE `GHEALTH_PRODUCT_ITEM` COMMENT '检测产品-检测项目关联表';

# 代理商主表
DROP TABLE IF EXISTS `GHEALTH_AGENCY`;
CREATE TABLE `GHEALTH_AGENCY`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `CODE`                  varchar(64)         NOT NULL        COMMENT '编号',
    `NAME`                  varchar(128)        NOT NULL        COMMENT '名称',
    `ABBR`                  varchar(64)                         COMMENT '简称',
    `PROVINCE`              varchar(64)                         COMMENT '省',
    `CITY`                  varchar(64)                         COMMENT '市',
    `ADDRESS`               varchar(256)                        COMMENT '联系地址',
    `CONTACT_NAME`          varchar(64)                         COMMENT '联系人姓名',
    `CONTACT_EMAIL`         varchar(64)                         COMMENT '联系人邮箱',
    `CONTACT_PHONE`         varchar(64)                         COMMENT '联系人电话',
    `REMARK`                varchar(256)                        COMMENT '备注',
    `PREAUTHORIZED_AMOUNT`  decimal(10,2)                       COMMENT '预授权金额',
    `ACCOUNT_AMOUNT`        decimal(10,2)       NOT NULL        COMMENT '账户余额',
    `AMOUNT_SIGNATURE`      varchar(256)        NOT NULL        COMMENT '余额签名',
    `PRIMARY_USERNAME`      varchar(32)         NOT NULL        COMMENT '主账号',
    `CREATE_TIME`           datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`          varchar(64)         NOT NULL        COMMENT '创建人姓名',
    `UPDATE_TIME`           datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`          varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`               tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`           datetime                            COMMENT '删除时间',
    `DELETOR_NAME`          varchar(64)                         COMMENT '删除人姓名',
    `SYNC_KEY`              varchar(64)                         COMMENT '历史数据同步KEY值，同步结束后删除该字段',
    CONSTRAINT PK_AGENCY PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_AGENCY` COMMENT '代理商主表';

# 代理账号表
DROP TABLE IF EXISTS `GHEALTH_AGENCY_ACCOUNT`;
CREATE TABLE `GHEALTH_AGENCY_ACCOUNT`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `AGENCY_ID`         varchar(64)         NOT NULL        COMMENT '代理ID',
    `NAME`              varchar(64)                         COMMENT '姓名',
    `PHONE`             varchar(64)                         COMMENT '电话',
    `USERNAME`          varchar(64)         NOT NULL        COMMENT '用户名',
    `PASSWORD`          varchar(256)        NOT NULL        COMMENT '密码',
    `ENABLED`           tinyint(1)          NOT NULL        COMMENT '账号状态：0-禁用 1-启用',
    `PRIMARY_ACCOUNT`   tinyint(1)          NOT NULL        COMMENT '是否主账号：0-否 1-是',
    CONSTRAINT PK_AGENCY_ACCOUNT PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_AGENCY_ACCOUNT` COMMENT '代理账号表';

# 代理账号登录token表
DROP TABLE IF EXISTS `GHEALTH_AGENCY_TOKEN`;
CREATE TABLE `GHEALTH_AGENCY_TOKEN`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ACCOUNT_ID`        varchar(64)         NOT NULL        COMMENT '账号ID',
    `TOKEN`             varchar(128)        NOT NULL        COMMENT '令牌',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `EXPIRE_TIME`       datetime                            COMMENT '失效时间',
    CONSTRAINT PK_AGENCY_TOKEN PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_AGENCY_TOKEN` COMMENT '代理账号登录token表';

# 代理产品表
DROP TABLE IF EXISTS `GHEALTH_AGENCY_PRODUCT`;
CREATE TABLE `GHEALTH_AGENCY_PRODUCT`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `AGENCY_ID`         varchar(64)         NOT NULL        COMMENT '代理ID',
    `PRODUCT_ID`        varchar(64)         NOT NULL        COMMENT '产品ID',
    `AGENCY_PRICE`      decimal(10,2)       NOT NULL        COMMENT '代理价格',
    CONSTRAINT PK_AGENCY_PRODUCT PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_AGENCY_PRODUCT` COMMENT '代理产品表';

# 代理账单表
DROP TABLE IF EXISTS `GHEALTH_AGENCY_BILL`;
CREATE TABLE `GHEALTH_AGENCY_BILL`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `AGENCY_ID`         varchar(64)         NOT NULL        COMMENT '代理ID',
    `TITLE`             varchar(256)        NOT NULL        COMMENT '账单标题',
    `INCREASED`         tinyint(1)          NOT NULL        COMMENT '0-余额减少 1-余额增加',
    `BILL_TYPE`         varchar(64)         NOT NULL        COMMENT '账单事件，字典项：AGENCY_BILL_EVENT_TYPE',
    `EVENT_DETAILS`     varchar(64)         NOT NULL        COMMENT '账单事件详情',
    `AMOUNT_BEFORE`     decimal(10,2)       NOT NULL        COMMENT '变更前余额',
    `AMOUNT_AFTER`      decimal(10,2)       NOT NULL        COMMENT '变更后余额',
    `BILL_TIME`         datetime            NOT NULL        COMMENT '记账时间',
    CONSTRAINT PK_AGENCY_BILL PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_AGENCY_BILL` COMMENT '代理账单表';

# 代理充值记录表
DROP TABLE IF EXISTS `GHEALTH_AGENCY_PREPAY`;
CREATE TABLE `GHEALTH_AGENCY_PREPAY`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `AGENCY_ID`         varchar(64)         NOT NULL        COMMENT '代理ID',
    `AMOUNT`            decimal(10,2)       NOT NULL        COMMENT '预付款登记金额',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '操作时间',
    `CREATOR_NAME`      varchar(64)         NOT NULL        COMMENT '操作人姓名',
    CONSTRAINT PK_AGENCY_PREPAY PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_AGENCY_PREPAY` COMMENT '代理充值记录表';

# 客户主表
DROP TABLE IF EXISTS `GHEALTH_CUSTOMER`;
CREATE TABLE `GHEALTH_CUSTOMER`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `NAME`              varchar(64)         NOT NULL        COMMENT '姓名',
    `PHONE`             varchar(64)         NOT NULL        COMMENT '联系电话',
    `EMAIL`             varchar(128)                        COMMENT '电子邮箱',
    `SEX`               varchar(64)                         COMMENT '性别',
    `BIRTHDAY`          varchar(64)                         COMMENT '出生日期',
    `NATION`            varchar(64)                         COMMENT '民族，字典项：NATION',
    `MARITAL_STATUS`    varchar(64)                         COMMENT '婚姻状况，字典项：MARITAL_STATUS',
    `VOCATION`          varchar(256)                        COMMENT '职业',
    `COMPANY`           varchar(256)                        COMMENT '工作单位',
    `PROVINCE`          varchar(64)                         COMMENT '省',
    `CITY`              varchar(64)                         COMMENT '市',
    `COUNTY`            varchar(64)                         COMMENT '区',
    `ADDRESS`           varchar(256)                        COMMENT '联系地址',
    `REMARK`            varchar(256)                        COMMENT '备注',
    `AGENCY_ID`         varchar(64)                         COMMENT '所属代理ID',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`      varchar(64)         NOT NULL        COMMENT '创建人姓名',
    `UPDATE_TIME`       datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`      varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`           tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`       datetime                            COMMENT '删除时间',
    `DELETOR_NAME`      varchar(64)                         COMMENT '删除人姓名',
    `SYNC_KEY`          varchar(64)                         COMMENT '历史数据同步KEY值，同步结束后删除该字段',
    CONSTRAINT PK_CUSTOMER PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_CUSTOMER` COMMENT '客户主表';

# 订单主表
DROP TABLE IF EXISTS `GHEALTH_ORDER`;
CREATE TABLE `GHEALTH_ORDER`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `PRODUCT_ID`            varchar(64)         NOT NULL        COMMENT '产品ID',
    `CUSTOMER_ID`           varchar(64)         NOT NULL        COMMENT '客户ID',
    `AGENCY_ID`             varchar(64)         NOT NULL        COMMENT '代理ID',
    `CODE`                  varchar(64)         NOT NULL        COMMENT '编号',
    `ACTUAL_PRICE`          decimal(10,2)       NOT NULL        COMMENT '实际价格',
    `SAMPLE_TYPE`           varchar(64)                         COMMENT '样本类型',
    `STATUS`                varchar(64)         NOT NULL        COMMENT '状态',
    `SUBMIT_TIME`           datetime                            COMMENT '提交时间',
    `SUBMITOR_NAME`         varchar(64)                         COMMENT '提交人姓名',
    `REPORT_URL`            varchar(256)                        COMMENT '报告下载地址',
    `REPORT_PRINT_REQUIRED` tinyint(1)          NOT NULL        COMMENT '是否需要纸质报告',
    `REPORT_GENERATE_TIME`  datetime                            COMMENT '报告生成时间',
    `REPORT_GENERATE_TASK`  varchar(64)                         COMMENT '报告生成任务',
    `CREATE_TIME`           datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`          varchar(64)         NOT NULL        COMMENT '创建人姓名',
    `UPDATE_TIME`           datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`          varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`               tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`           datetime                            COMMENT '删除时间',
    `DELETOR_NAME`          varchar(64)                         COMMENT '删除人姓名',
    `SYNC_KEY`          varchar(64)                         COMMENT '历史数据同步KEY值，同步结束后删除该字段',
    CONSTRAINT PK_ORDER PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_ORDER` COMMENT '订单主表';

# 订单历史表
DROP TABLE IF EXISTS `GHEALTH_ORDER_HISTORY`;
CREATE TABLE `GHEALTH_ORDER_HISTORY`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_ID`              varchar(64)         NOT NULL        COMMENT '订单ID',
    `TITLE`                 varchar(256)        NOT NULL        COMMENT '事件标题',
    `EVENT_TYPE`            varchar(64)         NOT NULL        COMMENT '事件类型，字典项：ORDER_EVENT',
    `EVENT_DETAILS`         varchar(64)                         COMMENT '账单事件详情',
    `EVENT_TIME`            datetime            NOT NULL        COMMENT '记账时间',
    `OPERATOR_NAME`         varchar(64)                         COMMENT '操作人姓名',
    CONSTRAINT PK_ORDER_HISTORY PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_ORDER_HISTORY` COMMENT '订单历史表';

# 订单-退款申请表
DROP TABLE IF EXISTS `GHEALTH_ORDER_REFUND_APPLY`;
CREATE TABLE `GHEALTH_ORDER_REFUND_APPLY`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_ID`          varchar(64)         NOT NULL        COMMENT '订单ID',
    `REASON_CATEGORY`   varchar(64)         NOT NULL        COMMENT '原因分类，字典项：REFUND_REASON_CATEGORY',
    `REASON_DETAILS`    varchar(512)        NOT NULL        COMMENT '具体原因',
    `APPLIER_NAME`      varchar(64)         NOT NULL        COMMENT '申请人姓名',
    `APPLY_TIME`        datetime            NOT NULL        COMMENT '申请时间',
    `STATUS`            tinyint(1)          NOT NULL        COMMENT '处理状态，字典项：NORMAL_APPLY_STATUS',
    `CONFIRM_AMOUNT`    decimal(10,2)                       COMMENT '退款金额',
    `CONFIRM_REMARK`    varchar(512)                        COMMENT '确认备注',
    `CONFIRM_TIME`      datetime                            COMMENT '确认时间',
    `CONFIRMER_NAME`    varchar(64)         NOT NULL        COMMENT '确认人姓名',
    CONSTRAINT PK_ORDER_REFUND_APPLY PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_ORDER_REFUND_APPLY` COMMENT '订单-退款申请表';

# 订单-退款记录表
DROP TABLE IF EXISTS `GHEALTH_ORDER_REFUND`;
CREATE TABLE `GHEALTH_ORDER_REFUND`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_ID`          varchar(64)         NOT NULL        COMMENT '订单ID',
    `REFUND_AMOUNT`     decimal(10,2)       NOT NULL        COMMENT '退款金额',
    `REFUND_TIME`       datetime            NOT NULL        COMMENT '退款事件',
    `CONFIRMER_NAME`    varchar(64)         NOT NULL        COMMENT '操作人姓名',
    CONSTRAINT PK_ORDER_REFUND PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_ORDER_REFUND` COMMENT '订单-退款记录表';

# 订单-检测数据表
DROP TABLE IF EXISTS `GHEALTH_ORDER_TESTING_DATA`;
CREATE TABLE `GHEALTH_ORDER_TESTING_DATA`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_ID`          varchar(64)         NOT NULL        COMMENT '订单ID',
    `UPLOAD_RECORD_ID`  varchar(64)         NOT NULL        COMMENT '上传记录ID',
    `DATA_DETAILS`      text                NOT NULL        COMMENT '上传数据详情',
    CONSTRAINT PK_ORDER_TESTING_DATA PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_ORDER_TESTING_DATA` COMMENT '订单-检测数据表';

# 订单-检测评估-项目评估结果
DROP TABLE IF EXISTS `GHEALTH_ORDER_EVAL_ITEM`;
CREATE TABLE `GHEALTH_ORDER_EVAL_ITEM`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_ID`          varchar(64)         NOT NULL        COMMENT '订单ID',
    `ITEM_ID`           varchar(64)         NOT NULL        COMMENT '检测项目ID',
    `EVAL_VALUE`        decimal(10,4)                       COMMENT '评估结果',
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
    `GENETYPE`          varchar(8)                          COMMENT '基因型',
    `EVAL_VALUE`        decimal(10,4)                       COMMENT '评估结果',
    CONSTRAINT PK_ORDER_EVAL_ITEM_LOCUS PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_ORDER_EVAL_ITEM_LOCUS` COMMENT '订单-检测评估-项目位点评估结果表';

# 订单-报告生成任务表
DROP TABLE IF EXISTS `GHEALTH_ORDER_REPORT_GENERATE_TASK`;
CREATE TABLE `GHEALTH_ORDER_REPORT_GENERATE_TASK`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `STATUS`            tinyint(1)          NOT NULL        COMMENT '任务状态 0->生成中  1->成功  2->失败',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`      varchar(64)                         COMMENT '创建人姓名',
    `FINISH_TIME`       datetime                            COMMENT '完成时间',
    `ERROR_CODE`        varchar(32)                         COMMENT '错误码',
    `ERROR_MESSAGE`     varchar(256)                        COMMENT '错误信息',
    `WORD_FILE_URL`     varchar(256)                        COMMENT 'WORD报告路径',
    `PDF_FILE_URL`      varchar(256)                        COMMENT 'PDF报告路径',
    CONSTRAINT PK_ORDER_REPORT_GENERATE_TASK PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_ORDER_REPORT_GENERATE_TASK` COMMENT '订单-报告生成任务表';

# 检测数据上传记录
DROP TABLE IF EXISTS `GHEALTH_TESTING_DATA_UPLOAD_RECORD`;
CREATE TABLE `GHEALTH_TESTING_DATA_UPLOAD_RECORD`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `TITLE`             varchar(256)        NOT NULL        COMMENT '简要描述',
    `FILENAME`          varchar(256)        NOT NULL        COMMENT '上传文件名',
    `DOWNLOAD_URL`      varchar(256)        NOT NULL        COMMENT '下载路径',
    `UPLOAD_TIME`       datetime            NOT NULL        COMMENT '上传时间',
    `UPLOADER_NAME`     varchar(64)         NOT NULL        COMMENT '上传人姓名',
    CONSTRAINT PK_TESTING_DATA_UPLOAD_RECORD PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_TESTING_DATA_UPLOAD_RECORD` COMMENT '检测数据上传记录表';

# 报告模板
DROP TABLE IF EXISTS `GHEALTH_REPORT_TEMPLATE`;
CREATE TABLE `GHEALTH_REPORT_TEMPLATE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `PRODUCT_ID`        varchar(64)         NOT NULL        COMMENT '产品ID',
    `CODE`              varchar(64)         NOT NULL        COMMENT '模板编号',
    `NAME`              varchar(128)        NOT NULL        COMMENT '模板名称',
    `AGENCY_CUSTOMIZED` tinyint(1)          NOT NULL        COMMENT '定制模板：0-否 1-是',
    `AGENCY_ID`         varchar(64)                         COMMENT '代理商ID',
    `TSDG_KEY`          varchar(64)         NOT NULL        COMMENT '文档生成应用中的模板ID',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`      varchar(64)         NOT NULL        COMMENT '创建人姓名',
    `UPDATE_TIME`       datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`      varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`           tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`       datetime                            COMMENT '删除时间',
    `DELETOR_NAME`      varchar(64)                         COMMENT '删除人姓名',
    CONSTRAINT PK_REPORT_TEMPLATE PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_REPORT_TEMPLATE` COMMENT '报告模板表';

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

# 事件通知配置表
DROP TABLE IF EXISTS `GHEALTH_EVENT_NOTIFY_CONFIG`;
CREATE TABLE `GHEALTH_EVENT_NOTIFY_CONFIG`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `AGENCY_CUSTOMIZED`     tinyint(1)          NOT NULL        COMMENT '代理定制配置：0-否 1-是',
    `AGENCY_ID`             varchar(64)                         COMMENT '代理商ID',
    `CONFIG_DETAILS`       	varchar(512)                      	COMMENT '配置详情',
    `CREATE_TIME`           datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`          varchar(64)         NOT NULL        COMMENT '创建人姓名',
    `UPDATE_TIME`           datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`          varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`               tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`           datetime                            COMMENT '删除时间',
    `DELETOR_NAME`          varchar(64)                         COMMENT '删除人姓名',
    CONSTRAINT PK_EVENT_NOTIFY_CONFIG PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_EVENT_NOTIFY_CONFIG` COMMENT '事件通知配置表';

# 邮件服务配置
DROP TABLE IF EXISTS `GHEALTH_EMAIL_SENDER`;
CREATE TABLE `GHEALTH_EMAIL_SENDER`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `PROTOCOL`              varchar(16)         NOT NULL        COMMENT '协议类型',
    `SERVER_HOST`           varchar(128)        NOT NULL        COMMENT '邮件服务器主机',
    `SERVER_PORT`           integer             NOT NULL        COMMENT '邮件服务器端口',
    `USERNAME`              varchar(32)         NOT NULL        COMMENT '验证用户名',
    `PASSWORD`              varchar(256)        NOT NULL        COMMENT '验证密码',
    `FROM_ADDRESS`          varchar(128)        NOT NULL        COMMENT '发送人邮件地址',
    `FROM_PERSONAL`         varchar(128)        NOT NULL        COMMENT '发送人名称',
    `CREATE_TIME`           datetime            NOT NULL        COMMENT '创建时间',
    `CREATOR_NAME`          varchar(64)         NOT NULL        COMMENT '创建人姓名',
    `UPDATE_TIME`           datetime                            COMMENT '最近更新时间',
    `UPDATOR_NAME`          varchar(64)                         COMMENT '最近更新人姓名',
    `DELETED`               tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`           datetime                            COMMENT '删除时间',
    `DELETOR_NAME`          varchar(64)                         COMMENT '删除人姓名',
    CONSTRAINT PK_EMAIL_SENDER PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_EMAIL_SENDER` COMMENT '邮件服务配置';

# 文档表
DROP TABLE IF EXISTS `GHEALTH_OBJECT_STORAGE`;
CREATE TABLE `GHEALTH_OBJECT_STORAGE`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `STORAGE_TYPE`          varchar(16)         NOT NULL        COMMENT 'LOCAL:本地,ALI-OSS:阿里OSS',
    `STORAGE_DETAILS`       varchar(512)        NOT NULL        COMMENT '存储详情',
    CONSTRAINT PK_OBJECT_STORAGE PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_OBJECT_STORAGE` COMMENT '文档表';

# OPEN-API-CREDENTIALS
DROP TABLE IF EXISTS `GHEALTH_OPEN_API_CREDENTIALS`;
CREATE TABLE `GHEALTH_OPEN_API_CREDENTIALS`
(
    `ID`                    varchar(64)         NOT NULL                    COMMENT '主键',
    `ACCESS_KEY`            varchar(64)         NOT NULL        UNIQUE      COMMENT 'Key',
    `ACCESS_SECRET`         varchar(64)         NOT NULL                    COMMENT 'Secret',
    `AGENCY_ID`             varchar(64)                         UNIQUE      COMMENT '代理商ID',
    `ENABLED`               tinyint(1)          NOT NULL                    COMMENT '是否可用',
    `CREATE_TIME`           datetime            NOT NULL                    COMMENT '创建时间',
    `CREATOR_NAME`          varchar(64)         NOT NULL                    COMMENT '创建人姓名',
    `UPDATE_TIME`           datetime                                        COMMENT '最近更新时间',
    `UPDATOR_NAME`          varchar(64)                                     COMMENT '最近更新人姓名',
    `DELETED`               tinyint(1)          NOT NULL                    COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`           datetime                                        COMMENT '删除时间',
    `DELETOR_NAME`          varchar(64)                                     COMMENT '删除人姓名',
    CONSTRAINT PK_OPEN_API_CREDENTIALS PRIMARY KEY (`ID`)
)
;
ALTER TABLE `GHEALTH_OPEN_API_CREDENTIALS` COMMENT 'OPEN-API-CREDENTIALS';

INSERT INTO GHEALTH_OPEN_API_CREDENTIALS (ID, ACCESS_KEY, ACCESS_SECRET, ENABLED, CREATE_TIME, CREATOR_NAME, DELETED) VALUES ('00194834f660427e81df0a3feae7f086', 'e8e453f7631d4b459a51b539ef810b6b', '4cad11e1f5ce4c1bbbcbe5a7e05438a9', 1, NOW(), '管理员', 0);