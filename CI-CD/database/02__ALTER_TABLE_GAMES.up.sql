ALTER TABLE games
    ADD COLUMN IF NOT EXISTS details jsonb not null;