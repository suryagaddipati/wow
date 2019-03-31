package wow.aws

object AWSApi {

  def main(args: Array[String]) {
    import awscala._
    import awscala.ec2._

    implicit val ec2 = EC2.at(Region.US_EAST_1)

    //    val existings: Seq[Instance] = ec2.instances
    //    ec2.

    //    println(existings)
  }

  def create(instance: Instance): Unit = {
    println(s"creating instance ${instance}")
  }

  def create(eip: EIP): Unit = {
    println(s"create eip ${eip}")
  }


}
