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
VALUES (1003, 'Create an EC2 instance using type:"t2.micro". Paste instance id in here: (instanceId).(*instanceType=m3.medium)',
        'rjm.romek.awscourse.verifier.ec2.EC2TypeVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1004, 'Use vpc:"vpc-2e32f54b | vpc-evl-ocd".(*vpcId=vpc-2e32f54b)(*instanceId)',
        'rjm.romek.awscourse.verifier.ec2.EC2VpcVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1005, 'Use subnet:"subnet-d08352a7 | rds-c-evl-ocd | eu-west-1c".(*subnetId=subnet-d08352a7)(*instanceId)',
        'rjm.romek.awscourse.verifier.ec2.EC2SubnetVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1006, 'Use Amazon Linux AMI 2018.03.0 HVM ami:"ami-e6fc5e91".(*ami=ami-e6fc5e91)(*instanceId)',
        'rjm.romek.awscourse.verifier.ec2.EC2AmiVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1007, 'Tag it with tag:"key:app, value:awscourse".(*tags=app:awscourse)(*instanceId)',
        'rjm.romek.awscourse.verifier.ec2.EC2TagsVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1008, 'Associate create security group, that allows any traffic on port 80 and attach it to instance.(*ingress=tcp:80:80:0.0.0.0/0)(*instanceId)',
        'rjm.romek.awscourse.verifier.ec2.EC2SecurityGroupVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1009, 'Read private instance ip address and put it here: (u).',
        'rjm.romek.awscourse.verifier.http.HttpVerifier', 1001);

INSERT INTO chapter(chapter_id, title) VALUES (1002, 'ElasticBeanstalk');

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1010, 'Create ElasticBeanstalk environment, wait until it is green and paste environment name here: (environmentName).',
        'rjm.romek.awscourse.verifier.elasticbeanstalk.EnvironmentHealthyVerifier', 1002);

INSERT INTO chapter(chapter_id, title) VALUES (1003, 'IAM');

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1011, 'Create role, that can be assumed by anyone and allows ec2:CreateVolume on * and paste role arn in here: (roleArn).(*region=eu-west-1)',
        'rjm.romek.awscourse.verifier.iam.CreateVolumeInRegionAllowedVerifier', 1003);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1012, 'Deny creating volumes in us-east-1 region.(*roleArn)(*region=us-east-1)',
        'rjm.romek.awscourse.verifier.iam.CreateVolumeInRegionDisallowedVerifier', 1003);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1013, 'Allow creating volumes in sa-east-1 region, but only up to 100GB.(*roleArn)(*region=sa-east-1)(*maxSize=100)',
        'rjm.romek.awscourse.verifier.iam.CreateVolumeUpToSizeAllowedVerifier', 1003);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1014, 'Deny creating volumes in eu-central-1 region, that are of type "standard" or "st1" and larger than 500GB.(*roleArn)(*region=eu-central-1)(*maxSize=500)(*deniedTypes=standard,st1)',
        'rjm.romek.awscourse.verifier.iam.CreateVolumeUpToSizeWithTypeAllowedVerifier', 1003);
