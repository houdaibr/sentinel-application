CREATE TABLE IF NOT EXISTS events(
    id SERIAL PRIMARY KEY,
    service VARCHAR(255) NOT NULL,
    level VARCHAR(55) NOT NULL,
    message TEXT NOT NULL,
    trace_id VARCHAR(55) NOT NULL,
    occurred_at TIMESTAMPTZ NOT NULL,
    received_at TIMESTAMPTZ NOT NULL
);
CREATE INDEX IF NOT EXISTS index_events_service ON events(service, occurred_at DESC);
