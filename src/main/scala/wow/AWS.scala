package wow


object AWS {
  def main(args: Array[String]) {
    import awscala._
    import awscala.ec2._

    implicit val ec2 = EC2.at(Region.US_EAST_1)

    val existings: Seq[Instance] = ec2.instances
    //    ec2.

    println(existings)
  }

  def instance(name: String)(f: String => Unit): Unit = {

  }

  instance(name = "meow")(mew => ""

  )

  def ec2(ami: String, instanceType: String) = "meow"
}