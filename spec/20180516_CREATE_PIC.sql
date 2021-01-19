-- Table `moveup`.`pic` // 画像
CREATE TABLE IF NOT EXISTS `moveup`.`pic` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `item_id`             INT             NOT NULL,                                -- itemID
  `item_type`           TINYINT(2)      NOT NULL,                                -- itemType(1:eventStar, 2:eventComment)
  `pic_url`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像url
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `pic_idx1` (`item_id`,`item_type`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='画像';