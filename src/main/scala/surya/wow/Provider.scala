package surya.wow


trait Provider {
  def resources: Seq[Resource] = List()

  def :+(res: Resource): Provider = this

  def changes: Provider.Changes
}

trait ProviderWithState extends Provider {
  def state: State

  def changes = {

    val roots = getRoots
    val add = roots.foldLeft(List[Resource]()) { (ad, r) =>
      if (!state.has(r)) {
        ad ++ r.getAll
      } else {
        ad
      }
    }
    Provider.Changes(add, state.resources.diff(add))
  }

  def getRoots(): List[Resource] = {
    var rList = resources.toList
    resources.foreach(r => {
      rList = rList.filter(rr => !r.dependencies.contains(rr))
    })
    rList
  }
}


object Provider {

  case class Changes(additions: Seq[Resource], deletions: Seq[Resource])

}
