package game

object Direction extends Enumeration {
  
  type Direction = Value
  
  val Up, Down, Left, Right = Value
  
  def getXChange(x: Direction.Value) : Int = {
    if(x == Left) -1 else if(x == Right) 1 else 0
  }
    
  def getYChange(y: Direction.Value) : Int = {
    if(y == Up) -1 else if(y == Down) 1 else 0
  }
  
}