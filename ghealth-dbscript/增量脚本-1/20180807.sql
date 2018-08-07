#特殊订单
INSERT INTO `ghealth_authority` (`ID`, `PARENT_ID`, `CODE`, `NAME`, `SORT`) VALUES ('5d2b075a93c240438c2ef452a77840dd', '846a4ade6e344a51a0b78f7c6172dc9b', 'BUSINESS.SPECIALORDER', '特殊订单', '8');
INSERT INTO `ghealth_resource` (`ID`, `NAME`, `URI`) VALUES ('b8b8ecc0273848dd854e44341b7f0f04', '特殊订单', '/order/specialList.jsp');
INSERT INTO `ghealth_authority_resource` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('5d2b075a93c240438c2ef452a77840dd', 'b8b8ecc0273848dd854e44341b7f0f04');
INSERT INTO `ghealth_menu` (`ID`, `PARENT_ID`, `NAME`, `URI`, `SORT`, `ICON`) VALUES ('dfadf17b64dd44cd87962e1e91be6212', '8970819bc2254ddebe000bfee4ca0a8d', '特殊订单', '/order/specialList.jsp', '8', 'fa-tasks');
