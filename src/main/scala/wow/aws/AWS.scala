package wow.aws

import wow._

import scala.collection.mutable

object AWS extends Provider {
  def subnet(vpc: VPC, cidrBlock: String, mapPublicIdOnLaunch: Boolean) = add(Subnet(vpc, cidrBlock, mapPublicIdOnLaunch))


  def vpc(cidrBlock: String): VPC = VPC(cidrBlock)

  def ec2(ami: String, instanceType: String): Instance = add(Instance(ami, instanceType))

  def eip(instance: Instance) = add(EIP(instance))

  def internetGateway(vpc: VPC): InternetGateway = add(InternetGateway(vpc))

  def route(vpc: VPC, internetGateway: InternetGateway, destinationCidrBlock: String): Route = add(Route(vpc, internetGateway, destinationCidrBlock))

  var resources = new mutable.MutableList[Resource]()

  def create: Any = plan.create()

  def plan: Plan = Plan(resources)

  def add[R <: Resource](r: R): R = {
    resources += r
    r
  }


  override def create(resource: Resource): Unit = {
    resource match {
      case i: Instance => AWSApi.create(i)
      case eip: EIP => AWSApi.create(eip)
    }
  }

}

case class Instance(ami: String, instanceType: String) extends Resource {
  override def dependencies: List[Resource] = List()

}

case class VPC(cidrBlock: String) extends Resource {
  override def dependencies: List[Resource] = List()

}

case class InternetGateway(vpc: VPC) extends Resource {
  override def dependencies: List[Resource] = List(vpc)

}

case class Route(vpc: VPC, internetGateway: InternetGateway, destinationCidrBlock: String) extends Resource {
  override def dependencies: List[Resource] = List(vpc, internetGateway)
}

case class Subnet(vpc: VPC, cidrBlock: String, mapPublicIdOnLaunch: Boolean) extends Resource {
  override def dependencies: List[Resource] = List(vpc)
}

case class EIP(instance: Instance) extends Resource {
  override def dependencies: List[Resource] = List(instance)
}
