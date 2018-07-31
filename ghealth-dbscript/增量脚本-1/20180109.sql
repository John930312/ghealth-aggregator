# 事件通知配置表
DROP TABLE IF EXISTS `GHEALTH_EVENT_NOTIFY_CONFIG`;
CREATE TABLE `GHEALTH_EVENT_NOTIFY_CONFIG`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `AGENCY_CUSTOMIZED`     tinyint(1)          NOT NULL        COMMENT '代理定制配置：0-否 1-是',
    `AGENCY_ID`             varchar(64)                         COMMENT '代理商ID',
    `CONFIG_DETAILS`        varchar(512)                        COMMENT '配置详情',
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