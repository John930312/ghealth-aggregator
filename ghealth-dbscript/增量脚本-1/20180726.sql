#药物管理
INSERT INTO `ghealth_authority` (`ID`, `PARENT_ID`, `CODE`, `NAME`, `SORT`) VALUES ('5b330a1bf89f428cb9a45804927ae4d0', '8649bce39de35d4f96a946d286faf1fb', 'KNOWLEDGEBASE.DRUG', '药物管理', '4');
INSERT INTO `ghealth_authority` (`ID`, `PARENT_ID`, `CODE`, `NAME`, `SORT`) VALUES ('bce3629d94a94c33852df79f1ee1b884', '5b330a1bf89f428cb9a45804927ae4d0', 'KNOWLEDGEBASE.DRUG.CREATE', '新建药物', '1');
INSERT INTO `ghealth_authority` (`ID`, `PARENT_ID`, `CODE`, `NAME`, `SORT`) VALUES ('d1c467fa1b464c379e63ea1b0bac5742', '5b330a1bf89f428cb9a45804927ae4d0', 'KNOWLEDGEBASE.DRUG.MODIFY', '修改药物', '2');
INSERT INTO `ghealth_authority` (`ID`, `PARENT_ID`, `CODE`, `NAME`, `SORT`) VALUES ('6e65127c8c9e4c9098c2a8f35d3268b1', '5b330a1bf89f428cb9a45804927ae4d0', 'KNOWLEDGEBASE.DRUG.DELETE', '删除药物', '3');
INSERT INTO `ghealth_authority` (`ID`, `PARENT_ID`, `CODE`, `NAME`, `SORT`) VALUES ('78cfb597a1bf4fa4b3920eda78c6cb1b', '5b330a1bf89f428cb9a45804927ae4d0', 'KNOWLEDGEBASE.DRUG.DISPLAY', '查询药物', '4');
INSERT INTO `ghealth_resource` (`ID`, `NAME`, `URI`) VALUES ('008b7d413a2e4ffd9f887c23b30d7095', '药物管理列表', '/drug/list.jsp');
INSERT INTO `ghealth_resource` (`ID`, `NAME`, `URI`) VALUES ('8a6e2747ef0641c9a5e939b6890f84d0', '药物新增', '/drug/create.jsp');
INSERT INTO `ghealth_resource` (`ID`, `NAME`, `URI`) VALUES ('4f521e20e81d416aae58f4bc41c0fdbf', '药物修改', '/drug/modify.jsp');
INSERT INTO `ghealth_resource` (`ID`, `NAME`, `URI`) VALUES ('c18ee877bfce4c3c81d1c6cdc8f7ac36', '药物删除', '/drug/delete.jsp');
INSERT INTO `ghealth_resource` (`ID`, `NAME`, `URI`) VALUES ('b8bbc97793ec4a578881ac2b1b29097d', '药物详情', '/drug/display.jsp');
INSERT INTO `ghealth_authority_resource` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('bce3629d94a94c33852df79f1ee1b884', '8a6e2747ef0641c9a5e939b6890f84d0');
INSERT INTO `ghealth_authority_resource` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('d1c467fa1b464c379e63ea1b0bac5742', '4f521e20e81d416aae58f4bc41c0fdbf');
INSERT INTO `ghealth_authority_resource` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('6e65127c8c9e4c9098c2a8f35d3268b1', 'c18ee877bfce4c3c81d1c6cdc8f7ac36');
INSERT INTO `ghealth_authority_resource` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('78cfb597a1bf4fa4b3920eda78c6cb1b', '008b7d413a2e4ffd9f887c23b30d7095');
INSERT INTO `ghealth_authority_resource` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('78cfb597a1bf4fa4b3920eda78c6cb1b', 'b8bbc97793ec4a578881ac2b1b29097d');