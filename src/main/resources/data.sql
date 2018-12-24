INSERT INTO user(user_id, username, password, enabled)
VALUES (1000, 'tester', '$2a$12$RIHZWG9xp6GcM.iIOEPV..9/vPcQs6kqDKnMHjy6lFMqMDbCxOAqC', true);
INSERT INTO user(user_id, username, password, enabled)
VALUES (1001, 'pusiak', '$2a$12$RIHZWG9xp6GcM.iIOEPV..9/vPcQs6kqDKnMHjy6lFMqMDbCxOAqC', true);

INSERT INTO chapter(chapter_id, title) VALUES (1000, 'S3');

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1000, 'Create a S3 bucket (bucketName) and create file (keyName) inside it.',
        'rjm.romek.awscourse.verifier.s3.KeyExistsVerifier', 1000);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1001, 'Ensure versioning is enabled for it.(*bucketName)',
        'rjm.romek.awscourse.verifier.s3.VersioningEnabledVerifier', 1000);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1002,
        CONCAT('Enable lifecycle policy for the bucket. Use standard storage class for first 30 days, ',
               'then move it to standard infrequent and after that to glacier - 90 days after creation.',
               '(*STANDARD_IA=30)(*GLACIER=90)'),
        'rjm.romek.awscourse.verifier.s3.LifecyclePolicyVerifier', 1000);

INSERT INTO chapter(chapter_id, title) VALUES (1001, 'EC2');

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1003, 'Create an EC2 instance using type:"t2.micro", vpc:"vpc-2e32f54b | vpc-evl-ocd", subnet:"subnet-df8352a8 | rds-c-evl-ocd | eu-west-1c" and add tag:"key:app, value:awscourse". Paste instance id in here: (instanceId).(*instanceType=m3.medium)(*ami=ami-09693313102a30b2c)(*vpc=vpc-2e32f54b)(*subnet=subnet-df8352a8)(*tag=app:awscourse)',
        'rjm.romek.awscourse.verifier.ec2.EC2TypeVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1004, 'Use Amazon Linux AMI 2018.03.0 HVM ami:"ami-e6fc5e91".(*ami=ami-e6fc5e91)(*instanceId)',
        'rjm.romek.awscourse.verifier.ec2.EC2AmiVerifier', 1001);
