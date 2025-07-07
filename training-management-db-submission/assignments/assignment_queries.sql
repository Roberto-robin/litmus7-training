#Assignment queries

1.Get all batches a candidate is enrolled in, with their status
SELECT c.Cndt_name, b.Batch_code, c.Cndt_Status
FROM candidate c
JOIN batch_candidate bc ON c.Cndt_ID = bc.Candidate_id
JOIN batches b ON bc.Batch_code = b.Batch_code;


2.Get all trainers assigned to a batch
SELECT b.Batch_code, t.Trainer_name, t.Trainer_email
FROM batches b
JOIN batch_trainers bt ON b.Batch_code = bt.Batch_code
JOIN trainers t ON bt.Trainer_id = t.Trainer_ID;


3.Get all topics under a course
SELECT c.Course_name, t.Topic_name
FROM course c
JOIN topics t ON c.Course_id = t.Course_id;



4.List assignment scores for a candidate in a batch
SELECT c.Cndt_name, a.Title AS Assignment_Title, s.Score, b.Batch_code
FROM submissions s
JOIN candidate c ON s.Cndt_id = c.Cndt_ID
JOIN assignments a ON s.Assignment_number = a.Number
JOIN batches b ON a.Batch = b.Batch_code;



5.List candidates with status "Completed" in a given batch
SELECT c.Cndt_name, c.Cndt_email, b.Batch_code
FROM candidate c
JOIN batch_candidate bc ON c.Cndt_ID = bc.Candidate_id
JOIN batches b ON bc.Batch_code = b.Batch_code
WHERE c.Cndt_Status = 'Completed';
