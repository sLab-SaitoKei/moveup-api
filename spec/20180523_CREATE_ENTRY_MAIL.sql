-- Table `moveup`.`entry_mail` //応募メール
CREATE TABLE IF NOT EXISTS `moveup`.`entry_mail` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `entry_id`            INT UNSIGNED    NOT NULL,                                -- アイテムID
  `category`            TINYINT(2)      NOT NULL,                                -- カテゴリ
  `type`                TINYINT(2)      NOT NULL,                                -- タイプ(1,news-moveup 2news-event, 3event, )
  `title`               VARCHAR(255)    NOT NULL DEFAULT "",                     -- タイトル
  `subject`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 件名
  `detail`              VARCHAR(4096)   NOT NULL DEFAULT "",                     -- 内容
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `withdraw_idx1` (`entry_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='応募メール';