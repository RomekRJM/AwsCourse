AWS Course [![Build Status](https://travis-ci.org/RomekRJM/AwsCourse.svg?branch=master)](https://travis-ci.org/RomekRJM/AwsCourse) ![Test Coverage](jacoco.svg)
=================================

Cloned from: https://github.com/RomekRJM/AwsCourse


Installation
---------------
1. Install ebcli https://aws.amazon.com/getting-started/tutorials/set-up-command-line-elastic-beanstalk/.
2. Run following command in awscourse dir ``eb create -db -db.engine mysql -db.version 5.7.28 -c awscourse awscourse``.
3. Follow instructions on the screen, example:
```bash
eb create -db -db.engine mysql -db.version 5.7.28 -c awscourse awscourse

Enter an RDS DB username (default is "ebroot"):
Enter an RDS DB master password:
Retype password to confirm:
Uploading: [##################################################] 100% Done...
Environment details for: awscourse
  Application name: awscourse
  Region: eu-west-1
  Deployed Version: app-e852-200331_113427
  Environment ID: e-ydcn4rpmsi
  Platform: arn:aws:elasticbeanstalk:eu-west-1::platform/Tomcat 8.5 with Java 8 running on 64bit Amazon Linux/3.3.4
  Tier: WebServer-Standard-1.0
  CNAME: awscourse.eu-west-1.elasticbeanstalk.com
  Updated: 2020-03-31 09:35:59.151000+00:00
```
4. Set spring profile: `eb setenv SPRING_PROFILES_ACTIVE=prod`
```bash
2020-03-31 09:48:35    INFO    Environment update is starting.
2020-03-31 09:48:43    INFO    Updating environment awscourse's configuration settings.
2020-03-31 09:49:49    INFO    Successfully deployed new configuration to environment.
```
5. Set server port to 5000 as Beanstalk requires it: `eb setenv SERVER_PORT=5000`
```bash
2020-03-31 09:48:35    INFO    Environment update is starting.
2020-03-31 09:48:43    INFO    Updating environment awscourse's configuration settings.
2020-03-31 09:49:49    INFO    Successfully deployed new configuration to environment.
```
6. Build war file ```mvn clean package```
7. Deploy application ```eb deploy```
4. Application requires following permissions to work:
```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "elasticbeanstalk:Check*",
                "elasticbeanstalk:Describe*",
                "elasticbeanstalk:List*",
                "elasticbeanstalk:RequestEnvironmentInfo",
                "elasticbeanstalk:RetrieveEnvironmentInfo",
                "ec2:Describe*",
                "elasticloadbalancing:Describe*",
                "autoscaling:Describe*",
                "s3:Get*",
                "s3:List*",
                "sts:AssumeRole",
                "rds:Describe*"
            ],
            "Resource": "*"
        }
    ]
}
```