-- Table `moveup`.`tv` // tv詳細
CREATE TABLE IF NOT EXISTS `moveup`.`tv` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `uuid`                VARCHAR(16)     NOT NULL,                                -- UUID
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `office`              VARCHAR(255)    NOT NULL,                                -- 事務所
  `url`                 VARCHAR(255)    NOT NULL,                                -- 画像url
  `tv_type`             TINYINT(2)      NOT NULL,                                -- tvType(1:special, 2:kids, 3:girls, 4:lifre, 5:okayama)
  `sort_score`          INT             NOT NULL,                                -- TOPに表示
  `date`                DATETIME        NOT NULL ,                               -- 日付
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `category`            TINYINT(2)      NOT NULL DEFAULT 0,                      -- カテゴリ (0:不明)
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tv詳細'