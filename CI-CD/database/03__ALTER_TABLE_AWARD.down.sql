ALTER TABLE awards
alter
column prize TYPE NUMBER USING prize::NUMBER;