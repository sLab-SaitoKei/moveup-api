-- Table `moveup`.`withdraw` // 退会
CREATE TABLE IF NOT EXISTS `moveup`.`withdraw` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `user_id`             INT UNSIGNED    NOT NULL,                                -- ユーザーID
  `reason`              TINYINT(2)      NOT NULL,                                -- 退会理由(1,サービスがなかった 2,他のサービスの方が良い 3,使いにくい・わかりにくい 4,サポート良くなかった 5,重複アカウント 6その他)
  `detail`              VARCHAR(2048)   NOT NULL DEFAULT "",                     -- 退会詳細
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `withdraw_idx1` (`user_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='退会';