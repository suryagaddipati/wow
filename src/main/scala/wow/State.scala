package wow

case class State(resources: List[Resource]) {
  def :+(r: Resource): State = State(resources :+ r)

  def has(r: Resource): Boolean = resources.contains(r)

}
