package surya.wow

import org.scalatest.FlatSpec

class PlanSpec extends FlatSpec {

  case class Res() extends Resource {
    override def dependencies: List[Resource] = List()

    override def create(): Any = {}
  }

  "Plan" should "describe" in {
    val plan = Plan(State(Res()), Res())
    val planDescription: Plan.Description = plan.desribe()
    planDescription
  }

}
