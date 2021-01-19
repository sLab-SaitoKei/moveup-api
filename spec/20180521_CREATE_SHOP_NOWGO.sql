-- Table `moveup`.`shop_nowgo` // 行ける情報
CREATE TABLE IF NOT EXISTS `moveup`.`shop_nowgo` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `shop_id`             INT UNSIGNED    NOT NULL ,                               -- 店舗ID
  `category`            TINYINT(2)      NOT NULL DEFAULT 0,                      -- カテゴリ (0:不明, 1:飲食店, 2:求人)
  `title`               VARCHAR(255)    NOT NULL DEFAULT "",                     -- タイトル
  `pic_url1`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像1
  `desc`                VARCHAR(1024)   NOT NULL DEFAULT "",                     -- 紹介文
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `shop_nowgo_idx1` (`shop_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='行ける情報';