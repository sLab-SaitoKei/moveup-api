CREATE DATABASE IF NOT EXISTS `moveup`;

-- Table `moveup`.`system_conf` // system状態設定
CREATE TABLE IF NOT EXISTS `moveup`.`system_conf` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,      -- ID
  `setting`             INT             NOT NULL,                     -- KEY(1:システム状態 2 日coupon price　3月coupon price)
  `value`               INT             NOT NULL,                     -- VALUE(システム状態->1:正常, 2:メンテナンス 日coupon price-> 日500円, 月coupon price->月20000円)
  PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='system状態設定';

-- Table `moveup`.`user` // ユーザー
CREATE TABLE IF NOT EXISTS `moveup`.`user` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ユーザーID
  `status`              TINYINT(2)      NOT NULL DEFAULT '0',                    -- ユーザステータス(1:有効, 2:仮退会, 3:無効, 4:退会済)
  `uuid`                VARCHAR(36)     COLLATE UTF8_BIN NOT NULL,               -- UUID
  `expire_datetime`     DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- UUID有効期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `user_idx1` (`uuid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ユーザー';

-- Table `moveup`.`user_foreign`  // 外部連携
CREATE TABLE IF NOT EXISTS `moveup`.`user_foreign` (
  `id`              INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `user_id`         INT UNSIGNED    NOT NULL,                                -- ユーザーID
  `foreign_type`    TINYINT(2)      NOT NULL,                                -- ユーザー連携タイプ(1:twitter, 2:facebook, 3:mail)
  `foreign_id`      VARCHAR(64)     NOT NULL,                                -- ユーザー連携ID、またメルアド
  `is_login`        TINYINT(2)      NOT NULL,                                -- ログインフラグ (1:ログインID, 2:連携ID)
  `access_token`    VARCHAR(255)    NOT NULL DEFAULT '',                     -- アクセストークン
  `refresh_token`   VARCHAR(255)    NOT NULL DEFAULT '',                     -- リフレッシュトークン
  `token_secret`    VARCHAR(255)    NOT NULL DEFAULT '',                     -- トークンシークレット
  `password`        VARCHAR(255)    NOT NULL DEFAULT '',                     -- パースワード
  `expire_datetime` DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- トークン有効期間
  `data`            TEXT                     DEFAULT NULL,                   -- 外部IDに関連させて憶えたい任意のデータ
  `create_datetime` DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime` DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`         TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`            varchar(255)    NOT NULL,                                -- ノート
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_foreign_idx1` (`foreign_type`,`foreign_id`,`user_id`),
  KEY `user_foreign_idx2` (`foreign_type`,`foreign_id`,`password`),
  KEY `user_foreign_idx3` (`foreign_id`),
  KEY `user_foreign_idx4` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='外部連携';

-- Table `moveup`.`session_id`  // セッション
CREATE TABLE IF NOT EXISTS `moveup`.`session_id` (
  `id`              INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `uuid`            VARCHAR(36)     NOT NULL,                                -- UUID
  `session_id`      VARCHAR(255)    NOT NULL,                                -- セッションID
  `expire_datetime` DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- トークン有効期間
  `create_datetime` DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime` DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`         TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`            varchar(255)    NOT NULL,                                -- ノート
  PRIMARY KEY (`id`),
  UNIQUE KEY session_id_idx1(uuid, session_id),
  KEY `session_id_idx2` (`session_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='セッションID';

-- Table `moveup`.`person`  // ユーザー情報
CREATE TABLE IF NOT EXISTS `moveup`.`person` (
  `id`                      INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ユーザーID
  `user_id`                 INT UNSIGNED    NOT NULL,                                -- user_id
  `nickname`                VARCHAR(32)              DEFAULT '',                     -- ニックネーム
  `first_name`              VARCHAR(255)    NOT NULL DEFAULT '',
  `second_name`             VARCHAR(255)    NOT NULL DEFAULT '',
  `first_name_kana`         VARCHAR(255)    NOT NULL DEFAULT '',
  `second_name_kana`        VARCHAR(255)    NOT NULL DEFAULT '',
  `gender`                  TINYINT(2)               DEFAULT 0,                      -- 性別(0:unknown, 1:male, 2:female)
  `birthday`                DATE                     DEFAULT '1970-01-01',           -- 誕生日
  `area`                    TINYINT(2)               DEFAULT 0,                      -- 区域
  `thumbnail_url`           VARCHAR(255)    NOT NULL DEFAULT "",                     -- サムネイルURL
  `career`                  TINYINT(2)      NOT NULL DEFAULT 0,                      -- 職業
  `zipcode`                 VARCHAR(255)    NOT NULL DEFAULT "",                     -- 郵便番号
  `address`                 VARCHAR(255)    NOT NULL DEFAULT "",                     -- 住所
  `mail`                    VARCHAR(255)    NOT NULL DEFAULT "",                     -- メール
  `create_datetime`         DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`         DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`                 TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                    VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_unique` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ユーザー情報';

-- Table `moveup`.`one_time_key` // 認証ワンタイムキー
CREATE TABLE IF NOT EXISTS `moveup`.`one_time_key` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `type`                TINYINT(2)      NOT NULL,                                -- 1:regist, 2:forget
  `token`               VARCHAR(64)     NOT NULL,                                -- トークン
  `mail`                VARCHAR(64)     NOT NULL DEFAULT '',                     -- メール
  `expire_datetime`     DATETIME        NOT NULL,                                -- トークン有効期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  UNIQUE KEY `one_time_key_idx1` (`type`,`token`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='認証ワンタイムキー';

-- Table `moveup`.`admin_user` // 管理画面ユーザー
CREATE TABLE IF NOT EXISTS `moveup`.`admin_user` (
  `id`                 INT UNSIGNED       NOT NULL AUTO_INCREMENT,                -- ID
  `login_name`         VARCHAR(64)        NOT NULL,                               -- ログイン名
  `status`             TINYINT(2)         NOT NULL DEFAULT 0,                     -- 1:有効, 2:無効
  `password`           VARCHAR(255)       NOT NULL,                               -- Password
  `show_name`          VARCHAR(64)        NOT NULL,                               -- 表示名
  `mail`               VARCHAR(64)        NOT NULL,                               -- メール
  `role`               INT UNSIGNED       NOT NULL,                               -- 役割
  `create_datetime`    DATETIME           NOT NULL,
  `create_user`        INT UNSIGNED       DEFAULT NULL,
  `update_datetime`    DATETIME           NOT NULL DEFAULT '1970-01-01 00:00:01', -- レコード最後更新日時
  `update_user`        INT UNSIGNED       DEFAULT NULL,
  `del_flg`            TINYINT(1)         NOT NULL DEFAULT FALSE,
  `note`               VARCHAR(255)       NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name_unique` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Table `moveup`.`admin_customer` // お客様管理ユーザー
CREATE TABLE IF NOT EXISTS `moveup`.`admin_customer` (
  `id`                 INT UNSIGNED       NOT NULL AUTO_INCREMENT,                -- ID
  `shop_id`            INT UNSIGNED       NOT NULL,                               -- 店舗ID
  `login_name`         VARCHAR(64)        NOT NULL,                               -- ログイン名
  `status`             TINYINT(2)         NOT NULL DEFAULT 0,                     -- 1:有効, 2:無効
  `fc_type`            TINYINT(2)         NOT NULL,                               -- 1:管理店, 2:支店
  `fc_admin_id`        INT UNSIGNED       NOT NULL,                               -- fc_type=2のみ、管理店IDを保存する
  `customer_type`      TINYINT(2)         NOT NULL,                               -- 1:shop, 2:place ，3：recruit
  `password`           VARCHAR(255)       NOT NULL,                               -- Password
  `show_name`          VARCHAR(64)        NOT NULL,                               -- 表示名
  `mail`               VARCHAR(64)        NOT NULL,                               -- メール
  `role`               INT UNSIGNED       NOT NULL,                               -- 役割
  `create_datetime`    DATETIME           NOT NULL,
  `create_user`        INT UNSIGNED       DEFAULT NULL,
  `update_datetime`    DATETIME           NOT NULL DEFAULT '1970-01-01 00:00:01', -- レコード最後更新日時
  `update_user`        INT UNSIGNED       DEFAULT NULL,
  `del_flg`            TINYINT(1)         NOT NULL DEFAULT FALSE,
  `note`               VARCHAR(255)       NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name_unique` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Table `moveup`.`admin_role` // 管理ユーザー役割
CREATE TABLE IF NOT EXISTS `moveup`.`admin_role` (
  `id`                 INT UNSIGNED       NOT NULL AUTO_INCREMENT,
  `name`               VARCHAR(255)       NOT NULL,                              -- 名前
  `type`               TINYINT(2)         NOT NULL,                              -- ユーザータイプ(1:管理ユーザー, 2:お客様管理)
  `action`             INT                NOT NULL,                              -- アクション（ページ名、操作など）
  `privilege`          INT                NOT NULL,                              -- 権限(0:権限なし, 1:読み込み, 2:書き込み)
  `create_datetime`    DATETIME           NOT NULL,
  `update_datetime`    DATETIME           NOT NULL DEFAULT '1970-01-01 00:00:01', -- レコード最後更新日時
  `del_flg`            TINYINT(1)         NOT NULL DEFAULT FALSE,
  `note`               VARCHAR(255)       NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Table `moveup`.`entry` // 応募結果
CREATE TABLE IF NOT EXISTS `moveup`.`entry` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `user_id`             INT UNSIGNED    NOT NULL,                                -- user_id
  `entry_id`            INT UNSIGNED    NOT NULL,                                -- entry_id
  `entry_type`          TINYINT(2)      NOT NULL DEFAULT 0,                      -- 応募タイプ (1,news-moveup 2news-event, 3event, )
  `mail`                VARCHAR(255)    NOT NULL,                                -- メール
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `content`             VARCHAR(1024)   NOT NULL,                                -- 内容
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='応募結果';

-- Table `moveup`.`tag_detail` // tag_detail
CREATE TABLE IF NOT EXISTS `moveup`.`tag_detail` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `module_type`         TINYINT(2)      NOT NULL,                                -- モジュールタイプ(1:店舗, 2:求人)
  `tag_type`            TINYINT(2)      NOT NULL,                                -- タグタイプ
  `content`             INT             NOT NULL,                                -- 内容
  `desc`                VARCHAR(255)    NOT NULL,                                -- 内容
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `tag_detail_idx1` (`module_type`,`tag_type`,`content`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tag検索';

-- Table `moveup`.`shop_tag` // 店舗tag
CREATE TABLE IF NOT EXISTS `moveup`.`shop_tag` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `shop_id`             INT UNSIGNED    NOT NULL,                                -- 店舗ID
  `module_type`         TINYINT(2)      NOT NULL,                                -- モジュールタイプ(1:食べる, todo)
  `tag_type`            TINYINT(2)      NOT NULL,                                -- タグタイプ
                                                                                 -- 1:エリア(1:岡山市、TODO:確認要)
                                                                                 -- 2:昼間予算(低価格帯)
                                                                                 -- 3:夜間予算（低価格帯）
                                                                                 -- 4:営業曜日 8桁数字、左第一位はperfix、二位は日曜日、七位は土曜日、例：月曜日〜金曜日は10111110
                                                                                 -- 5:昼間開店時間(HHMM)
                                                                                 -- 6:昼間閉店時間(HHMM)
                                                                                 -- 7:昼間営業時間オーダー(HHMM)
                                                                                 -- 8:夜営開店時間(HHMM)
                                                                                 -- 9:夜営閉店時間(HHMM)
                                                                                 -- 10:夜営業時間オーダー(HHMM)
                                                                                 -- 11:こだわり条件(1:カード可,2:個室,3:貸切可,4:駐車場,5:飲み放題,6:食べ放題,7:子供可,8:ペート可,9:クーポン,10:テイクアウト,11:喫煙席,12:禁煙席)
                                                                                 -- 12:利用シーン(0:不明, 1:モーニング, 2:ランチ)
                                                                                 -- 13：最寄駅
                                                                                 -- 14：最寄駅徒歩時間
                                                                                 -- 15：平均予算
                                                                                 -- 16:昼間予算(高価格帯)
                                                                                 -- 17:夜間予算(高価格帯)
                                                                                 -- 18:メインメニュー(1:和食, 2:寿司 todo)
                                                                                 -- 19:サブメニュー(1:食べる, todo)
                                                                                 -- 20:ブランド
                                                                                 -- 21:座数
                                                                                 -- 22:施術時間
                                                                                 -- 23:収容人数
                                                                                 -- 24:部屋タイプ
                                                                                 -- 25:部屋数
                                                                                 -- 26:お問合せ可能時間
                                                                                 -- 27:料理料金
                                                                                 -- 28:飲物料金
                                                                                 -- 29:持込料金
                                                                                 -- 30:従業員数
                                                                                 -- 31:資本金
                                                                                 -- 32:定休日(日付　１～31日)
                                                                                 -- 33:定休日(曜日　1～4　第＊週/１～７　第＊週)
  `content`             INT             NOT NULL,                                -- 内容
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `shop_tag_idx1` (`shop_id`,`module_type`,`tag_type`),
  KEY `shop_tag_idx2` (`module_type`),
  KEY `shop_tag_idx3` (`module_type`,`tag_type`,`content`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='店舗tag';

-- Table `moveup`.`shop` // 店舗
CREATE TABLE IF NOT EXISTS `moveup`.`shop` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `uuid`                VARCHAR(16)     NOT NULL,                                -- UUID
  `shop_type`           TINYINT(2)      NOT NULL,                                -- 店舗タイプ(1:food 2:fation)
  `name`                VARCHAR(255)    NOT NULL,                                -- 店舗名
  `coordinate1`         INT UNSIGNED    NOT NULL,                                -- 座標x
  `coordinate2`         INT UNSIGNED    NOT NULL,                                -- 座標y
  `address`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 住所
  `excerpt`             VARCHAR(255)    NOT NULL,                                -- 抜粋
  `now4`                TINYINT(1)              ,                                -- NOW4（0:false(なし),1:true（あり）)
  `now5`                TINYINT(1)              ,                                -- NOW5（0:false(なし),1:true（あり）)
  `pic_url`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- サブ画像1
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `shop_idx1` (`uuid`),
  KEY `shop_idx2` (`shop_type`,`now4`),
  KEY `shop_idx3` (`shop_type`,`now5`),
  KEY `shop_idx4` (`shop_type`,`now4`,`now5`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='店舗';


-- Table `moveup`.`shop_detail` // 店舗詳細
CREATE TABLE IF NOT EXISTS `moveup`.`shop_detail` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `shop_type`           TINYINT(2)      NOT NULL,                                -- 店舗タイプ(1:food 2:fation)
  `shop_list_id`        INT UNSIGNED    NOT NULL,                                -- 店舗ID
  `name`                VARCHAR(255)    NOT NULL,                                -- 店舗名
  `pic_url1`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- メイン画像
  `pic_url2`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- サブ画像1
  `pic_url3`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- サブ画像2
  `pic_url4`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- サブ画像3
  `pic_url5`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- サブ画像4
  `pic_url6`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 360画像
  `video_url`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 動画
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `desc1`               VARCHAR(1024)   NOT NULL DEFAULT "",                     -- 内容
  `desc2`               VARCHAR(255)             DEFAULT "",                     -- 内容(座数)
  `desc3`               VARCHAR(255)             DEFAULT "",                     -- 内容(個室)
  `desc4`               VARCHAR(255)             DEFAULT "",                     -- 内容(貸切)
  `desc5`               VARCHAR(255)             DEFAULT "",                     -- 内容(事前予約)
  `desc6`               VARCHAR(255)             DEFAULT "",                     -- 内容(喫煙・禁煙)
  `desc7`               VARCHAR(255)             DEFAULT "",                     -- 内容(駐車場)
  `desc8`               VARCHAR(255)             DEFAULT "",                     -- 内容(テイクアウト)
  `desc9`               VARCHAR(255)             DEFAULT "",                     -- 内容(出前・宅配)
  `desc10`              VARCHAR(255)             DEFAULT "",                     -- 内容(飲み放題)
  `desc11`              VARCHAR(255)             DEFAULT "",                     -- 内容(コース料理)
  `desc12`              VARCHAR(255)             DEFAULT "",                     -- 内容(取扱いサービス)
  `desc13`              VARCHAR(255)             DEFAULT "",                     -- 内容(取り置き)
  `desc14`              VARCHAR(255)             DEFAULT "",                     -- 内容(平均施術時間)
  `desc15`              VARCHAR(255)             DEFAULT "",                     -- 内容(支払い方法)
  `desc16`              VARCHAR(255)             DEFAULT "",                     -- 内容(スタッフプロフィール)
  `desc17`              VARCHAR(255)             DEFAULT "",                     -- 内容(基本メニュー)
  `desc18`              VARCHAR(255)             DEFAULT "",                     -- 内容(団体予約)
  `desc19`              VARCHAR(255)             DEFAULT "",                     -- 内容(収容人数)
  `desc20`              VARCHAR(255)             DEFAULT "",                     -- 内容(宿泊施設)
  `desc21`              VARCHAR(255)             DEFAULT "",                     -- 内容(二次会)
  `desc22`              VARCHAR(255)             DEFAULT "",                     -- 内容(送迎)
  `desc23`              VARCHAR(255)             DEFAULT "",                     -- 内容(校訓)
  `desc24`              VARCHAR(255)             DEFAULT "",                     -- 内容(指導方針)
  `desc25`              VARCHAR(255)             DEFAULT "",                     -- 内容(食事)
  `desc26`              VARCHAR(255)             DEFAULT "",                     -- 内容(利用用途)
  `desc27`              VARCHAR(255)             DEFAULT "",                     -- 内容(プラン／コース)
  `desc28`              VARCHAR(255)             DEFAULT "",                     -- 内容(所属団体)
  `desc29`              VARCHAR(255)             DEFAULT "",                     -- 内容(店舗一覧)
  `desc30`              VARCHAR(255)             DEFAULT "",                     -- 内容(特徴設備)
  `desc31`              VARCHAR(255)             DEFAULT "",                     -- 内容(サービス一覧、主な取り扱い物件、主な取り扱いメニュー、主な取り扱いサービス、取り扱いペット)
  `desc32`              VARCHAR(255)             DEFAULT "",                     -- 内容(その他特徴設備)
  `desc33`              VARCHAR(255)             DEFAULT "",                     -- 内容(その他特徴サービス)
  `desc34`              VARCHAR(255)             DEFAULT "",                     -- 内容(ペット)
  `desc35`              VARCHAR(255)             DEFAULT "",                     -- 内容(HP)
  `desc36`              VARCHAR(255)             DEFAULT "",                     -- 内容(公式アカウント)
  `desc37`              VARCHAR(255)             DEFAULT "",                     -- 内容(関連、系列グループ店舗)
  `desc38`              VARCHAR(255)             DEFAULT "",                     -- 内容(備考)
  `desc39`              VARCHAR(255)             DEFAULT "",                     -- 内容(定休日)
  `desc40`              VARCHAR(255)             DEFAULT "",                     -- 内容(アクセス)
  `tel`                 VARCHAR(64)     NOT NULL DEFAULT "",                     -- 電話
  `address`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 住所
  `now5`                VARCHAR(255)             DEFAULT "",                     -- NOW5
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `shop_detail_idx1` (`shop_list_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='店舗詳細';

-- Table `moveup`.`shop_coupon` // お得情報
CREATE TABLE IF NOT EXISTS `moveup`.`shop_coupon` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `shop_id`             INT UNSIGNED    NOT NULL ,                               -- 店舗ID
  `category`            TINYINT(2)      NOT NULL DEFAULT 0,                      -- カテゴリ (0:不明, 1:飲食店, 2:求人)
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `pic_url1`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像1
  `desc`                VARCHAR(1024)   NOT NULL DEFAULT "",                     -- 紹介文
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `shop_coupon_idx1` (`shop_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='お得情報';

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

-- Table `moveup`.`corporate_info_detail` // コーポレートインフォ詳細
CREATE TABLE IF NOT EXISTS `moveup`.`corporate_info_detail` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `shop_type`           TINYINT(2)      NOT NULL,                                -- 店舗タイプ(8:コーポレートインフォ)
  `shop_list_id`        INT UNSIGNED    NOT NULL,                                -- 店舗ID
  `name`                VARCHAR(255)    NOT NULL,                                -- 企業名
  `pic_url1`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- メイン画像
  `pic_url2`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- サブ画像1
  `pic_url3`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- サブ画像2
  `pic_url4`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- サブ画像3
  `pic_url5`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- サブ画像4
  `pic_url6`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 360画像
  `video_url`           VARCHAR(255)    NOT NULL DEFAULT "",                     -- 動画
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `desc1`               VARCHAR(1024)   NOT NULL DEFAULT "",                     -- 内容
  `desc2`               VARCHAR(255)             DEFAULT "",                     -- 内容(設立)
  `desc3`               VARCHAR(255)             DEFAULT "",                     -- 内容(代表取締役)
  `desc4`               VARCHAR(255)             DEFAULT "",                     -- 内容(資本金)
  `desc5`               VARCHAR(255)             DEFAULT "",                     -- 内容(従業員数)
  `desc6`               VARCHAR(255)             DEFAULT "",                     -- 内容(事業内容)
  `desc7`               VARCHAR(255)             DEFAULT "",                     -- 内容(営業所、支店、支社)
  `desc8`               VARCHAR(255)             DEFAULT "",                     -- 内容(HP)
  `desc9`               VARCHAR(255)             DEFAULT "",                     -- 内容(公式アカウント)
  `desc10`              VARCHAR(255)             DEFAULT "",                     -- 内容(関連、系列グループ店舗)
  `desc11`              VARCHAR(255)             DEFAULT "",                     -- 内容(備考)
  `tel`                 VARCHAR(64)     NOT NULL DEFAULT "",                     -- 電話
  `address`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 住所
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `corporate_info_detail_idx1` (`shop_list_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='コーポレートインフォ詳細';

-- Table `moveup`.`free_paper` // フリーペーパ
CREATE TABLE IF NOT EXISTS `moveup`.`free_paper` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `uuid`                VARCHAR(16)     NOT NULL,                                -- UUID
  `volume`              INT             NOT NULL,                                -- ボリューム
  `pic_url`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `date`                VARCHAR(255)    NOT NULL,                                -- 日付
  `sort_score`          INT             NOT NULL,                                -- TOPに表示(0:不明, 1:あり, 2:なし)
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `free_paper_idx1` (`uuid`),
  KEY `free_paper_idx2` (`sort_score`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='フリーペーパ';

-- Table `moveup`.`free_paper_detail` // フリーペーパ詳細
CREATE TABLE IF NOT EXISTS `moveup`.`free_paper_detail` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `free_paper_id`       INT             NOT NULL,                                -- フリーペーパID
  `pic_type`            TINYINT(2)      NOT NULL,                      			 -- カテゴリ (1:IMG, 2:PDF)
  `pic_url`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `free_paper_detail_idx1` (`free_paper_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='フリーペーパ詳細';


-- Table `moveup`.`fav_detail` // 気に入り情報
CREATE TABLE IF NOT EXISTS `moveup`.`fav_detail` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `shop_id`             INT UNSIGNED    NOT NULL ,                               -- 店舗ID
  `type`                TINYINT(2)      NOT NULL DEFAULT 0,                      -- カテゴリ (0:不明, 1:飲食店, 2:求人, 13:event，14goods)
  `user_id`             INT UNSIGNED    NOT NULL,                                -- ユーザーID
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `fav_detail_idx1` (`user_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='気に入り情報';

-- Table `moveup`.`recruit_tag` // 求人tag
CREATE TABLE IF NOT EXISTS `moveup`.`recruit_tag` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `recruit_id`             INT UNSIGNED    NOT NULL,                             -- 店舗ID
  `tag_type`            TINYINT(2)      NOT NULL,                                -- タグタイプ
                                                                                 -- 1:エリア(1:岡山市、TODO:確認要)
                                                                                 -- 2:雇用形態
                                                                                 -- 3:こだわり
                                                                                 -- 4:特徽
                                                                                 -- 5:待遇
                                                                                 -- 6：就業時間が始まる(HHMM)
                                                                                 -- 7：就業時間が終わる(HHMM)
                                                                                 -- 8:業種
                                                                                 -- 9:勤務時間
                                                                                 -- 10:勤務期間
                                                                                 -- 11:経験・資格（歓迎情報）
                                                                                 -- 12:働き方
                                                                                 -- 13:給与の特徴
                                                                                 -- 14:職場環境
                                                                                 -- 15:待遇
  `content`             INT             NOT NULL,                                -- 内容
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `recruit_tag_idx1` (`recruit_id`),
  KEY `recruit_tag_idx2` (`tag_type`,`content`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='求人tag';

-- Table `moveup`.`recruit` // 求人リスト TODO:画面項目問題あり
CREATE TABLE IF NOT EXISTS `moveup`.`recruit` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `uuid`                VARCHAR(16)     NOT NULL,                                -- UUID
  `shop_id`             INT UNSIGNED    NOT NULL ,                               -- 店舗ID
  `recruit_name`        VARCHAR(255)   NOT NULL DEFAULT "",                      -- RecruitName
  `title1`              VARCHAR(255)    NOT NULL,                                -- タイトル1
  `pic_url1`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像1
  `salary`              VARCHAR(255)    NOT NULL DEFAULT "",                     -- 給与
  `address`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 勤務地
  `station`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 駅
  `working_time`        VARCHAR(255)    NOT NULL DEFAULT "",                     -- 通勤時間
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `recruit_idx1` (`uuid`),
  KEY `recruit_idx2` (`shop_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='求人';

-- Table `moveup`.`recruit_detail` // 求人詳細 TODO:画面項目問題あり
CREATE TABLE IF NOT EXISTS `moveup`.`recruit_detail` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `recruit_id`          INT UNSIGNED    NOT NULL,                                -- 求人ID
  `shop_id`             INT UNSIGNED    NOT NULL ,                               -- 店舗ID
  `recruit_name`        VARCHAR(255)   NOT NULL DEFAULT "",                      -- RecruitName
  `title1`              VARCHAR(255)    NOT NULL,                                -- タイトル1
  `title2`              VARCHAR(255)    NOT NULL,                                -- タイトル2
  `pic_url1`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像1
  `pic_url2`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像2
  `pic_url3`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像3
  `pic_url4`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像4
  `pic_url5`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像5
  `video_url1`          VARCHAR(255)    NOT NULL DEFAULT "",                     -- 動画1
  `content`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 内容
  `salary`              VARCHAR(255)    NOT NULL DEFAULT "",                     -- 給与
  `address`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 勤務地
  `station`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 駅
  `working_time`        VARCHAR(255)    NOT NULL DEFAULT "",                     -- 通勤時間
  `salary_full`         VARCHAR(255)    NOT NULL DEFAULT "",                     -- 雇用形態&給与
  `capacity`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 資格
  `working_date`        VARCHAR(255)    NOT NULL DEFAULT "",                     -- 勤務時間
  `vacation`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 休日&休暇
  `treatment`           VARCHAR(255)    NOT NULL DEFAULT "",                     -- 待遇
  `desc`                VARCHAR(2048)   NOT NULL DEFAULT "",                     -- 内容
  `tel`                 VARCHAR(64)     NOT NULL DEFAULT "",                     -- 電話
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `recruit_detail_idx1` (`recruit_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='求人詳細';

-- Table `moveup`.`event` // イベント
CREATE TABLE IF NOT EXISTS `moveup`.`event` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `uuid`                VARCHAR(16)     NOT NULL,                                -- UUID
  `sort_score`          INT             NOT NULL DEFAULT 0,                      -- TOPに表示
  `hold_date`           DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- 開催時間
  `pic_list_no`         TINYINT(1)      NOT NULL DEFAULT 0,                      -- リスト画面番号
  `pic_url`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `excerpt`             VARCHAR(255)    NOT NULL,                                -- 抜粋
  `guest_name`          VARCHAR(255)    NOT NULL,                                -- ゲスト名
  `hall_name`           VARCHAR(255)    NOT NULL,                                -- 会館名
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `event_idx1` (`uuid`),
  KEY `event_idx2` (`sort_score`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='イベント';

-- Table `moveup`.`event_detail` // イベント詳細
CREATE TABLE IF NOT EXISTS `moveup`.`event_detail` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `event_id`            INT UNSIGNED    NOT NULL ,                               -- イベントID
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `guest_name`          VARCHAR(255)    NOT NULL,                                -- ゲスト名
  `hall_name`           VARCHAR(255)    NOT NULL,                                -- 会館名
  `hold_date`           DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- 開催時間
  `pic_url1`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像1
  `pic_url2`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像2
  `pic_url3`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像3
  `pic_url4`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像4
  `pic_url5`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像5
  `pic_url6`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像6
  `pic_url7`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像7
  `pic_url8`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像8
  `pic_url9`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像9
  `pic_url10`           VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像10
  `video_url1`          VARCHAR(255)    NOT NULL DEFAULT "",                     -- 動画1
  `video_url2`          VARCHAR(255)    NOT NULL DEFAULT "",                     -- 動画2
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
  KEY `event_detail_idx1` (`event_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='イベント詳細';

-- Table `moveup`.`news` // ニュース
CREATE TABLE IF NOT EXISTS `moveup`.`news` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `uuid`                VARCHAR(16)     NOT NULL,                                -- UUID
  `date`                DATETIME        NOT NULL ,                               -- ニュース日付
  `type`                TINYINT(2)      NOT NULL DEFAULT 0,                      -- カテゴリ (0:不明, 1:EVENT, 2:MOVEUP, 3:NEWS，4ENENT-ENTRY, 5:studioNews)
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `sort_score`          INT             NOT NULL,                                -- TOPに表示(0:不明, 1:あり, 2:なし)
  `excerpt`             VARCHAR(255)    NOT NULL,                                -- 詳細抜粋
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `news_idx1` (`uuid`),
  KEY `news_idx2` (`sort_score`),
  KEY `news_idx3` (`create_datetime`),
  KEY `news_idx4` (`type`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ニュース';

-- Table `moveup`.`news_detail` // ニュース詳細
CREATE TABLE IF NOT EXISTS `moveup`.`news_detail` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `news_id`             INT UNSIGNED    NOT NULL ,                               -- ニュースID
  `date`                DATETIME        NOT NULL ,                               -- ニュース日付
  `type`                TINYINT(2)      NOT NULL DEFAULT 0,                      -- カテゴリ (0:不明, 1:EVENT, 2:MOVEUP, 3:NEWS, 4ENENT-ENTRY, 5:studioNews)
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `detail`              VARCHAR(2048)   NOT NULL,                                -- 詳細
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `news_detail_idx1` (`news_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ニュース詳細';


-- Table `moveup`.`rise` // ライズ詳細
CREATE TABLE IF NOT EXISTS `moveup`.`rise` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `name1`               VARCHAR(64)     NOT NULL,                                -- 名前1
  `name2`               VARCHAR(64)     NOT NULL,                                -- 名前2
  `office`              VARCHAR(255)    NOT NULL,                                -- 事務所
  `sort_score`          INT             NOT NULL,                                -- TOPに表示
  `pic_url`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- アバター
  `category`            TINYINT(2)      NOT NULL,                                -- riseカテゴリ(1.ATHLETE, 2.STREET, 3.ARTIST, 4.NEXTER, 5.GIRLS)
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `rise_idx1` (`sort_score`),
  KEY `rise_idx2` (`category`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ライズ';

-- Table `moveup`.`rise_detail` // ライズ詳細
CREATE TABLE IF NOT EXISTS `moveup`.`rise_detail` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `rise_id`             INT UNSIGNED    NOT NULL,                                -- riseListId
  `name1`               VARCHAR(64)     NOT NULL,                                -- 名前1
  `name2`               VARCHAR(64)     NOT NULL,                                -- 名前2
  `honor`               VARCHAR(255)    NOT NULL,                                -- 肩書
  `office`              VARCHAR(255)    NOT NULL,                                -- 事務所
  `category`            TINYINT(2)      NOT NULL,                                -- riseカテゴリ
  `top_title`           VARCHAR(255)    NOT NULL,                                -- topタイトル
  `title1`              VARCHAR(255)    NOT NULL,                                -- タイトル1
  `title2`              VARCHAR(255)    NOT NULL,                                -- タイトル2
  `title3`              VARCHAR(255)    NOT NULL,                                -- タイトル3
  `birthday`            DATE            NOT NULL,                                -- 誕生日
  `hometown`            VARCHAR(255)    NOT NULL,                                -- 出身地
  `top_pic_url`         VARCHAR(255)    NOT NULL DEFAULT "",                     -- top画像
  `pic_url1`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像1
  `pic_url2`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像2
  `pic_url3`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像3
  `pic_url4`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- アバター画像
  `video_url1`          VARCHAR(255)             DEFAULT "",                     -- 動画
  `desc1`               VARCHAR(2048)   NOT NULL DEFAULT "",                     -- 内容1
  `desc2`               VARCHAR(2048)   NOT NULL DEFAULT "",                     -- 内容2
  `desc3`               VARCHAR(2048)   NOT NULL DEFAULT "",                     -- 内容3
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `rise_detail_idx1` (`rise_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ライズ詳細';

-- Table `moveup`.`comment` // 情報
CREATE TABLE IF NOT EXISTS `moveup`.`comment` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `item_id`             INT UNSIGNED    NOT NULL,                                -- itemId
  `comment_type`        TINYINT(2)      NOT NULL,                                -- 情報のタイプ (1:shop, 2:rise)
  `sort_score`          INT             NOT NULL,                                -- TOPに表示
  `desc`                VARCHAR(2048)   NOT NULL DEFAULT "",                     -- 内容
  `pic_url1`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像1
  `pic_url2`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像2
  `publish_start`       DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載開始期間
  `publish_end`         DATETIME                 DEFAULT '1970-01-01 00:00:01',  -- 掲載終了期間
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `comment_idx1` (`item_id`),
  KEY `comment_idx2` (`sort_score`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='情報';

-- Table `moveup`.`notification` // 通知
CREATE TABLE IF NOT EXISTS `moveup`.`notification` (
  `id`                INT UNSIGNED   NOT NULL AUTO_INCREMENT,                 -- プッシュ通知ID
  `user_id`           INT UNSIGNED   NOT NULL,                                -- ユーザID
  `user_type`         INT UNSIGNED   NOT NULL,                                -- ユーザ種類 (1,管理者, 2,ユーザー)
  `notifi_content_id` INT UNSIGNED   NOT NULL,                                -- 通知内容ID
  `notify_type`       TINYINT(2)     NOT NULL,                                -- プッシュ通知種類 (1:お知らせ, 2,プッシュ通知)
  `notified_device`   TINYINT(2)     NOT NULL,                                -- 通知デバイス (1:Android, 2:iOS, 3,All)
  `notify_status`     TINYINT(2)     NOT NULL,                                -- ステータス(1:未開封, 2,開封ずみ)
  `push_status`       TINYINT(2)     NOT NULL,                                -- ステータス(1:,プッシュ未送信, 2,プッシュ送信ずみ)
  `publish_start`     DATETIME                 DEFAULT '1970-01-01 00:00:01', -- 掲載開始期間
  `publish_end`       DATETIME                 DEFAULT '1970-01-01 00:00:01', -- 掲載終了期間
  `create_datetime`   DATETIME       NOT NULL,                                -- レコード生成日時
  `update_datetime`   DATETIME       NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`           TINYINT(1)     NOT NULL,                                -- 論理削除フラグ
  `note`              varchar(255)   NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='プッシュ通知';

-- Table `moveup`.`notification_content` // 通知内容
CREATE TABLE IF NOT EXISTS `moveup`.`notification_content` (
  `id`               INT UNSIGNED   NOT NULL AUTO_INCREMENT,                 -- プッシュ通知ID
  `title`            VARCHAR(255)   DEFAULT NULL,                            -- タイトル
  `content`          VARCHAR(255)   DEFAULT NULL,                            -- 内容
  `create_datetime`  DATETIME       NOT NULL,                                -- レコード生成日時
  `update_datetime`  DATETIME       NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`          TINYINT(1)     NOT NULL,                                -- 論理削除フラグ
  `note`             varchar(255)   NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='プッシュ通知';

-- Table `moveup`.`notification_device` // 通知デバイス
CREATE TABLE IF NOT EXISTS `moveup`.`notification_device` (
  `id`              INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- 通知デバイスID
  `user_id`         INT UNSIGNED    NOT NULL,                                -- ユーザID
  `user_type`       INT UNSIGNED    NOT NULL,                                -- ユーザ種類 (1,管理者, 2,ユーザー)
  `device_id`       VARCHAR(32)     NOT NULL,                                -- デバイスID
  `os`              TINYINT(2)      NOT NULL,                                -- デバイスOS（1:Android, 2:iOS, 3:その他）
  `token`           VARCHAR(255)    NOT NULL,                                -- デバイストークン
  `create_datetime` DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime` DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`         TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`            varchar(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `notification_device_idx1` (`user_id`, `os`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='通知デバイス';

-- Table `moveup`.`goods` // 商品list TODO:仕様未定部分ある
CREATE TABLE IF NOT EXISTS `moveup`.`goods` (
  `id`               INT UNSIGNED   NOT NULL AUTO_INCREMENT,                -- 商品ListId
  `uuid`             VARCHAR(16)    NOT NULL,                                -- UUID
  `title`            VARCHAR(255)   NOT NULL,                               -- タイトル
  `brand`            VARCHAR(255)   NOT NULL,                               -- ブランド
  `pic_url`          VARCHAR(255)   NOT NULL DEFAULT "",                    -- 画像
  `price`            INT            NOT NULL DEFAULT '0',                   -- 値段
  `goods_type`       VARCHAR(255)   NOT NULL DEFAULT '0',                   -- 商品種類
  `sort_score`       INT            NOT NULL,                               -- TOPに表示
  `publish_start`    DATETIME                DEFAULT '1970-01-01 00:00:01', -- 掲載開始期間
  `publish_end`      DATETIME                DEFAULT '1970-01-01 00:00:01', -- 掲載終了期間
  `create_datetime`  DATETIME       NOT NULL,                               -- レコード生成日時
  `update_datetime`  DATETIME       NOT NULL DEFAULT '1970-01-01 00:00:01', -- レコード最後更新日時
  `del_flg`          TINYINT(1)     NOT NULL,                               -- 論理削除フラグ
  `note`             varchar(255)   NOT NULL,                               -- ノート
  PRIMARY KEY  (`id`),
  KEY `goods_idx1` (`uuid`),
  KEY `goods_idx2` (`sort_score`),
  KEY `goods_idx3` (`title`,`goods_type`,`price`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品list';

-- Table `moveup`.`goods_detail` // 商品詳細 TODO:仕様未定部分ある
CREATE TABLE IF NOT EXISTS `moveup`.`goods_detail` (
  `id`               INT UNSIGNED   NOT NULL AUTO_INCREMENT,                -- 商品ID
  `goods_id`         INT UNSIGNED   NOT NULL,                               -- goodsListID
  `name`             VARCHAR(255)   NOT NULL,                               -- 名前
  `brand`            VARCHAR(255)   NOT NULL,                               -- ブランド
  `title`            VARCHAR(255)   NOT NULL,                               -- タイトル
  `pic_url1`         VARCHAR(255)   NOT NULL DEFAULT "",                    -- 画像1
  `pic_url2`         VARCHAR(255)   NOT NULL DEFAULT "",                    -- 画像2
  `pic_url3`         VARCHAR(255)   NOT NULL DEFAULT "",                    -- 画像3
  `pic_url4`         VARCHAR(255)   NOT NULL DEFAULT "",                    -- 画像4
  `pic_url5`         VARCHAR(255)   NOT NULL DEFAULT "",                    -- 画像5
  `pic_url6`         VARCHAR(255)   NOT NULL DEFAULT "",                    -- 画像6
  `pic_url7`         VARCHAR(255)   NOT NULL DEFAULT "",                    -- 画像7
  `pic_url8`         VARCHAR(255)   NOT NULL DEFAULT "",                    -- 画像8
  `pic_url9`         VARCHAR(255)   NOT NULL DEFAULT "",                    -- 画像9
  `pic_url10`        VARCHAR(255)   NOT NULL DEFAULT "",                    -- 画像10
  `desc1`            VARCHAR(2048)  NOT NULL DEFAULT "",                    -- 内容1
  `desc2`            VARCHAR(2048)  NOT NULL DEFAULT "",                    -- 内容2
  `desc3`            VARCHAR(2048)  NOT NULL DEFAULT "",                    -- 内容3
  `price`            INT            NOT NULL DEFAULT '0',                   -- 値段
  `publish_start`    DATETIME                DEFAULT '1970-01-01 00:00:01', -- 掲載開始期間
  `publish_end`      DATETIME                DEFAULT '1970-01-01 00:00:01', -- 掲載終了期間
  `create_datetime`  DATETIME       NOT NULL,                               -- レコード生成日時
  `update_datetime`  DATETIME       NOT NULL DEFAULT '1970-01-01 00:00:01', -- レコード最後更新日時
  `del_flg`          TINYINT(1)     NOT NULL,                               -- 論理削除フラグ
  `note`             varchar(255)   NOT NULL,                               -- ノート
  PRIMARY KEY  (`id`),
  KEY `goods_detail_idx1` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品詳細';

-- Table `moveup`.`stock` // stock詳細 TODO:仕様未定部分ある
CREATE TABLE IF NOT EXISTS `moveup`.`stock` (
  `id`               INT UNSIGNED   NOT NULL AUTO_INCREMENT,                -- stockID
  `goods_id`         INT            NOT NULL,                               -- goodsID
  `size`             TINYINT(2)     NOT NULL DEFAULT '0',                   -- サイズ（0:不明, 1:X, 2:L, 3:M, 4:S, 5:XS）
  `colors`           TINYINT(2)     NOT NULL DEFAULT '0',                   -- 商品の色(0:カラー, 1:赤い, 2:オレンジ, 3:イエロー, 4:緑, 5:靑い, 6:パープル
                                                                            -- 7:黒い, 8:白い, 9:コーヒー色, 10:ピンク)
  `amount`           INT            NOT NULL DEFAULT '0',                   -- リアルタイム在庫
  `stock`            INT            NOT NULL DEFAULT '0',                   -- 在庫
  `stock_type`       TINYINT(2)     NOT NULL,                               -- stock(1:追加, 2:減少)
  `create_datetime`  DATETIME       NOT NULL,                               -- レコード生成日時
  `update_datetime`  DATETIME       NOT NULL DEFAULT '1970-01-01 00:00:01', -- レコード最後更新日時
  `del_flg`          TINYINT(1)     NOT NULL,                               -- 論理削除フラグ
  `note`             varchar(255)   NOT NULL,                               -- ノート
  PRIMARY KEY  (`id`),
  KEY `stock_idx1` (`goods_id`,`size`,`colors`),
  KEY `stock_idx2` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='在庫詳細';

-- Table `moveup`.`goods_purchase` // 購入 TODO:仕様未定部分ある
CREATE TABLE IF NOT EXISTS `moveup`.`goods_purchase` (
  `id`               INT UNSIGNED   NOT NULL AUTO_INCREMENT,                 -- 購入ID
  `user_id`          INT UNSIGNED   NOT NULL,                                -- userID
  `goods_id`         INT UNSIGNED   NOT NULL,                                -- goodsID
  `price`            INT            NOT NULL DEFAULT '0',                    -- 値段
  `coin`             INT            NOT NULL DEFAULT '0',                    -- コイン
  `quantity`         INT            NOT NULL DEFAULT '0',                    -- 数量
  `size`             TINYINT(2)     NOT NULL DEFAULT '0',                    -- サイズ(0:不明, 1:XS, 2:S, 3:M, 4:L, 5:XL)
  `colors`           TINYINT(2)     NOT NULL DEFAULT '0',                    -- 商品の色(0:カラー, 1:赤い, 2:オレンジ, 3:イエロー, 4:緑, 5:靑い, 6:パープル
                                                                             -- 7:黒い, 8:白い, 9:コーヒー色, 10:ピンク)
  `status`           INT            NOT NULL DEFAULT '0',                    -- 購入状態 11:cart, 12:支払いは成功していない
                                                                             --         21:支払いが成功する-出荷していない, 22：出荷済み, 23:確認済み
                                                                             --         31:返品を申請する, 32:商家確認済み, 33:返金済み, 34:品物が届いていない
  `serial_number`    VARCHAR(64)    NOT NULL,                                -- 番号
  `create_datetime`  DATETIME       NOT NULL,                                -- レコード生成日時
  `update_datetime`  DATETIME       NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`          TINYINT(1)     NOT NULL,                                -- 論理削除フラグ
  `note`             varchar(255)   NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `goods_purchase_idx1` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='購入';

-- Table `moveup`.`purchase_info` // 購入お客様情報
CREATE TABLE IF NOT EXISTS `moveup`.`purchase_info` (
  `id`               INT UNSIGNED   NOT NULL AUTO_INCREMENT,                 -- 情報ID
  `user_id`          INT UNSIGNED   NOT NULL,                                -- UserID
  `first_name1`      VARCHAR(64)    NOT NULL,                                -- 姓(漢字)
  `last_name1`       VARCHAR(64)    NOT NULL,                                -- 名(漢字)
  `first_name2`      VARCHAR(64)    NOT NULL,                                -- 姓(カナ)
  `last_name2`       VARCHAR(64)    NOT NULL,                                -- 名(カナ)
  `zip_code`         VARCHAR(64)    NOT NULL,                                -- 郵便番号
  `address1`         VARCHAR(255)   NOT NULL,                                -- アドレス
  `address2`         VARCHAR(255)   NOT NULL,                                -- 予備アドレス
  `email`            VARCHAR(64)    NOT NULL,                                -- 電子メール
  `tel`              VARCHAR(64)    NOT NULL,                                -- 電話番号
  `create_datetime`  DATETIME       NOT NULL,                                -- レコード生成日時
  `update_datetime`  DATETIME       NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`          TINYINT(1)     NOT NULL,                                -- 論理削除フラグ
  `note`             varchar(255)   NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `purchase_info_idx1` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='購入お客様情報';

-- Table `moveup`.`coin_history` // コインヒストリ TODO:仕様未定部分ある
CREATE TABLE IF NOT EXISTS `moveup`.`coin_history` (
  `id`              INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `coin_id`         INT UNSIGNED    NOT NULL,                                -- コインID
  `item_id`         INT UNSIGNED    NOT NULL,                                -- ItemID
  `user_id`         INT UNSIGNED    NOT NULL,                                -- ユーザーID
  `amount`          BIGINT          NOT NULL DEFAULT '0',                    -- リアルタイムcoin
  `coin_changed`    BIGINT          NOT NULL,                                -- コイン変化数
  `coin_type`       TINYINT(2)      NOT NULL,                                -- コイン(1:追加, 2:減少)
  `serial_number`   VARCHAR(64)     NOT NULL,                                -- 番号
  `create_datetime` DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime` DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`         TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`            varchar(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `coin_history_idx1` (`coin_id`,`item_id`),
  KEY `coin_history_idx2` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='コインヒストリ';

-- Table `moveup`.`coin_master` // coin_master
CREATE TABLE IF NOT EXISTS `moveup`.`coin_master` (
  `id`              INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `coin_id`         INT UNSIGNED    NOT NULL,                                -- コインID
  `coin_type`       TINYINT(2)      NOT NULL,                                -- タグタイプ
  `desc`            VARCHAR(255)    NOT NULL,                                -- 内容
  `create_datetime` DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime` DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`         TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`            VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='coin_master';

-- Table `moveup`.`invite` // 招待
CREATE TABLE IF NOT EXISTS `moveup`.`invite` (
  `id`                INT UNSIGNED  NOT NULL AUTO_INCREMENT,                   -- キャンペーン番号
  `invite_type`       INT NOT NULL,                                            -- 招待種別 (TODO:未定)
  `name_from`         VARCHAR(32)   DEFAULT '',                                -- 招待名前
  `friend_mail`       VARCHAR(255)  NOT NULL DEFAULT '',                       -- メールアドレス
  `message`           VARCHAR(400)  DEFAULT '',                                -- 招待メッセージ
  `token_id`          VARCHAR(8)    NOT NULL,                                  -- トークン番号
  `campaign_id`       INT UNSIGNED  NOT NULL,                                  -- キャンペーン番号（空、必要の場合キャンペンマスタテーブル追加対応可能）
  `user_id_from`      INT UNSIGNED  NOT NULL,                                  -- 送信元
  `user_id_to`        INT UNSIGNED  NOT NULL,                                  -- 送信先
  `read_flg_to`       INT(1)        NOT NULL,                                  -- 承認状態 (0:未承可否認、1:拒否または承認) 承認の可否についてはinvite_history参照
  `expire_date`       DATETIME      NOT NULL DEFAULT '1970-01-01 00:00:01',    -- トークン有効期限日時(NULLの場合は、明示的な指定無し)
  `invite_date`       DATE          NOT NULL,                                  -- 日付
  `invite_action`     INT           NOT NULL,                                  -- 入会アクション (0=クリック, 1=登録)
  `incentive_status`  INT           NOT NULL,                                  -- インセンティブ状態 (0=未送信 1=送信失敗 2=送信済) => パーティショニングをあらかじめ入れよう
  `reuse_count`       INT           NOT NULL DEFAULT 0,                        -- inviteを更新した回数
  `create_datetime`   DATETIME      NOT NULL,                                  -- 作成日時
  `update_datetime`   DATETIME      NOT NULL DEFAULT '1970-01-01 00:00:01',    -- レコード最後更新日時
  `del_flg`           TINYINT(1)    NOT NULL,                                  -- 削除フラグ(0:通常、1:削除、2:管理者削除)
  `note`              VARCHAR(255)  NOT NULL,                                  -- ノート
    PRIMARY KEY (id),
    UNIQUE KEY `invite_unique_token` (`token_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='招待';

-- Table `moveup`.`user_setting` // ユーザー設定テーブル
CREATE TABLE IF NOT EXISTS `moveup`.`user_setting` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `user_id`             INT UNSIGNED    NOT NULL,                                -- ユーザーID
  `setting_key`         VARCHAR(255)    NOT NULL,                                -- 属性キー
  `setting_value`       VARCHAR(255)    NOT NULL,                                -- 属性値
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `user_setting_idx1` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ユーザー設定';


-- Table `moveup`.`demo` // デモテーブル
CREATE TABLE IF NOT EXISTS `moveup`.`demo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table `moveup`.`top` // topページ画像と動画
CREATE TABLE IF NOT EXISTS `moveup`.`top` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `url`                 VARCHAR(255)    NOT NULL,                                -- 画像や動画url
  `type`                TINYINT(2)      NOT NULL,                                -- タイプ(1:画像, 2:動画)
  `score`               INT             NOT NULL,                                -- 位置を表示する
  `link_url`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- LinkUrl
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `score` (`score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='topページ画像と動画';

-- Table `moveup`.`place` // プレスリスト
CREATE TABLE IF NOT EXISTS `moveup`.`place` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `uuid`                VARCHAR(16)     NOT NULL,                                -- UUID
  `name`                VARCHAR(64)     NOT NULL,                                -- 名所
  `introduce`           VARCHAR(2048)   NOT NULL,                                -- 抜粋
  `address`             VARCHAR(255)    NOT NULL,                                -- アドレス
  `area`                INT             NOT NULL,                                -- エリア(1:岡山市、TODO:確認要)
  `pic_url`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像
  `flic_url`            VARCHAR(255)    NOT NULL,                                -- flic360
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `place_idx1` (`uuid`),
  KEY `place_idx2` (`area`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='プレスリスト';

-- Table `moveup`.`place_detail` // プレス詳細
CREATE TABLE IF NOT EXISTS `moveup`.`place_detail` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `place_list_id`       INT UNSIGNED    NOT NULL,                                -- プレスID
  `name`                VARCHAR(255)    NOT NULL,                                -- 名所
  `location`            VARCHAR(255)    NOT NULL,                                -- 位置
  `station`             VARCHAR(255)    NOT NULL,                                -- 最寄駅
  `walk`                VARCHAR(255)    NOT NULL,                                -- 最寄駅徒歩時間
  `time`                VARCHAR(255)    NOT NULL,                                -- 観覧時間
  `holiday`             VARCHAR(255)    NOT NULL,                                -- 定休日
  `pic_url`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- メイン画像
  `pic_url1`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- サブ画像1
  `pic_url2`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- サブ画像2
  `pic_url3`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- サブ画像3
  `pic_url4`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- サブ画像4
  `flic_url`            VARCHAR(255)    NOT NULL DEFAULT "",                     -- flic360
  `video_url`           VARCHAR(255)    NOT NULL DEFAULT "",                     -- 動画
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `desc`                VARCHAR(1024)   NOT NULL DEFAULT "",                     -- 内容
  `tel`                 VARCHAR(64)     NOT NULL DEFAULT "",                     -- 電話
  `address`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 住所
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `place_detail_idx1` (`place_list_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='プレス詳細';

-- Table `moveup`.`inquire` // 問い合わせ
CREATE TABLE IF NOT EXISTS `moveup`.`inquire` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `user_id`             INT UNSIGNED,                                            -- userId
  `type`                TINYINT(2)      NOT NULL,                                -- 問い合わせタイプ（1:一般ユーザー, 2:協力店）
  `mail`                VARCHAR(255),                                            -- メルアド
  `first_name`          VARCHAR(255),                                            -- 名前、姓
  `second_name`         VARCHAR(255),                                            -- 名前、苗字
  `contents`            VARCHAR(2048)   NOT NULL,                                -- 問い合わせ内容
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='問い合わせ';

-- Table `moveup`.`payment_history` // payment_history
CREATE TABLE IF NOT EXISTS `moveup`.`payment_history` (
  `id`              INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `goods_id`        INT UNSIGNED    NOT NULL,                                -- goodsID
  `user_id`         INT UNSIGNED    NOT NULL,                                -- ユーザーID
  `amount`          INT             NOT NULL DEFAULT '0',                    -- 取引金額
  `forward`         VARCHAR(8)      NOT NULL,                                -- 仕向先コード
  `method`          VARCHAR(2)      NOT NULL,                                -- 支払方法
  `approve`         VARCHAR(8)      NOT NULL,                                -- 承認番号
  `tran_id`         VARCHAR(28)     NOT NULL,                                -- トランザクション ID
  `tran_date`       VARCHAR(14)     NOT NULL,                                -- 決済日付
  `check_string`    VARCHAR(32)     NOT NULL,                                -- MD5 ハッシュ
  `order_id`        VARCHAR(64)     NOT NULL,                                -- 取引番号
  `create_datetime` DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime` DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`         TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`            varchar(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='payment_history';

-- Table `moveup`.`pic` // 画像
CREATE TABLE IF NOT EXISTS `moveup`.`pic` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `item_id`             INT             NOT NULL,                                -- itemID
  `item_type`           TINYINT(2)      NOT NULL,                                -- itemType(1:eventStar, 2:eventComment, 3:team2020, 4:studioNews, 5:studioDetail，6-9)
  `pic_url`             VARCHAR(255)    NOT NULL DEFAULT "",                     -- 画像url
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `pic_idx1` (`item_id`,`item_type`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='画像';

-- Table `moveup`.`studio_gallery` // studioページ画像と動画
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

-- Table `moveup`.`team2020` // team2020詳細
CREATE TABLE IF NOT EXISTS `moveup`.`team2020` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `uuid`                VARCHAR(16)     NOT NULL,                                -- UUID
  `name`                VARCHAR(255)    NOT NULL,                                -- 名前
  `pseudonym`           VARCHAR(255)    NOT NULL,                                --ふりかな
  `category`            TINYINT(2)      NOT NULL DEFAULT 0,                      -- カテゴリ (0:不明)
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `team2020_idx1` (`uuid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='team2020詳細'

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

-- Table `moveup`.`batch_status` // batch_status
CREATE TABLE IF NOT EXISTS `moveup`.`batch_status` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `batch_type`          TINYINT(2)      NOT NULL,                                -- 1:coupon, 2:payment, 3:pic, 4:freepaper, 5:tv
  `status`              TINYINT(2)      NOT NULL,                                -- 0:notBeginning, 1:working, 2:finish
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='batch_status';

-- Table `moveup`.`settlement` // settlement
CREATE TABLE IF NOT EXISTS `moveup`.`settlement` (
  `id`                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `customer_id`         INT UNSIGNED    NOT NULL,                                -- customer_id
  `item_type`           TINYINT(2)      NOT NULL,                                -- 1:shop, 2:goods, 3:top, 4:freepaper, 5:event, 6:news
  `item_id`             INT UNSIGNED    NOT NULL,                                -- itemID
  `type`                TINYINT(2)      NOT NULL,                                -- 1:coupon, 2:score，3:advertisement, 4:monthly_expenses, 5:subshop
  `amount`              INT             NOT NULL,                                -- 決済金額
  `yyyymm`              INT             NOT NULL,                                -- bill yyyyMM
  `status`              TINYINT(1)      NOT NULL DEFAULT 0,                      -- 0:Unsettled, 1:already settled
  `method`              TINYINT(2)      NOT NULL,                                -- 1:Credit Card, 2:Bank account buckle, 3:Bank remittance
  `title`               VARCHAR(255)    NOT NULL,                                -- タイトル
  `content`             VARCHAR(1024)   NOT NULL,                                -- 内容
  `create_datetime`     DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='settlement';

-- Table `moveup`.`settlement_master` // settlement_master
CREATE TABLE IF NOT EXISTS `moveup`.`settlement_master` (
  `id`              INT UNSIGNED    NOT NULL AUTO_INCREMENT,                 -- ID
  `settlement_type` TINYINT(2)      NOT NULL,                                -- 1:coupon, 2:score，3:advertisement, 4:monthly_expenses, 5:subshop
  `content`         VARCHAR(255)    NOT NULL,                                -- 内容
  `create_datetime` DATETIME        NOT NULL,                                -- レコード生成日時
  `update_datetime` DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`         TINYINT(1)      NOT NULL,                                -- 論理削除フラグ
  `note`            VARCHAR(255)    NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `settlement_master_idx1` (`settlement_type`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='settlement_master';

-- Table `moveup`.`random_shop_one` // random shop list one
CREATE TABLE IF NOT EXISTS `moveup`.`random_shop_one` (
  `id`                  INT UNSIGNED     NOT NULL AUTO_INCREMENT,                 -- ID
  `shop_type`           TINYINT(2)       NOT NULL,                                -- 1:food, 12:fastion, 3:health,... 7:place,8:info, 2:recruit
  `uuid`                VARCHAR(255)     NOT NULL,                                --
  `area`                TINYINT(2)       NOT NULL,                                --
  `now4`                TINYINT(1)       NOT NULL,                                --
  `now5`                TINYINT(1)       NOT NULL,                                --
  `create_datetime`     DATETIME         NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME         NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)       NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)     NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `shop_type_idx1` (`shop_type`),
  KEY `uuid_idx2` (`uuid`),
  KEY `area_idx3` (`area`),
  KEY `area_idx4` (`now4`),
  KEY `area_idx5` (`now5`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='random_shop_one';

-- Table `moveup`.`random_shop_two` // random shop list two
CREATE TABLE IF NOT EXISTS `moveup`.`random_shop_two` (
  `id`                  INT UNSIGNED     NOT NULL AUTO_INCREMENT,                 -- ID
  `shop_type`           TINYINT(2)       NOT NULL,                                -- 1:food, 12:fastion, 3:health,... 7:place,8:info, 2:recruit
  `uuid`                VARCHAR(255)     NOT NULL,                                --
  `area`                TINYINT(2)       NOT NULL,                                --
  `now4`                TINYINT(1)       NOT NULL,                                --
  `now5`                TINYINT(1)       NOT NULL,                                --
  `create_datetime`     DATETIME         NOT NULL,                                -- レコード生成日時
  `update_datetime`     DATETIME         NOT NULL DEFAULT '1970-01-01 00:00:01',  -- レコード最後更新日時
  `del_flg`             TINYINT(1)       NOT NULL,                                -- 論理削除フラグ
  `note`                VARCHAR(255)     NOT NULL,                                -- ノート
  PRIMARY KEY  (`id`),
  KEY `shop_type_idx1` (`shop_type`),
  KEY `uuid_idx2` (`uuid`),
  KEY `area_idx3` (`area`),
  KEY `area_idx4` (`now4`),
  KEY `area_idx5` (`now5`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='random_shop_two';

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