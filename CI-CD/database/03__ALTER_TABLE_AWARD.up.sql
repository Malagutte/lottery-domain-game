ALTER TABLE awards
    alter column prize TYPE NUMERIC USING prize::numeric;