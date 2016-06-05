create table football_match (
  id int PRIMARY KEY ,
  tournament VARCHAR(100),
  match_type VARCHAR(25),
  start_time TIMESTAMP ,
  team_a VARCHAR(100),
  team_b VARCHAR(100),
  finalized boolean,
  winner VARCHAR(100),

  team_a_score int,
  team_b_score int,
  team_a_penalty int,
  team_b_penalty int
);