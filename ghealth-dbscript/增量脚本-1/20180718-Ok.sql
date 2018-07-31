DROP TABLE IF EXISTS `ghealth_sms_send`;
CREATE TABLE `ghealth_sms_send` (
  `ID` varchar(64) NOT NULL,
  `AGENCY_ID` varchar(64) NOT NULL COMMENT '代理商id',
  `STATUS` varchar(64) NOT NULL COMMENT '短信发送事件  1 签收 2 送检  3 报告',
  `PHONE` varchar(64) NOT NULL COMMENT '手机号码',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `TEMPLATE_ID` varchar(64) NOT NULL COMMENT '短信模板id',
  `SENDED` tinyint(1) NOT NULL COMMENT '是否发送'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短息发送记录表';
