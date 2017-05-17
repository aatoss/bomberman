package game

import java.awt.Color
import java.util.{Timer, TimerTask}


class Ground {
  
  //Indicates the type of the ground. GetColor reacts then to the correct type
  private var typeOfGround = "Ground"
  
  private var canPlayerEnter: Boolean = true
  
  // Changes according if the player is in this piece of ground
  var player: Option[Player] = None
  
  // Indicates if the bomb is in this piece
  var bomb: Option[Bomb] = None
  
  // If is exploading this variable has the bomb that caused the explosion
  var exploading: Option[Bomb] = None
  
  def getType = typeOfGround
  
  def getColor: Color = {
    if(typeOfGround == "Exploading"){exploading.get.getExplosionColor}
      else{if(isBombIn){bomb.get.getColor}
        else{if (playerIn){return player.get.getColor}
          else {if(typeOfGround == "Wall"){return Color.YELLOW}
            else{if(typeOfGround == "DWall"){return Color.DARK_GRAY}
              else {return Color.GRAY}
            }
          }
        }
      }
  }
  
  def canConvert: Boolean = {
    typeOfGround != "Wall"
  }
  
  def convertToDWall = {
    if (canConvert){
      typeOfGround = "DWall"
      canPlayerEnter = false
    }
  }
  
  def convertToGround = {
    typeOfGround = "Ground"
    canPlayerEnter = true
  }
  
  def typeChanger: Unit = {
    typeOfGround = "Ground"
    exploading = None
  }

  def explosion = {
    if (canConvert){
      canPlayerEnter = true
      typeOfGround = "Exploading"
      val timer = new Timer
      def delay(f: () => Unit, n: Long) = timer.schedule(new TimerTask() { def run = f() }, n)
      delay(() => typeChanger, 2000L)
    }
    
  }
  
  def mainExplosion(bomb: Bomb): Unit = {
    if (canConvert){
      exploading = Some(bomb)
      canPlayerEnter = true
      typeOfGround = "Exploading"
      val timer = new Timer
      def delay(f: () => Unit, n: Long) = timer.schedule(new TimerTask() { def run = f() }, n)
      delay(() => typeChanger, 2000L) 
    }
  }
  
  def addExploading(bomb: Bomb) = {
    exploading = Some(bomb)
  }
  
  def convertToWall = {
    typeOfGround = "Wall"
    canPlayerEnter = false
  }
  
  def addPlayer(playerToAdd: Player) = if(canEnter) player = Some(playerToAdd)
  
  def removePlayer = player = None
  
  def playerIn: Boolean = player.isDefined
  
  def canEnter: Boolean= {
    if (!canPlayerEnter){false}
      else{if (playerIn){false}
        else{if (isBombIn){false}
          else{true
          }
         }
       }
  }
  
  def plantBomb(bombToPlant: Bomb) = if(!isBombIn) bomb = Some(bombToPlant)
  
  def isBombIn: Boolean = bomb.isDefined
  
  def removeBomb = bomb = None
  
}

