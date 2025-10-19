-- ユーザー情報（著者・編集者・閲覧者など）
CREATE TABLE users (
  id NUMBER PRIMARY KEY,
  username VARCHAR2(100) NOT NULL,
  email VARCHAR2(255),
  role VARCHAR2(50) DEFAULT 'viewer'
);

COMMENT ON COLUMN users.id IS 'ユーザーID';
COMMENT ON COLUMN users.username IS '表示名';
COMMENT ON COLUMN users.email IS 'メールアドレス';
COMMENT ON COLUMN users.role IS '権限（admin/editor/viewer）';

-- カテゴリ情報（例：技術、業務マニュアルなど）
CREATE TABLE categories (
  id NUMBER PRIMARY KEY,
  name VARCHAR2(100) UNIQUE NOT NULL
);

COMMENT ON COLUMN categories.id IS 'カテゴリID';
COMMENT ON COLUMN categories.name IS 'カテゴリ名';

-- タグ情報（例：Linux、Markdown、検索）
CREATE TABLE tags (
  id NUMBER PRIMARY KEY,
  name VARCHAR2(100) UNIQUE NOT NULL
);

COMMENT ON COLUMN tags.id IS 'タグID';
COMMENT ON COLUMN tags.name IS 'タグ名';

-- 記事本体（Markdownで記述されたナレッジ）
CREATE TABLE articles (
  id NUMBER PRIMARY KEY,
  title VARCHAR2(255) NOT NULL,
  markdown CLOB NOT NULL,
  category_id NUMBER,
  author_id NUMBER,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  published NUMBER(1) DEFAULT 0,
  view_count NUMBER DEFAULT 0,
  CONSTRAINT fk_article_category FOREIGN KEY (category_id) REFERENCES categories(id),
  CONSTRAINT fk_article_author FOREIGN KEY (author_id) REFERENCES users(id)
);

COMMENT ON COLUMN articles.id IS '記事ID';
COMMENT ON COLUMN articles.title IS '記事タイトル';
COMMENT ON COLUMN articles.markdown IS 'Markdown形式の本文';
COMMENT ON COLUMN articles.category_id IS 'カテゴリID（外部キー）';
COMMENT ON COLUMN articles.author_id IS '著者ID（外部キー）';
COMMENT ON COLUMN articles.updated_at IS '最終更新日時';
COMMENT ON COLUMN articles.published IS '公開フラグ（0:下書き, 1:公開）';
COMMENT ON COLUMN articles.view_count IS '閲覧数（人気記事ランキング用）';

-- 記事とタグの中間テーブル（多対多）
CREATE TABLE article_tags (
  article_id NUMBER NOT NULL,
  tag_id NUMBER NOT NULL,
  PRIMARY KEY (article_id, tag_id),
  CONSTRAINT fk_tag_article FOREIGN KEY (article_id) REFERENCES articles(id),
  CONSTRAINT fk_tag_tag FOREIGN KEY (tag_id) REFERENCES tags(id)
);

COMMENT ON COLUMN article_tags.article_id IS '記事ID（外部キー）';
COMMENT ON COLUMN article_tags.tag_id IS 'タグID（外部キー）';

-- 記事のバージョン履歴（差分管理）
CREATE TABLE article_versions (
  id NUMBER PRIMARY KEY,
  article_id NUMBER NOT NULL,
  version_number NUMBER NOT NULL,
  markdown CLOB NOT NULL,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_by NUMBER,
  CONSTRAINT fk_version_article FOREIGN KEY (article_id) REFERENCES articles(id),
  CONSTRAINT fk_version_user FOREIGN KEY (updated_by) REFERENCES users(id)
);

COMMENT ON COLUMN article_versions.id IS 'バージョンID';
COMMENT ON COLUMN article_versions.article_id IS '元記事ID（外部キー）';
COMMENT ON COLUMN article_versions.version_number IS 'バージョン番号';
COMMENT ON COLUMN article_versions.markdown IS 'Markdown本文（履歴用）';
COMMENT ON COLUMN article_versions.updated_at IS '更新日時';
COMMENT ON COLUMN article_versions.updated_by IS '更新者ID（外部キー）';

-- コメント欄（任意機能）
CREATE TABLE comments (
  id NUMBER PRIMARY KEY,
  article_id NUMBER NOT NULL,
  user_id NUMBER NOT NULL,
  content VARCHAR2(1000) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_comment_article FOREIGN KEY (article_id) REFERENCES articles(id),
  CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES users(id)
);

COMMENT ON COLUMN comments.id IS 'コメントID';
COMMENT ON COLUMN comments.article_id IS '対象記事ID（外部キー）';
COMMENT ON COLUMN comments.user_id IS 'コメント投稿者ID（外部キー）';
COMMENT ON COLUMN comments.content IS 'コメント本文';
COMMENT ON COLUMN comments.created_at IS 'コメント作成日時';
