INSERT INTO chapter(chapter_id, title) VALUES(1, 'Test');
INSERT INTO task (task_id, description, done, title, validator, chapter_chapter_id) VALUES (2, 'Description', false, 'Task1', 'rjm.romek.awscourse.validator.BucketExistsValidator', 1);
INSERT INTO task (task_id, description, done, title, validator, chapter_chapter_id) VALUES (3, 'Description', false, 'Task2', 'rjm.romek.awscourse.validator.BucketExistsValidator', 1);