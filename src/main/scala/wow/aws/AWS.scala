package wow.aws

import wow._

import scala.collection.mutable

object AWS extends Provider {
  def create: Any = plan.create()

  def plan: Plan = Plan(resources)


  var resources = new mutable.MutableList[Resource]()


  def ec2(ami: String, instanceType: String): Instance = add(Instance(ami, instanceType))

  def eip(instance: Instance) = add(EIP(instance))

  def add[R <: Resource](r: R): R = {
    resources += r
    r
  }


  override def create(resource: Resource): Unit = {
    resource match {
      case i: Instance => AWSApi.create(i)
      case eip: EIP => AWSApi.create(eip)
    }
    // AWSApi.create(resource)
  }

}

case class Instance(ami: String, instanceType: String) extends Resource {
  override def dependencies: List[Resource] = List()

}

case class EIP(instance: Instance) extends Resource {
  override def dependencies: List[Resource] = List(instance)
}
