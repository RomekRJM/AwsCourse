INSERT INTO user(user_id, username, password, enabled)
VALUES (1000, 'tester', '$2a$12$lsMVr5hacA75DixrtCJfJe92fcR3fTqEjnF9NGFhfXOnEM7xpZoG2', true);

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
VALUES (1003, 'Create an EC2 instance using Amazon Linux AMI 2018.03.0 HVM, SSD Volume Type ami {ami-028188d9b49b32a80}. Paste instance id in here: (instanceId).(*ami=ami-028188d9b49b32a80)(*instanceId)',
        'rjm.romek.awscourse.verifier.ec2.EC2AmiVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1004, 'Create an EC2 instance using type {t2.micro}.(*instanceType=t2.micro)',
        'rjm.romek.awscourse.verifier.ec2.EC2TypeVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1005, 'Use vpc:{vpc-9abd78ff | vpc-dev-ocd}.(*vpcId=vpc-9abd78ff)(*instanceId)',
        'rjm.romek.awscourse.verifier.ec2.EC2VpcVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1006, 'Use subnet:{subnet-6e4fe30b | ec2-c-dev-ocd | eu-west-1c}.(*subnetId=subnet-6e4fe30b)(*instanceId)',
        'rjm.romek.awscourse.verifier.ec2.EC2SubnetVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1007, 'Tag it with tag {app:awscourse}.(*tags=app:awscourse)(*instanceId)',
        'rjm.romek.awscourse.verifier.ec2.EC2TagsVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1008, 'Associate it with security group, that allows ssh traffic on port 22.(*ingress=tcp:22:22:0.0.0.0/0)(*instanceId)',
        'rjm.romek.awscourse.verifier.ec2.EC2SecurityGroupVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1009, '.. and http on port 80. Create new key pair for it - name it after yourself.(*ingress=tcp:80:80:0.0.0.0/0)(*instanceId)',
        'rjm.romek.awscourse.verifier.ec2.EC2SecurityGroupVerifier', 1001);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1010, 'Read private instance ip address and put it here: (u), then ssh to the instance and run following command {sudo python -m SimpleHTTPServer 80}.',
        'rjm.romek.awscourse.verifier.http.HttpVerifier', 1001);

INSERT INTO chapter(chapter_id, title) VALUES (1002, 'ElasticBeanstalk');

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1011, 'Deploy {https://gitlab.tech.lastmile.com/vOCET/aws-sample-project/tree/master/aws-sample-maven} to ElasticBeanstalk environment, wait until it is green and paste environment name here: (environmentName).',
        'rjm.romek.awscourse.verifier.elasticbeanstalk.EnvironmentHealthyVerifier', 1002);

INSERT INTO chapter(chapter_id, title) VALUES (1003, 'RDS');

INSERT INTO task (task_id, description, verifier, chapter_chapter_id, page)
VALUES (1012, 'Create an MySql RDS instance, as show on screenshots below. Paste instance name in here: (dbInstanceId).(*engine=mysql)',
        'rjm.romek.awscourse.verifier.rds.RDSEngineVerifier', 1003, 'rds');

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1013, 'When db launches, make sure it is reachable from outside world by adjusting security group and provide endpoint: (endpoint), database: (database), user: (user), password: (!password)',
        'rjm.romek.awscourse.verifier.db.DBConnectionVerifier', 1003);

INSERT INTO chapter(chapter_id, title) VALUES (1004, 'IAM');

INSERT INTO task (task_id, description, verifier, chapter_chapter_id, page)
VALUES (1014, 'Create role, that can be assumed by trusted entity as shown below and allows {ec2:CreateVolume} on {*} and paste role arn in here: (roleArn).(*region=eu-west-1)',
        'rjm.romek.awscourse.verifier.iam.CreateVolumeInRegionAllowedVerifier', 1004, 'iam_trusted_policy');

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1015, 'Deny creating volumes in {us-east-1} region.(*roleArn)(*region=us-east-1)',
        'rjm.romek.awscourse.verifier.iam.CreateVolumeInRegionDisallowedVerifier', 1004);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1016, 'Deny creating volumes larger than 100GB in {sa-east-1} region.(*roleArn)(*region=sa-east-1)(*maxSize=100)',
        'rjm.romek.awscourse.verifier.iam.CreateVolumeUpToSizeAllowedVerifier', 1004);

INSERT INTO task (task_id, description, verifier, chapter_chapter_id)
VALUES (1017, 'Deny creating volumes in {eu-central-1} region, that are of type {standard} or {st1} and larger than 500GB.(*roleArn)(*region=eu-central-1)(*maxSize=500)(*deniedTypes=standard,st1)',
        'rjm.romek.awscourse.verifier.iam.CreateVolumeUpToSizeWithTypeAllowedVerifier', 1004);