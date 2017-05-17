package tests

import game._
import game.Direction
import org.junit.Test
import org.junit.Assert._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.Assertions._
import scala.collection.mutable.Buffer
import java.awt.Color

class UnitTests extends FlatSpec with Matchers {
  
  "The map should contain 15 arrays" should "When creating new map" in  {
    val map = new Map(15)
    assert(map.grid.length == 15, "had differend number of rows")
  }
  
  "The map"  should "contain Wall in positon (0, 0) and Ground in (1, 1) when calling method prepare game field" in {
     val map = new Map(15)
     map.prepareGameField
     assert(map.grid(0)(0).getType == "Wall" && map.grid(1)(1).getType == "Ground", "Type of ground was wrong in either position")
  }
  
  "The map"  should "have player 1 in position (1, 1) and contain only one player" in {
     val map = new Map(15)
     map.prepareGameField
     map.addPlayers(Buffer((1, new Player(1, map, false))))
     assert(map.numberOfPlayers == 1 && map.grid(1)(1).playerIn, "No players detected")
  }
  
  "The player"  should "have moved into position (2, 1) when calling players move method" in {
     val map = new Map(15)
     map.prepareGameField
     map.addPlayers(Buffer((1, new Player(1, map, false))))
     map.getPlayer(1).get.move(Direction.Down)
     assert(map.grid(2)(1).playerIn, "No players detected")
  }
  
  "There"  should "be four players creating 4 player game" in {
     val map = new Map(15)
     map.prepareGameField
     map.addPlayers(Buffer((1, new Player(1, map, false)), (2, new Player(2, map, false)), (3, new Player(3, map, false)), (4, new Player(4, map, false))))
     assert(map.numberOfPlayers == 4, "Wrong number of players")
  }
  
    "There"  should "be four players with correct colors when creating 4 player game" in {
     val map = new Map(15)
     map.prepareGameField
     map.addPlayers(Buffer((1, new Player(1, map, false)), (2, new Player(2, map, false)), (3, new Player(3, map, false)), (4, new Player(4, map, false))))
     val colorComparison = map.getPlayer(1).get.getColor == Color.BLUE && map.getPlayer(2).get.getColor == Color.RED && map.getPlayer(3).get.getColor == Color.GREEN && map.getPlayer(4).get.getColor == new Color(102, 0, 204)
     assert(colorComparison, "Some player has wrong color")
  }
  
}