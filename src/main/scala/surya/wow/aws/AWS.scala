package surya.wow.aws

import awscala.ec2.EC2
import surya.wow.ProviderWithState

object AWS extends ProviderWithState {

  def subnet(vpc: VPC, cidrBlock: String, mapPublicIdOnLaunch: Boolean) = :+(Subnet(vpc, cidrBlock, mapPublicIdOnLaunch))

  def vpc(cidrBlock: String): VPC = :+(VPC(cidrBlock))

  def instance(ami: String, instanceType: String): Instance = (Instance(ami, instanceType))

  def eip(instance: Instance) = (EIP(instance))

  def internetGateway(vpc: VPC): InternetGateway = :+(InternetGateway(vpc))

  def route(vpc: VPC, internetGateway: InternetGateway, destinationCidrBlock: String): Route = :+(Route(vpc, internetGateway, destinationCidrBlock))


  implicit class RichEC2(val ec2: EC2) extends AnyVal {
    def eip(instanceId: String) = {
      import com.amazonaws.services.ec2.model.{AllocateAddressRequest, AssociateAddressRequest, DomainType}
      val allocate_request = new AllocateAddressRequest().withDomain(DomainType.Vpc)

      val allocate_response = ec2.allocateAddress(allocate_request)

      val allocation_id = allocate_response.getAllocationId

      val associate_request = new AssociateAddressRequest().withInstanceId(instanceId).withAllocationId(allocation_id)

      val associate_response = ec2.associateAddress(associate_request)
      (allocate_response, associate_response)
    }
  }

  import awscala._

  implicit val ec2 = EC2.at(Region.US_EAST_1)

  case class Instance(ami: String, instanceType: String) extends surya.wow.Resource {
    var id = ""

    override def dependencies: List[surya.wow.Resource] = List()

    override def create(): Any = {
      val instances = ec2.runAndAwait(ami, ec2.keyPairs.head)
      val instance = instances(0)
      this.id = instance.instanceId
    }
  }

  case class EIP(instance: Instance) extends surya.wow.Resource {
    override def dependencies: List[surya.wow.Resource] = List(instance)

    override def create(): Any = {
      ec2.eip(instance.id)
    }
  }

  case class VPC(cidrBlock: String) extends surya.wow.Resource {
    override def dependencies: List[surya.wow.Resource] = List()

    override def create(): Any = ???
  }

  case class InternetGateway(vpc: VPC) extends surya.wow.Resource {
    override def dependencies: List[surya.wow.Resource] = List(vpc)

    override def create(): Any = ???
  }

  case class Route(vpc: VPC, internetGateway: InternetGateway, destinationCidrBlock: String) extends surya.wow.Resource {
    override def dependencies: List[surya.wow.Resource] = List(vpc, internetGateway)

    override def create(): Any = ???
  }

  case class Subnet(vpc: VPC, cidrBlock: String, mapPublicIdOnLaunch: Boolean) extends surya.wow.Resource {
    override def dependencies: List[surya.wow.Resource] = List(vpc)

    override def create(): Any = ???
  }


}

