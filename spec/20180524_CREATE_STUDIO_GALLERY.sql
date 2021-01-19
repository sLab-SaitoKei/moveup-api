-- Table `moveup`.`studio_top` // studioページ画像と動画
CREATE TABLE IF NOT EXISTS `moveup`.`studio_gallery` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `url`                 VARCHAR(255)    NOT NULL,                                -- 画像や動画url
  `type`                TINYINT(2)      NOT NULL,                                -- タイプ(1:画像, 2:動画)
  `score`               INT             NOT NULL,                                -- 位置を表示する
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `score` (`score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='studioページ画像と動画';
