AWS Course [![Build Status](https://travis-ci.org/RomekRJM/AwsCourse.svg?branch=master)](https://travis-ci.org/RomekRJM/AwsCourse) ![Test Coverage](jacoco.svg)
=================================

Cloned from: https://github.com/RomekRJM/AwsCourse


Installation
---------------
1. Install ebcli https://aws.amazon.com/getting-started/tutorials/set-up-command-line-elastic-beanstalk/.
2. Run following command in awscourse dir ``eb create -db -db.engine mysql -c awscourse``.
3. Follow instructions on the screen, example:
```bash
Enter Environment Name
(default is awscourse-dev): awscourse

Select a load balancer type
1) classic
2) application
3) network
(default is 2):

Would you like to enable Spot Fleet requests for this environment?
(y/N): N

Enter an RDS DB username (default is "ebroot"): awscourse
Enter an RDS DB master password:
Retype password to confirm:

2.0+ Platforms require a service role. We will attempt to create one for you. You can specify your own role using the --service-role option.
Type "view" to see the policy, or just press ENTER to continue:
```
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