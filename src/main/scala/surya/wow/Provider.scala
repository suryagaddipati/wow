package surya.wow

import scala.collection.convert.Wrappers.MutableSeqWrapper
import scala.collection.mutable
import scala.collection.mutable.ListBuffer


trait Provider {
  val resources: mutable.MutableList[Resource] = new mutable.MutableList[Resource]()

  def :+(res: Resource): Provider = {
    resources += res
    this
  }

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
