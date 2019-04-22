package surya.wow

import org.scalatest.FlatSpec

class PlanSpec extends FlatSpec {

  case class Res(name: String, deps: Resource*) extends Resource {
    override def dependencies: List[Resource] = deps.toList

    override def create(): Any = {}
  }

  "Plan" should "list additions and deletions" in {
    val plan = Plan(State(Res("meow")), Res("purr"))
    val additions = plan.additions
    val deletions = plan.deletions
    assert(additions == List(Res("purr")))
    assert(deletions == List(Res("meow")))
  }
  "Plan" should "additions should list dependencies" in {
    val one = Res("1", Res("1.1"))
    val plan = Plan(State(), one)
    val additions = plan.additions
    val deletions = plan.deletions
    assert(additions == List(one, Res("1.1")))
  }

}
