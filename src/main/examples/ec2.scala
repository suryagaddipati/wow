import surya.wow.aws.AWS

val vpc = AWS.vpc(cidrBlock = "10.0.0.0/16")
val internetGateway = AWS.internetGateway(vpc = vpc)
val route = AWS.route(vpc = vpc, internetGateway = internetGateway, destinationCidrBlock = "0.0.0.0/0")

val awsSubnet = AWS.subnet(vpc = vpc, cidrBlock = "10.0.1.0/24", mapPublicIdOnLaunch = true)

val bl = AWS.instance(instanceType = "t2.micro", ami = "ami-2757f631")
AWS.eip(instance = bl)


