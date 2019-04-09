package wow

import wow.aws.AWS

import scala.collection.mutable

case class Plan(resources: mutable.MutableList[Resource]) {

  override def toString() = s"creating ${roots}"

  implicit val awsProvider = AWS

  def create() = roots.foreach({ r =>
    val resp = r.create
    writeState(JsonUtil.toJson(r))
  })


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
