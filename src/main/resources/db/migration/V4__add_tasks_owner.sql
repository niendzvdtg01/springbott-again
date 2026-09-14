ALTER TABLE tasks
    ADD COLUMN owner_id BIGINT NULL;

UPDATE tasks
SET owner_id = (
    SELECT MIN(id)
    FROM users
)
WHERE owner_id IS NULL;

ALTER TABLE tasks
    MODIFY owner_id BIGINT NOT NULL;

ALTER TABLE tasks
    ADD CONSTRAINT fk_tasks_owner
        FOREIGN KEY (owner_id)
        REFERENCES users(id);

CREATE INDEX idx_tasks_owner_id
    ON tasks(owner_id);