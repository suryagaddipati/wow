package surya.wow

import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import surya.wow.provider.aws.AWS

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(Array(
  new Type(value = classOf[AWS.Instance], name = "instance"),
  new Type(value = classOf[AWS.EIP], name = "eip")
))
abstract class Resource {
  val getAll: Seq[Resource] = dependencies.foldLeft(List(this)) { (all, depp) => all ++ depp.getAll }

  def dependencies: List[Resource] = List()

  def doCreate() = {
    println(s"creating resource ${this}")
    create()
  }

  def create(): Any
}
