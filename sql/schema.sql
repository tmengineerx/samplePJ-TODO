-- todo_app に接続した状態で実行すること

DROP TABLE IF EXISTS tasks CASCADE;
DROP TABLE IF EXISTS login CASCADE;
DROP TABLE IF EXISTS users CASCADE;


-- テーブル定義（アプリのスキーマ本体）
-- ============================================================
-- users（旧 login）
-- ============================================================
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(60) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    CONSTRAINT users_username_not_blank CHECK (length(btrim(username)) > 0)
);

-- ============================================================
-- tasks
-- ============================================================
CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    assignee VARCHAR(100),
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    CONSTRAINT tasks_username_fk FOREIGN KEY (username) REFERENCES users (username) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT tasks_title_not_blank CHECK (length(btrim(title)) > 0),
    CONSTRAINT tasks_period_order CHECK (
        start_date IS NULL
        OR end_date IS NULL
        OR start_date <= end_date
    )
);

CREATE INDEX tasks_username_idx ON tasks (username, id DESC);

-- ============================================================
-- updated_at 自動更新
-- ============================================================
CREATE
OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
    BEGIN NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_tasks_set_updated_at BEFORE
UPDATE
    ON tasks FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trigger_users_set_updated_at BEFORE
UPDATE
    ON users FOR EACH ROW EXECUTE FUNCTION set_updated_at();