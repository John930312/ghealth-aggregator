UPDATE `ghealth_testing_item` SET  `EVAL_GRADE_DETAILS`='{\"grades\":[{\"gradeCount\":3,\"intervals\":[{\"grade\":3,\"min\":4.1,\"max\":\"\"},{\"grade\":2,\"min\":0,\"max\":4.1}]},{\"gradeCount\":5,\"intervals\":[{\"grade\":4,\"min\":4.1,\"max\":\"\"},{\"grade\":2,\"min\":0,\"max\":4.1}]}]}' WHERE (`CODE`='LACE1');

UPDATE `ghealth_testing_item` SET  `EVAL_ALGORITHM`='1', `EVAL_ALGORITHM_DETAILS`='{\"mappingDisease\": false,\"maleAverageValue\": 1,\"femaleAverageValue\": 1}', `EVAL_GRADE_DETAILS`='{\"grades\":[{\"gradeCount\":3,\"intervals\":[{\"grade\":3,\"min\":4,\"max\":\"\"},{\"grade\":2,\"min\":0,\"max\":4}]},{\"gradeCount\":5,\"intervals\":[{\"grade\":4,\"min\":4,\"max\":\"\"},{\"grade\":2,\"min\":0,\"max\":4}]}]}' WHERE (`CODE`='LACE1');

