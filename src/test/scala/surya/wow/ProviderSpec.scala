package surya.wow

import org.scalatest.FlatSpec

class ProviderSpec extends FlatSpec {

  case class Res(name: String, deps: Resource*) extends Resource {
    override def dependencies: List[Resource] = deps.toList

    override def create(): Any = {}
  }

  case class DummyProvider(override val state: State = State()) extends ProviderWithState {
  }


  "Plan" should "list additions and deletions" in {

    val provider = DummyProvider(State(Res("meow")))
    provider :+ Res("purr")
    val additions = provider.changes.additions
    val deletions = provider.changes.deletions
    assert(additions == List(Res("purr")))
    assert(deletions == List(Res("meow")))
  }
  "Plan" should "additions should list dependencies" in {
    val one = Res("1", Res("1.1"))
    val plan = DummyProvider()
    plan :+ one
    val additions = plan.changes.additions
    val deletions = plan.changes.deletions
    assert(additions == List(one, Res("1.1")))
  }

}
