package surya.wow

import java.io.PrintWriter

import surya.wow.aws.AWS

import scala.collection.mutable

case class Plan(state: State, resources: Resource*) {
  def desribe(): Plan.Description = Plan.Description()


  override def toString() = s"creating ${roots}"

  implicit val awsProvider = AWS


  def create(): State = roots.foldLeft(state) { (s, r) =>
    val newState = Plan(s, r.dependencies: _*).create()
    if (newState.has(r)) {
      println(s"Resource ${r} exists. Skipping..")
      newState
    } else {
      val resp = r.doCreate()
      newState :+ r
    }
  }.save()


  def roots: List[Resource] = {
    var rList = resources.toList
    resources.foreach(r => {
      rList = rList.filter(rr => !r.dependencies.contains(rr))
    })
    rList
  }


}

object Plan {

  case class Description()

}
