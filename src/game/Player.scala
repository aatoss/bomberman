package game

import java.awt.Color
import java.util.{Timer, TimerTask}


class Player(val number: Int, val map: Map, val isAI: Boolean){
  
  private var lives = 3
  
  //Returns the color of each player
  def getColor: Color = {
    if(number == 1){
      Color.BLUE
    }else if(number == 2){
       Color.RED
    }else if(number == 3){
      Color.GREEN
    }else{
      new Color(102, 0, 204)
    }
  }
  
  private var explosionArea = 2
  
  var maxBombs: Int = 1
  
  // Returns the start position of each player
  def startPosition: (Int, Int) = {
    if(number == 1){
      (1, 1)
    } else if(number == 2){
      (13, 13)
    }else if(number == 3){
      (13, 1)
    }else (1, 13)
  }
  
  private var position = startPosition
  
  def getPosition = position
  
  def showLives = {
    lives
  }
  
  //Handels the deleting of the player at the current space and adds player to the target position. Also updates position
  def move(direction: Direction.Value): Unit = {
    val horisontalChange = Direction.getXChange(direction)
    val verticalChange = Direction.getYChange(direction)
    

    if(map.grid(position._2 + verticalChange)(position._1 + horisontalChange).canEnter){
      map.grid(position._2)(position._1).removePlayer
      map.grid(position._2 + verticalChange)(position._1 + horisontalChange).addPlayer(this)
      position = (position._1 + horisontalChange, position._2 + verticalChange)
//      println( horisontalChange + " ja " +  verticalChange + " ja sijainti " + getPosition) // for debugging purposes
//      println(map.grid(position._2)(position._1).playerIn)
        }
  }
  
  def dropBomb = {
    map.putBomb((getPosition._2, getPosition._1), new Bomb(this, (getPosition._2, getPosition._1), 2, explosionArea))
  }

  //Is called when player is hit with explosion. Also activates stun
  def hit = {
    //println("Hit called") //for debugging purposes
    if (lives != 1) lives -= 1 else death
    stun = true
    val timer = new Timer
    def delay(f: () => Unit, n: Long) = timer.schedule(new TimerTask() { def run = f() }, n)
      delay(() => stun = false, 2000L)
  }
  
  private var stun = false
  
  def isStun = stun
  
  def death = {
    map.removePlayer(this)
  }
  
  
}