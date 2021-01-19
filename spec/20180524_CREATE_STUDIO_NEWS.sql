-- Table `moveup`.`studio_news` // スタジオ
CREATE TABLE IF NOT EXISTS `moveup`.`studio_news` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `uuid`                VARCHAR(16)     NOT NULL,                                -- UUID
  `date`                DATETIME        NOT NULL,                                -- ニュース日付
  `sort_score`          INT             NOT NULL DEFAULT 0,                      -- TOPに表示(0:不明, 1:あり, 2:なし)
  `category`            TINYINT(2)      NOT NULL DEFAULT 0,                      -- カテゴリ (0:不明)
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `excerpt`             VARCHAR(255)    NOT NULL,                                -- 抜粋
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `studio_news_idx1` (`uuid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='スタジオ';
