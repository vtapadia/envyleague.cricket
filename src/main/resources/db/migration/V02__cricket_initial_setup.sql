create table cricket_match (
  id int PRIMARY KEY ,
  tournament VARCHAR(100),
  match_type VARCHAR(25),
  start_time TIMESTAMP ,
  team_a VARCHAR(100),
  team_b VARCHAR(100),
  finalized boolean,
  winner VARCHAR(100),

  total_runs int,
  total_fours int,
  total_sixes int,
  total_wickets int
);

create TABLE cricket_prediction (

);