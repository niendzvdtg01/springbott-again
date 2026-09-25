CREATE TABLE task_activity (
    id BIGINT NOT NULL AUTO_INCREMENT,
    event_id CHAR(36) NOT NULL,
    task_id BIGINT NOT NULL,
    owner_id BIGINT NOT NULL,
    previous_status VARCHAR(30) NOT NULL,
    current_status VARCHAR(30) NOT NULL,
    task_version BIGINT NOT NULL,
    occurred_at TIMESTAMP(6) NOT NULL,
    processed_at TIMESTAMP(6) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uk_task_activity_event_id
        UNIQUE (event_id),

    CONSTRAINT fk_task_activity_task
        FOREIGN KEY (task_id)
        REFERENCES tasks(id),

    CONSTRAINT fk_task_activity_owner
        FOREIGN KEY (owner_id)
        REFERENCES users(id)
);

CREATE INDEX idx_task_activity_task_id_id
    ON task_activity(task_id, id);