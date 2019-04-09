package wow.aws

import awscala.ec2.EC2
import wow._

import scala.collection.mutable

object AWS {
  def subnet(vpc: VPC, cidrBlock: String, mapPublicIdOnLaunch: Boolean) = add(Subnet(vpc, cidrBlock, mapPublicIdOnLaunch))


  def vpc(cidrBlock: String): VPC = VPC(cidrBlock)

  def instance(ami: String, instanceType: String): Instance = add(Instance(ami, instanceType))

  def eip(instance: Instance) = add(EIP(instance))

  def internetGateway(vpc: VPC): InternetGateway = add(InternetGateway(vpc))

  def route(vpc: VPC, internetGateway: InternetGateway, destinationCidrBlock: String): Route = add(Route(vpc, internetGateway, destinationCidrBlock))

  var resources = new mutable.MutableList[wow.Resource]()

  def create: Any = plan.create()

  def plan: Plan = Plan(resources)

  def add[R <: wow.Resource](r: R): R = {
    resources += r
    r
  }

  import awscala._

  implicit val ec2 = EC2.at(Region.US_EAST_1)

  case class Instance(ami: String, instanceType: String) extends wow.Resource {
    override def dependencies: List[wow.Resource] = List()

    override def create(): Any = {
      //      val instances = ec2.runAndAwait(ami, ec2.keyPairs.head)
      println(s"creating instance ${this}")
    }
  }

  case class VPC(cidrBlock: String) extends wow.Resource {
    override def dependencies: List[wow.Resource] = List()

    override def create(): Any = ???
  }

  case class InternetGateway(vpc: VPC) extends wow.Resource {
    override def dependencies: List[wow.Resource] = List(vpc)

    override def create(): Any = ???
  }

  case class Route(vpc: VPC, internetGateway: InternetGateway, destinationCidrBlock: String) extends wow.Resource {
    override def dependencies: List[wow.Resource] = List(vpc, internetGateway)

    override def create(): Any = ???
  }

  case class Subnet(vpc: VPC, cidrBlock: String, mapPublicIdOnLaunch: Boolean) extends wow.Resource {
    override def dependencies: List[wow.Resource] = List(vpc)

    override def create(): Any = ???
  }

  case class EIP(instance: Instance) extends wow.Resource {
    override def dependencies: List[wow.Resource] = List(instance)

    override def create(): Any = ???
  }

}

