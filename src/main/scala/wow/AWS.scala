package wow

import scala.collection.mutable


object AWS {

  def main(args: Array[String]) {
    import awscala._
    import awscala.ec2._

    implicit val ec2 = EC2.at(Region.US_EAST_1)

    val existings: Seq[Instance] = ec2.instances
    //    ec2.

    println(existings)
  }

  var resources = new mutable.MutableList[Resource]()

  case class EC2Resource() extends Resource

  def ec2(ami: String, instanceType: String): Resource = {
    val r = EC2Resource()
    resources += r
    r
  }

  def eip(instance: Resource) = EC2Resource()
}