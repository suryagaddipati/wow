package wow

import scala.collection.mutable

case class Plan(resources: mutable.MutableList[Resource]) {
  override def toString() = s"creating ${resources}"

}
