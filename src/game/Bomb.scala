package game

import java.awt.Color

class Bomb(val player: Player,val position: (Int, Int), time: Int, val area: Int) {
  
  // Returns the color of the bomb witch is always black
  def getColor: Color = Color.BLACK
  
  // Returns the player matched color of the explosion
  def getExplosionColor: Color = {
    if(player.number == 1){
      Color.CYAN
    } else if(player.number == 2){
      Color.MAGENTA
    }else if(player.number == 3){
      new Color(0, 153, 0)
    }else new Color(178, 102, 255)
  }
}