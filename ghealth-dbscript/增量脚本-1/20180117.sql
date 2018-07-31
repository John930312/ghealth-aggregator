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