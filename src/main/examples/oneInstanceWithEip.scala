import surya.wow.aws.AWS

val one = AWS.instance(instanceType = "t2.micro", ami = "ami-2757f631")
val eip = AWS.eip(one)


