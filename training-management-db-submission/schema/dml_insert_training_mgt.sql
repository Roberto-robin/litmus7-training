#Insert commands
Insert into COURSE and TOPICS
INSERT INTO course VALUES (1, 'Database Systems');



-- INSERT INTO topics VALUES 
(101, 'ER Diagrams', 1),
(102, 'Normalization', 1),
(103, 'SQL Joins', 1);



-- Insert into TRAINERS
INSERT INTO trainers VALUES 
(1, 'Arjun Menon', 'arjun@trainer.com'),
(2, 'Meera Nair', 'meera@trainer.com');



-- Insert into CANDIDATE
INSERT INTO candidate VALUES 
(101, 'Riya Sen', 'riya@email.com', 9876543210, 'In Progress'),
(102, 'Vikram Das', 'vikram@email.com', 9876501234, 'Completed');



-- Insert into BATCHES
INSERT INTO batches VALUES 
(1001, '2025-07-01', '2025-08-30', 1);



-- Insert into BATCH_CANDIDATE
INSERT INTO batch_candidate VALUES 
(101, 1001),
(102, 1001);



-- Insert into BATCH_TRAINERS
INSERT INTO batch_trainers VALUES 
(1001, 1),
(1001, 2);



-- Insert into ASSIGNMENTS
INSERT INTO assignments VALUES 
(201, 'Assignment 1', 'Design an ER Diagram', '2025-07-10', 1001),
(202, 'Assignment 2', 'Write DDL SQL', '2025-07-15', 1001);



-- Insert into SUBMISSIONS
INSERT INTO submissions VALUES 
(1, 101, 201, 85),
(2, 102, 201, 90);



-- Insert into ADMIN
INSERT INTO admin VALUES 
(1, 'Priya Sinha');



-- Insert into USERS
-- Candidate as user
INSERT INTO users VALUES 
(1, 'pass123', 'candidate', 101),
(2, 'pass456', 'candidate', 102);



-- Trainers as users
INSERT INTO users VALUES 
(3, 'password', 'trainer', 1),
(4, 'password', 'trainer', 2);



-- Admin as user
INSERT INTO users VALUES 
(5, 'admin123', 'admin', 1);
