package surya.wow

import scala.collection.mutable


trait Provider {
  val resources: mutable.MutableList[Resource] = new mutable.MutableList[Resource]()

  def :+[R <: Resource](res: R): R = {
    resources += res
    res
  }

  def changes: Provider.Changes
}

trait ProviderWithState extends Provider {
  def state: State = State.current()

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
