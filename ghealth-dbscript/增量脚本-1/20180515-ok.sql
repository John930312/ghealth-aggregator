#  代理价格修改记录表
DROP TABLE IF EXISTS `ghealth_agency_product_old_price`;
CREATE TABLE `ghealth_agency_product_old_price`
(
`ID`  varchar(64) NOT NULL COMMENT '主键' ,
`AGENCY_PRODUCT_ID` varchar(64) NOT NULL COMMENT '代理商产品ID' ,
`OLD_AGENCY_PRICE` decimal(10,2) NOT NULL COMMENT '修改前代理价格' ,
`CHANGED_AGENCY_PRICE` decimal(10,2) NOT NULL COMMENT '修改后代理价格' ,
`DETAIL` text NOT NULL COMMENT '修改备注' ,
`OPERATE_TIME` datetime NOT NULL COMMENT '修改时间' ,
`OPERATOR` varchar(64) NOT NULL COMMENT '操作人' ,
PRIMARY KEY (`ID`)
)COMMENT='代理价格修改记录表';
