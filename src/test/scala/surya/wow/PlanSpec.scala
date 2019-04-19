package surya.wow

import org.scalatest.FlatSpec

class PlanSpec extends FlatSpec {

  case class Res(name: String) extends Resource {
    override def dependencies: List[Resource] = List()

    override def create(): Any = {}
  }

  "Plan" should "describe" in {
    val plan = Plan(State(Res("meow")), Res("purr"))
    val planDescription: Plan.Description = plan.describe()
    val additions = planDescription.additions()
    val deletions = planDescription.deletions()
    assert(additions == List(Res("purr")))
    assert(deletions == List(Res("meow")))
  }

}
