INSERT INTO `ghealth_authority` (`ID`, `PARENT_ID`, `CODE`, `NAME`, `SORT`) VALUES ('2899ca98b2f546beab81509ced1338f8', 'ffe3b21ec2cb41fdbcf21b167e9b3428', 'AGENCY.SHORTMESSAGE.FESTIVAL.CREATE', '节日祝福', '5');
INSERT INTO `ghealth_resource` (`ID`, `NAME`, `URI`) VALUES ('8d2154694cde453087442f9a5149ff00', '短信节日祝福', '/shortMessage/shortMessage_festival_create.jsp');
INSERT INTO `ghealth_authority_resource` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2899ca98b2f546beab81509ced1338f8', '8d2154694cde453087442f9a5149ff00');

INSERT INTO `ghealth_dict` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('f7e8ec9bceca4d398e5863bff34e8c19', NULL, 'FESTIVAL_TEMPLATE', '短信模板', NULL, '14', '0');
INSERT INTO `ghealth_dict` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('184e498144be4afc97e6d20824e69527', 'f7e8ec9bceca4d398e5863bff34e8c19', 'FESTIVAL_TEMPLATE', '中秋节祝福', '328201', '1', '0');
