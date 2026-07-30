-- スキーマの利用権限
GRANT USAGE ON SCHEMA public TO todo_app;

-- アプリが必要とする DML のみ許可（DDL は許可しない）
GRANT
SELECT,
INSERT,
UPDATE,
DELETE ON tasks,users TO todo_app;

-- BIGSERIAL のシーケンス利用権限
GRANT USAGE,
SELECT
    ON all sequences IN SCHEMA public TO todo_app;

-- 誤ってテーブルを作られないよう、PUBLIC スキーマへの CREATE を明示的に剥奪
REVOKE CREATE ON SCHEMA public
FROM
    PUBLIC;