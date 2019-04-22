package surya.wow


trait Plan {
  def additions: Seq[Resource]

  def deletions: Seq[Resource]

  def create(): State = ???
}


object Plan {

  def apply(state: State, resources: Resource*): Plan = {
    val roots = getRoots(resources: _*)
    val add = roots.foldLeft(List[Resource]()) { (ad, r) =>
      if (!state.has(r)) {
        ad ++ r.getAll
      } else {
        ad
      }
    }

    //new Plan(add, state.resources.diff(add))
    new Plan {
      override def additions: Seq[Resource] = add

      override def deletions: Seq[Resource] = state.resources.diff(add)
    }
  }

  def getRoots(resources: Resource*): List[Resource] = {
    var rList = resources.toList
    resources.foreach(r => {
      rList = rList.filter(rr => !r.dependencies.contains(rr))
    })
    rList
  }

}
