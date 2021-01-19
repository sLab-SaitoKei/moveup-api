ALTER TABLE `moveup`.`place_detail`
ADD COLUMN `coordinate1` int(10)  NULL  AFTER `address`;

ALTER TABLE `moveup`.`place_detail`
ADD COLUMN `coordinate2`  int(10)  NULL  AFTER `coordinate1`;