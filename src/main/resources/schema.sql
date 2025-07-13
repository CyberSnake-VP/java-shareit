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
    CONSTRAINT fk_items_to_users FOREIGN KEY(owner_id) REFERENCES users(id)
);

