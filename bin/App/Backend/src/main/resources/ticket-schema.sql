DROP TABLE `ticket`;

CREATE TABLE ticket (
  	id BIGINT PRIMARYKEY AUTO_INCREMENT,
  	title VARCHAR(20) NOT NULL,
  	issue VARCHAR(20) NOT NULL,
 	topic VARCHAR(20) NOT NULL,
 	submitDate CURRENT_TIMESTAMP NOT NULL,
 	urgency INT NOT NULL,
  	status VARCHAR(20) NOT NULL,
  	trainer BIGINT FOREIGN KEY REFERENCES trainer(id),
  	trainees BIGINT FOREIGN KEY REFERENCES trainee_ticket(trainee_id)
);