CREATE TABLE if not exists conference_talk (
    id SERIAL primary key,
    title varchar not null,
    description varchar not null,
    topic varchar not null
);