-- Table `moveup`.`settlement` // settlement
CREATE TABLE IF NOT EXISTS `moveup`.`settlement` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `customer_id`         INT UNSIGNED    NOT NULL,                                -- customer_id
  `item_type`           TINYINT(2)      NOT NULL,                                -- 1:shop, 2:goods, 3:top, 4:freepaper, 5:event, 6:news
  `item_id`             INT UNSIGNED    NOT NULL,                                -- itemID
  `type`                TINYINT(2)      NOT NULL,                                -- 1:coupon, 2:score，3:advertisement, 4:monthly_expenses, 5:subshop
  `amount`              INT             NOT NULL,                                -- 決済金額
  `yyyymm`              INT             NOT NULL,                                -- bill yyyyMM
  `status`              TINYINT(1)      NOT NULL DEFAULT 0,                     -- 0:Unsettled, 1:already settled
  `method`              TINYINT(2)      NOT NULL,                                -- 1:Credit Card, 2:Bank account buckle, 3:Bank remittance
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `content`             VARCHAR(1024)   NOT NULL,                                -- 内容
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='settlement';