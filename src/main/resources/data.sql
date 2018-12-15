INSERT INTO user(user_id, username, password, enabled) VALUES(1000, 'tester', '$2a$12$RIHZWG9xp6GcM.iIOEPV..9/vPcQs6kqDKnMHjy6lFMqMDbCxOAqC', true);
INSERT INTO user(user_id, username, password, enabled) VALUES(1001, 'pusiak', '$2a$12$RIHZWG9xp6GcM.iIOEPV..9/vPcQs6kqDKnMHjy6lFMqMDbCxOAqC', true);

INSERT INTO chapter(chapter_id, title) VALUES(1000, 'S3');

INSERT INTO task (task_id, description, title, validator, chapter_chapter_id)
VALUES (1000, 'Create a S3 bucket {bucketName} and create file {keyName} inside it.',
        'S3 Bucket', 'rjm.romek.awscourse.validator.KeyExistsValidator', 1000);

INSERT INTO task (task_id, description, title, validator, chapter_chapter_id)
VALUES (1001, 'Ensure versioning is enabled for it.{*bucketName}',
        'S3 Bucket', 'rjm.romek.awscourse.validator.VersioningEnabledValidator', 1000);