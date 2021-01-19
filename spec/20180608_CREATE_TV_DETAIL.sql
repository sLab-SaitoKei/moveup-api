-- Table `moveup`.`tv_detail` // tv_detail詳細
CREATE TABLE IF NOT EXISTS `moveup`.`tv_detail` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `tv_id`               INT             NOT NULL,                                -- tvId
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `detail`              VARCHAR(2048)   NOT NULL,                                -- 詳細
  `type`                TINYINT(2)      NOT NULL,                                -- type(1:会員, 2:すべての方)
  `date`                DATETIME        NOT NULL ,                               -- ニュース日付
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `category`            TINYINT(2)      NOT NULL DEFAULT 0,                      -- カテゴリ (0:不明)
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `tv_detail_idx1` (`tv_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tv_detail詳細'