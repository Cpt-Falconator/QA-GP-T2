DROP TABLE IF EXISTS `trainee` CASCADE;

CREATE TABLE `trainee` (
 	 `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
 	 `username` VARCHAR(60) NOT NULL,
 	 `first_name` VARCHAR(60) NOT NULL,
 	 `last_name` VARCHAR(60) NOT NULL,
 	 `cohort` VARCHAR(40) NOT NULL,
 	 `password` VARCHAR(45) NOT NULL,
 	 `trainee_email` VARCHAR(60) NOT NULL
);