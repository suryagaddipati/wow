package wow

import java.io.PrintWriter

import wow.aws.AWS

import scala.collection.mutable

case class Plan(resources: List[Resource]) {

  override def toString() = s"creating ${roots}"

  implicit val awsProvider = AWS


  def create(state: State = State.current): State = roots.foldLeft(state) { (s, r) =>
    val newState = Plan(r.dependencies).create(s)
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
