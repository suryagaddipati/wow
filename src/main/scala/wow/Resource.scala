package wow

trait Resource {
  def dependencies: List[Resource]

  def create(): Any
}
