AWS Course [![Build Status](https://travis-ci.org/RomekRJM/AwsCourse.svg?branch=master)](https://travis-ci.org/RomekRJM/AwsCourse) ![Test Coverage](jacoco.svg)
=================================

Cloned from: https://github.com/RomekRJM/AwsCourse


Installation
---------------
Application requires following permissions to work:
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