-- Table `moveup`.`notify_now45` // now4、now5通知
CREATE TABLE IF NOT EXISTS `moveup`.`notify_now45` (
  `id`                INT UNSIGNED   NOT NULL AUTO_INCREMENT,                 -- プッシュ通知ID
  `user_id`           INT UNSIGNED   NOT NULL,                                -- ユーザID
  `item_id`           INT UNSIGNED   NOT NULL,                                -- 通知内容ID
  `push_type`         TINYINT(2)     NOT NULL,                                -- プッシュ通知種類 (0:All,1:お知らせ, 2,プッシュ通知)
  `notify_type`       TINYINT(2)     NOT NULL,                                -- 通知タイプ (1:now4, 2:now5)
  `notify_status`     TINYINT(2)     NOT NULL,                                -- ステータス(1:未開封, 2,開封ずみ)
  `push_status`       TINYINT(2)     NOT NULL,                                -- ステータス(1:,プッシュ未送信, 2,プッシュ送信ずみ)
  `publish_start`     DATETIME                 DEFAULT '1970-01-01 00:00:01', -- 掲載開始期間
  `publish_end`       DATETIME                 DEFAULT '1970-01-01 00:00:01', -- 掲載終了期間
  `create_datetime`   DATETIME       NOT NULL,                                -- レコード生成日時
  `update_datetime`   DATETIME       NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`           TINYINT(1)     NOT NULL,                                -- 論理削除フラグ
  `note`              varchar(255)   NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `user_id_idx1` (`user_id`),
  KEY `item_id_idx2` (`item_id`),
  KEY `push_type_idx3` (`push_type`),
  KEY `notify_type_idx4` (`notify_type`),
  KEY `notify_status_idx5` (`notify_status`),
  KEY `push_status_idx6` (`push_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='プッシュ通知';