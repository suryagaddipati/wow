package wow

import scala.collection.mutable


object AWS {
  def plan: Plan = Plan(resources)


  var resources = new mutable.MutableList[Resource]()


  def ec2(ami: String, instanceType: String): Instance = add(Instance(ami, instanceType))

  def eip(instance: Instance) = add(EIP(instance))

  def add[R <: Resource](r: R): R = {
    resources += r
    r
  }

  case class Instance(ami: String, instanceType: String) extends Resource {
    override def dependencies: List[Resource] = List()
  }

  case class EIP(instance: Instance) extends Resource {
    override def dependencies: List[Resource] = List(instance)
  }

}