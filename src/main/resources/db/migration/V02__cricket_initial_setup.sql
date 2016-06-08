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

create TABLE cricket_tournament_team (
  tournament VARCHAR(100),
  team VARCHAR(100),
  "group" VARCHAR(50),
  points integer,
  PRIMARY KEY (tournament, team)
);

create TABLE cricket_prediction (
  "user" VARCHAR(50),
  match int,
  league VARCHAR(50),
  winner VARCHAR(100),
  points int,
  points_scorer VARCHAR(200),
  total_runs int,
  total_fours int,
  total_sixes int,
  total_wickets int,
  PRIMARY KEY ("user", match, league)
);