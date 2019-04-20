package surya.wow

import surya.wow.aws.AWS

case class Plan(additions: Seq[Resource], deletions: Seq[Resource]) {


  implicit val awsProvider = AWS


  def create(): State = ???

  //    getRoots.foldLeft(state) { (s, r) =>
  //    val newState = Plan(s, r.dependencies: _*).create()
  //    if (newState.has(r)) {
  //      println(s"Resource ${r} exists. Skipping..")
  //      newState
  //    } else {
  //      val resp = r.doCreate()
  //      newState :+ r
  //    }
  //  }.save()


}

object Plan {

  def apply(state: State, resources: Resource*): Plan = {

    val roots = getRoots(resources: _*)
    val add = roots.foldLeft(List[Resource]()) { (ad, r) =>
      if (!state.has(r)) {
        (ad :+ r)
      } else {
        ad
      }
    }

    Plan(add, state.resources.diff(add))

  }

  def getRoots(resources: Resource*): List[Resource] = {
    var rList = resources.toList
    resources.foreach(r => {
      rList = rList.filter(rr => !r.dependencies.contains(rr))
    })
    rList
  }

}
