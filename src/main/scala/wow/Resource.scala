package wow

trait Resource {
  def dependencies: List[Resource]

  def create(implicit provider: Provider): Any = provider.create(this)
}
