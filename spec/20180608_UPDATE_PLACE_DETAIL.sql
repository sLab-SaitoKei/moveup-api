ALTER TABLE `moveup`.`place_detail`
ADD COLUMN `publish_start` datetime NOT NULL DEFAULT '1970-01-01 00:00:01'  AFTER `coordinate2`;

ALTER TABLE `moveup`.`place_detail`
ADD COLUMN `publish_end` datetime NOT NULL DEFAULT '1970-01-01 00:00:01'  AFTER `publish_start`;