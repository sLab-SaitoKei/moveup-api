-- Table `moveup`.`settlement_master` // settlement_master
CREATE TABLE IF NOT EXISTS `moveup`.`settlement_master` (
  `id`              INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `settlement_type` TINYINT(2)      NOT NULL,                                -- 1:coupon, 2:score，3:advertisement, 4:monthly_expenses, 5:subshop
  `content`         VARCHAR(255)    NOT NULL,                                -- 内容
  `create_datetime` DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime` DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`         TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`            VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `settlement_master_idx1` (`settlement_type`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='settlement_master';