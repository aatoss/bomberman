package game

import scala.util.Random

class AI (number: Int, map: Map) extends Player(number, map, true){
  
  
  private var position = startPosition
  
   val random = new Random
   
   val directions = Array(Direction.Up, Direction.Down, Direction.Left, Direction.Right)
  
  override def move(direction: Direction.Value) = {
    
    var dir = directions(random.nextInt(4))
    
    val horisontalChange = Direction.getXChange(dir)
    val verticalChange = Direction.getYChange(dir)
    
    dir match{
      case Direction.Up    =>{if(map.grid(position._2 + verticalChange)(position._1 + horisontalChange).canEnter){
        map.grid(position._2)(position._1).removePlayer
        map.grid(position._2 + verticalChange)(position._1 + horisontalChange).addPlayer(this)
        position = (position._1 + horisontalChange, position._2 + verticalChange)
//        println( horisontalChange + " ja " +  verticalChange + " ja sijainti " + getPosition) // for debugging purposes
//        println(map.grid(position._2)(position._1).playerIn)
        }}
      
      case Direction.Down  =>{if(map.grid(position._2 + verticalChange)(position._1 + horisontalChange).canEnter){
        map.grid(position._2)(position._1).removePlayer
        map.grid(position._2 + verticalChange)(position._1 + horisontalChange).addPlayer(this)
        position = (position._1 + horisontalChange, position._2 + verticalChange)
//        println( horisontalChange + " ja " +  verticalChange + " ja sijainti " + getPosition)
//        println(map.grid(position._2)(position._1).playerIn)
        }}
      
      case Direction.Left  =>{if(map.grid(position._2 + verticalChange)(position._1 + horisontalChange).canEnter){
        map.grid(position._2)(position._1).removePlayer
        map.grid(position._2 + verticalChange)(position._1 + horisontalChange).addPlayer(this)
        position = (position._1 + horisontalChange, position._2 + verticalChange)
//        println( horisontalChange + " ja " +  verticalChange + " ja sijainti " + getPosition)
//        println(map.grid(position._2)(position._1).playerIn)
        }}
      
      case Direction.Right =>{if(map.grid(position._2 + verticalChange)(position._1 + horisontalChange).canEnter){
        map.grid(position._2)(position._1).removePlayer
        map.grid(position._2 + verticalChange)(position._1 + horisontalChange).addPlayer(this)
        position = (position._1 + horisontalChange, position._2 + verticalChange)
//        println( horisontalChange + " ja " +  verticalChange + " ja sijainti " + getPosition)
//        println(map.grid(position._2)(position._1).playerIn)
        }}
    }
    
    
    
  }
  
}