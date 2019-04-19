package surya.wow

import java.io.PrintWriter

import surya.wow.aws.AWS

import scala.collection.mutable

case class Plan(state: State, resources: Resource*) {
  def describe(): Plan.Description = {
    val roots = getRoots
    val ad = roots.foldLeft(List[Resource]()) { (ad, r) =>
      if (!state.has(r)) {
        (ad :+ r)
      } else {
        ad
      }
    }

    Plan.Description(ad, state.diff(ad))
  }


  override def toString() = s"creating ${getRoots}"

  implicit val awsProvider = AWS


  def create(): State = getRoots.foldLeft(state) { (s, r) =>
    val newState = Plan(s, r.dependencies: _*).create()
    if (newState.has(r)) {
      println(s"Resource ${r} exists. Skipping..")
      newState
    } else {
      val resp = r.doCreate()
      newState :+ r
    }
  }.save()


  def getRoots: List[Resource] = {
    var rList = resources.toList
    resources.foreach(r => {
      rList = rList.filter(rr => !r.dependencies.contains(rr))
    })
    rList
  }


}

object Plan {

  case class Description(a: Seq[Resource], d: Seq[Resource]) {
    def additions(): Seq[Resource] = a

    def deletions(): Seq[Resource] = d
  }

}
