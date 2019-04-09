package wow

import scala.Array
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}

//@JsonTypeInfo ( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
//@JsonSubTypes (Array (
//new Type (value = classOf[ProductDetailsSimple], name = "simple"),
//new Type (value = classOf[ProductDetailsComplex], name = "complex")
//) )

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(Array(
  new Type(value = classOf[wow.aws.AWS.Instance], name = "instance"),
))
abstract class Resource {
  def dependencies: List[Resource]

  def create(): Any
}
