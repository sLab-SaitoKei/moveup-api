-- Table `moveup`.`batch_status` // batch_status
CREATE TABLE IF NOT EXISTS `moveup`.`batch_status` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `batch_type`          TINYINT(2)      NOT NULL,                                -- 1:admin_customer, 2:payment, 3:pic, 4:freepaper, 5:tv
  `status`              TINYINT(2)      NOT NULL,                                -- 0:notBeginning, 1:working, 2:finish
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='batch_status';