CREATE TABLE outbox_events (
    id CHAR(36) NOT NULL,
    aggregate_type VARCHAR(50) NOT NULL,
    aggregate_id BIGINT NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    exchange_name VARCHAR(150) NOT NULL,
    routing_key VARCHAR(150) NOT NULL,
    payload JSON NOT NULL,

    status VARCHAR(20) NOT NULL,
    attempts INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP(6) NOT NULL,
    published_at TIMESTAMP(6) NULL,
    last_error VARCHAR(1000) NULL,

    PRIMARY KEY (id),

    INDEX idx_outbox_status_created (
        status,
        created_at
    )
);