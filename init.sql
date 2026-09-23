create table events(
    id SERIAL primary key ,
    service varchar(255)  not null ,
    level varchar(55) not null ,
    message text not null ,
    trace_id varchar(55) not null,
    occurred_at timestamptz not null,
    received_at timestamptz not null

);
create INDEX index_events_service on events(service, occurred_at desc );