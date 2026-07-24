-- todo_app に接続した状態で実行すること
-- テーブル定義（アプリのスキーマ本体）

CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    name VARCHAR(100),
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP DEFAULT date_trunc('second', now()),
    updated_at TIMESTAMP DEFAULT date_trunc('second', now())
);

CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = date_trunc('second', now());
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_set_updated_at
BEFORE UPDATE ON tasks
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TABLE login (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT date_trunc('second', now()),
    updated_at TIMESTAMP DEFAULT date_trunc('second', now())
);
