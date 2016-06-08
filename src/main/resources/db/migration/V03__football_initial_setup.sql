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

create TABLE football_tournament_team (
  tournament VARCHAR(100),
  team VARCHAR(100),
  "group" VARCHAR(50),
  points int,
  goals_for int,
  goals_against int,
  PRIMARY KEY (tournament, team)
);

create TABLE football_prediction (
  "user" VARCHAR(50),
  match int,
  league VARCHAR(50),
  winner VARCHAR(100),
  points int,
  points_scorer VARCHAR(200),
  team_a_score int,
  team_b_score int,
  PRIMARY KEY ("user", match, league)
);