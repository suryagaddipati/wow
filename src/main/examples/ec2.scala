import wow.aws.AWS

val bl = AWS.ec2(instanceType = "t2.micro", ami = "ami-2757f631")
AWS.eip(instance = bl)
