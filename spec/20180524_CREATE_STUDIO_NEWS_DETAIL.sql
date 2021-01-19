-- Table `moveup`.`studio_news_detail` // スタジオ詳細
CREATE TABLE IF NOT EXISTS `moveup`.`studio_news_detail` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `studio_news_id`      INT UNSIGNED    NOT NULL,                                -- スタジオID
  `date`                DATETIME        NOT NULL,                                -- ニュース日付
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `desc1`               VARCHAR(2048)   NOT NULL DEFAULT "",                     -- 内容1
  `desc2`               VARCHAR(2048)   NOT NULL DEFAULT "",                     -- 内容2
  `desc3`               VARCHAR(2048)   NOT NULL DEFAULT "",                     -- 内容3
  `comment`             VARCHAR(2048)   NOT NULL DEFAULT "",                     -- コメント
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `studio_news_detail_idx1` (`studio_news_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='スタジオ詳細';

