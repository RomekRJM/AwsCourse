INSERT INTO user(id, username, password, enabled) VALUES(1, 'tester', '$2a$12$RIHZWG9xp6GcM.iIOEPV..9/vPcQs6kqDKnMHjy6lFMqMDbCxOAqC', true);

INSERT INTO chapter(chapter_id, title) VALUES(1, 'S3');
INSERT INTO task (task_id, description, done, title, answer, validator, chapter_chapter_id)
VALUES (1, 'Create a S3 bucket', false, 'S3 Bucket', '', 'rjm.romek.awscourse.validator.BucketExistsValidator', 1);