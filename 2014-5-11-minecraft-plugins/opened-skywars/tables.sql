SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";

CREATE TABLE IF NOT EXISTS `skywars_player` (
  `player_id`    INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  `player_name`  VARCHAR(16)     NOT NULL UNIQUE,
  `first_seen`   DATETIME        NOT NULL,
  `last_seen`    DATETIME        NOT NULL,
  `score`        INT(4)          NOT NULL DEFAULT 0,
  `games_played` INT(4) UNSIGNED NOT NULL DEFAULT 0,
  `games_won`    INT(4) UNSIGNED NOT NULL DEFAULT 0,
  `kills`        INT(4) UNSIGNED NOT NULL DEFAULT 0,
  `deaths`       INT(4) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`player_id`),
  KEY (`player_name`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =latin1;
