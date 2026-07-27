-- 1回だけ実行する。postgres など管理者ユーザーで接続した状態で実行すること。
-- DB 本体
CREATE DATABASE todo_app;

-- アプリ専用ロール（スーパーユーザー権限は付けない）
-- パスワードはこのファイルに書かず、実行時に自分で決めた値へ置き換える
CREATE ROLE todo_app LOGIN PASSWORD '<ここにパスワードを設定する>';

-- 接続権限のみ先に付与
GRANT CONNECT ON DATABASE todo_app TO todo_app;