CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS games (
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    number INTEGER NOT NULL,
    type TEXT NOT NULL,
    date DATE NOT NULL,
    raffle_numbers json not null,
    hits_to_win json not null
);

CREATE TABLE IF NOT EXISTS awards (
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    hit_numbers INTEGER NOT NULL,
    prize INTEGER NOT NULL,
    amount_people INTEGER NOT NULL,
    game_id uuid NOT NULL,
    CONSTRAINT fk_game_id FOREIGN KEY (game_id) REFERENCES games(id)
);

CREATE TABLE IF NOT EXISTS tasks (
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    init_date timestamp NOT NULL,
    end_date timestamp NOT NULL
);

