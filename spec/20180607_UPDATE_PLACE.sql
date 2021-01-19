ALTER TABLE `moveup`.`place`
ADD COLUMN `coordinate1` int(10)  NULL  AFTER `flic_url`;

ALTER TABLE `moveup`.`place`
ADD COLUMN `coordinate2`  int(10)  NULL  AFTER `coordinate1`;