package wow

import wow.aws.AWS

import scala.collection.mutable

case class Plan(resources: mutable.MutableList[Resource]) {

  override def toString() = s"creating ${roots}"

  implicit val awsProvider = AWS

  def create() = roots.foreach({ r =>
    try {
      val k = JsonUtil.fromJson[State](readState())
    } catch {
      case e => e.printStackTrace()
    }
    val resp = r.create
    val state = State(List(r))
    writeState(JsonUtil.toJson(state))
  })


  def readState() = {
    val source = scala.io.Source.fromFile("state.json")
    try source.mkString finally source.close()
  }

  def writeState(state: String) = {
    import java.io.PrintWriter
    new PrintWriter("state.json") {
      write(state);
      close
    }
  }

  def roots: List[Resource] = {
    var rList = resources.toList
    resources.foreach(r => {
      rList = rList.filter(rr => !r.dependencies.contains(rr))
    })
    rList
  }

}
