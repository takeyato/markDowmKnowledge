🧩 全体構成図（主要テーブル）

articles        ← 記事本体（Markdown）
├── article_tags     ← 記事とタグの中間テーブル（多対多）
├── article_versions ← バージョン履歴
├── comments         ← コメント
├── categories       ← カテゴリ
├── tags             ← タグ
└── users            ← 著者・編集者

📘 1. articles（記事）

CREATE TABLE articles (
  id NUMBER PRIMARY KEY,
  title VARCHAR2(255),
  markdown CLOB,
  category_id NUMBER,
  author_id NUMBER,
  updated_at TIMESTAMP,
  published BOOLEAN,
  view_count NUMBER DEFAULT 0
);

markdown: Markdown本文（CLOB）

published: 公開/下書きフラグ

view_count: 人気記事ランキング用

🏷 2. tags（タグ）

CREATE TABLE tags (
  id NUMBER PRIMARY KEY,
  name VARCHAR2(100) UNIQUE
);

🔗 3. article_tags（記事とタグの中間）

CREATE TABLE article_tags (
  article_id NUMBER,
  tag_id NUMBER,
  PRIMARY KEY (article_id, tag_id)
);

多対多の関係を管理

📂 4. categories（カテゴリ）

CREATE TABLE categories (
  id NUMBER PRIMARY KEY,
  name VARCHAR2(100) UNIQUE
);

articles.category_id と外部キーで接続

👤 5. users（著者・編集者）

CREATE TABLE users (
  id NUMBER PRIMARY KEY,
  username VARCHAR2(100),
  email VARCHAR2(255),
  role VARCHAR2(50) -- admin, editor, viewer など
);

🕒 6. article_versions（バージョン履歴）

CREATE TABLE article_versions (
  id NUMBER PRIMARY KEY,
  article_id NUMBER,
  version_number NUMBER,
  markdown CLOB,
  updated_at TIMESTAMP,
  updated_by NUMBER
);

差分表示はアプリ側で比較

updated_by は users.id を参照

💬 7. comments（コメント）

CREATE TABLE comments (
  id NUMBER PRIMARY KEY,
  article_id NUMBER,
  user_id NUMBER,
  content VARCHAR2(1000),
  created_at TIMESTAMP
);
