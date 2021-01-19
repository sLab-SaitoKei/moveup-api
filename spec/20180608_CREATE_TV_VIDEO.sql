-- Table `moveup`.`tv_video` // tv_video詳細
CREATE TABLE IF NOT EXISTS `moveup`.`tv_video` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `tv_detail_id`        INT             NOT NULL,                                -- tvDetailId
  `first_url`           VARCHAR(255)    NOT NULL DEFAULT "",                     -- firstUrl
  `last_url`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- lastUrl
  `pic_url`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像url
  `status`              TINYINT(2)      NOT NULL DEFAULT 0,                      -- status(1:変換中, 2:完成する)
  `video_type`          TINYINT(2)      NOT NULL,                                -- videoType(1:ブルーレイ, 2:ハイビジョン, 3:)
  `category`            TINYINT(2)      NOT NULL DEFAULT 0,                      -- カテゴリ (0:不明)
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `tv_detail_id_idx1` (`tv_detail_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tv_video詳細'