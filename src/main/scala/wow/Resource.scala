package wow

trait Resource {
  def dependencies: List[Resource]
}
