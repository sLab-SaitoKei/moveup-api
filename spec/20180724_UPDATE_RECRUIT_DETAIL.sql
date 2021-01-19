ALTER TABLE `moveup`.`recruit_detail`
ADD COLUMN `spot` VARCHAR(255)  NOT NULL DEFAULT "" AFTER `tel`,
ADD COLUMN `work_day` VARCHAR(255)  NOT NULL DEFAULT "" AFTER `spot`,
ADD COLUMN `work_period` VARCHAR(255)  NOT NULL DEFAULT "" AFTER `work_day`,
ADD COLUMN `executive` VARCHAR(255)  NOT NULL DEFAULT "" AFTER `work_period`,
ADD COLUMN `acceptance` VARCHAR(255)  NOT NULL DEFAULT "" AFTER `executive`,
ADD COLUMN `company_name` VARCHAR(255)  NOT NULL DEFAULT "" AFTER `acceptance`