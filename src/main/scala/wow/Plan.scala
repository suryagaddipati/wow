package wow

import java.io.PrintWriter

import wow.aws.AWS

import scala.collection.mutable

case class Plan(resources: List[Resource]) {

  override def toString() = s"creating ${roots}"

  implicit val awsProvider = AWS


  def create(state: State = State.current): PrintWriter = roots.foldLeft(state) { (s, r) =>
    Plan(r.dependencies).create(state)
    if (s.has(r)) {
      println(s"Resource ${r} exists. Skipping..")
      s
    } else {
      val resp = r.doCreate()
      s :+ r
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
