package wow

import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(Array(
  new Type(value = classOf[wow.aws.AWS.Instance], name = "instance"),
  new Type(value = classOf[wow.aws.AWS.EIP], name = "eip"),
))
abstract class Resource {
  def dependencies: List[Resource]

  def doCreate() = {
    println(s"creating resource ${this}")
    create()
  }

  def create(): Any
}
