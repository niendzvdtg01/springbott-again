CREATE INDEX idx_tasks_owner_status_id
    ON tasks(owner_id, status, id);