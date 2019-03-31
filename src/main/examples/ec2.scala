import wow.AWS

val bl = AWS.ec2(ami = "ami-2757f631", instanceType = "t2.micro")
AWS.eip(instance = bl)

print(bl)