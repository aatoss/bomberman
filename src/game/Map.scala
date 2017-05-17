package game

import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import scala.collection.mutable.Buffer
import java.util.{Timer, TimerTask}
import scala.util.control.Breaks._


class Map (val dimension: Int){
  
  val random = new Random
  
  val grid: Array[Array[Ground]] = Array.ofDim(dimension, dimension)
  
  private var playersInGame = scala.collection.mutable.HashMap[Int, Player]()
  
  private var bombsInField: Buffer[Bomb] = Buffer() 
  
  def prepareGameField = {
    for(x <- 0 until dimension){
      for(y <- 0 until dimension){
        grid(x)(y) = new Ground
      }
    }
    for(x <- 0 until dimension){
      for(y <- 0 until dimension){
        grid(x)(y).convertToDWall
      }
    }
    for(x <- 0 until dimension){
      grid(0)(x).convertToWall
      grid(dimension - 1)(x).convertToWall
      grid(x)(0).convertToWall
      grid(x)(dimension - 1).convertToWall
    }
    if(dimension == 15){
      for(x <- 2 to 12 by 2){
        for(y <- 2 to 12 by 2){
          grid(x)(y).convertToWall
        }
      }
      val values: Array[Int] = Array(1, 2, 12, 13)
      for(x <- 0 until 4){
        grid(1)(values(x)).convertToGround
        grid(13)(values(x)).convertToGround
      }
      grid(2)(1).convertToGround
      grid(2)(13).convertToGround
      grid(12)(1).convertToGround
      grid(12)(13).convertToGround
    }
    
    
  }
  
  def addPlayers(players: Buffer[(Int, Player)]) = {
    if(players.length == 1){
      playersInGame += players(0)
      grid(1)(1).addPlayer(players(0)._2)
    }else if(players.length == 2){
      playersInGame += players(0)
      playersInGame += players(1)
      grid(1)(1).addPlayer(players(0)._2)
      grid(13)(13).addPlayer(players(1)._2)
    }else if(players.length == 3){
      playersInGame += players(0)
      playersInGame += players(1)
      playersInGame += players(2)
      grid(1)(1).addPlayer(players(0)._2)
      grid(13)(13).addPlayer(players(1)._2)
      grid(1)(13).addPlayer(players(2)._2)
    }else if(players.length == 4){
      playersInGame += players(0)
      playersInGame += players(1)
      playersInGame += players(2)
      playersInGame += players(3)
      grid(1)(1).addPlayer(players(0)._2)
      grid(13)(13).addPlayer(players(1)._2)
      grid(1)(13).addPlayer(players(2)._2)
      grid(13)(1).addPlayer(players(3)._2)
    }
  }
  
  def removePlayer(player: Player) = {
    playersInGame.remove(player.number)
    grid(player.getPosition._2)(player.getPosition._1).removePlayer
  }
  
  def getPlayer(number: Int) = {
    if(playersInGame.contains(number)){
      val player = playersInGame(number)
      Option(player)
    }else None
  }
  
  def remainingPlayersInGame ={
    playersInGame
  }
  
  def numberOfPlayers = {
    playersInGame.size
  }
  
  def exploder(coordinates: (Int, Int), bomb: Bomb): Unit = {
    grid(coordinates._1)(coordinates._2).mainExplosion(bomb)
    radar(bomb.area, bomb.position, bomb)
    grid(coordinates._1)(coordinates._2).removeBomb
    bombsInField.remove(bombsInField.indexOf(bomb))
  }
  
  def radar(area: Int, coords: (Int,Int), bomb: Bomb) = {
    breakable {for(i <- 1 to area){
      var target = grid(coords._1)(coords._2 - i)
      if(target.getType == "DWall" || target.getType == "Wall"){  // Right
        target.addExploading(bomb)
        target.explosion
        break
      }else{
        target.addExploading(bomb)
        target.explosion
      }
    }}
    
    breakable {for(i <- 1 to area){
      var target = grid(coords._1)(coords._2 + i)
      if(target.getType == "DWall" || target.getType == "Wall"){  // Left
        target.addExploading(bomb)
        target.explosion
        break
      }else{
        target.addExploading(bomb)
        target.explosion
      }
    }}
    
    breakable {for(i <- 1 to area){
      var target = grid(coords._1 - i)(coords._2)
      if(target.getType == "DWall" || target.getType == "Wall"){  // Up
        target.addExploading(bomb)
        target.explosion
        break
      }else{
        target.addExploading(bomb)
        target.explosion
      }
    }}
    breakable {for(i <- 1 to area){
      var target = grid(coords._1 + i)(coords._2)
      if(target.getType == "DWall" || target.getType == "Wall"){  // Down
        target.addExploading(bomb)
        target.explosion
        break
      }else{
        target.addExploading(bomb)
        target.explosion
      }
    }} 
  }
  
  def countBombs(player: Int): Int = {
    var counter = 0
    for(x <- bombsInField){
      if(x.player.number == player){
        counter += 1
      }
    }
    return counter
  }
  
  def putBomb(coordinates: (Int, Int), bomb: Bomb){
    if (countBombs(bomb.player.number) < bomb.player.maxBombs){
      grid(coordinates._1)(coordinates._2).plantBomb(bomb)
      bombsInField.append(bomb)
      val timer = new Timer
      def delay(f: () => Unit, n: Long) = timer.schedule(new TimerTask() { def run = f() }, n)
      delay(() => exploder(coordinates, bomb), 3000L)
    }
  }
  
}