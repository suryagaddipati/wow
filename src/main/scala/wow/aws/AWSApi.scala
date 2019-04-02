package wow.aws

import awscala._
import awscala.ec2._

object AWSApi {
  implicit val ec2 = EC2.at(Region.US_EAST_1)

  def main(args: Array[String]) {


    //    val existings: Seq[Instance] = ec2.instances
    //    ec2.

    //    println(existings)
  }

  def create(instance: Instance): Unit = {
    val instances = ec2.runAndAwait(instance.ami, ec2.keyPairs.head)

    println(s"creating instance ${instance}")
  }

  def create(eip: EIP): Unit = {
    println(s"create eip ${eip}")
  }


}
