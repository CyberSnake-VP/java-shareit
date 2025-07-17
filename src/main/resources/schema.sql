CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name varchar(255) NOT NULL,
    email varchar(320) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT unique_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS items (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name varchar(255) NOT NULL,
    description varchar(1000) NOT NULL,
    available boolean NOT NULL,
    owner_id BIGINT,
    CONSTRAINT pk_item PRIMARY KEY (id),
    CONSTRAINT fk_items_to_users FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    start_date timestamp without time zone,
    end_date timestamp without time zone,
    item_id BIGINT,
    booker_id BIGINT,
    status varchar(100) NOT NULL,
    CONSTRAINT pk_booking PRIMARY KEY (id),
    CONSTRAINT fk_bookings_to_items FOREIGN KEY (item_id) REFERENCES items(id),
    CONSTRAINT fk_bookings_to_users FOREIGN KEY (booker_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS comments (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    text TEXT,
    item_id BIGINT,
    author_id BIGINT,
    created timestamp without time zone,
    CONSTRAINT pk_comments PRIMARY KEY (id),
    CONSTRAINT fk_comments_to_items FOREIGN KEY (item_id) REFERENCES items(id),
    CONSTRAINT fk_comments_to_users FOREIGN KEY (author_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS requests (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    description varchar(1000),
    requestor_id BIGINT,
    CONSTRAINT pk_requests PRIMARY KEY (id)
)