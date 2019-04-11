package wow

import wow.aws.AWS

import scala.collection.mutable

case class Plan(resources: mutable.MutableList[Resource]) {

  override def toString() = s"creating ${roots}"

  implicit val awsProvider = AWS


  def create() = roots.foldLeft(State.current) { (s, r) =>
    if (s.has(r)) {
      println(s"Resource ${r} exists. Skipping..")
      s
    } else {
      val resp = r.create
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
