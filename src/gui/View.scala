package gui

import game._
import java.awt.event.ActionEvent
import java.io.File

import scala.swing._

import javax.sound.sampled._
import javax.swing.AbstractAction
import javax.swing.Timer
import javax.swing.UIManager
import scala.collection.mutable.Buffer
import scala.swing.event.ButtonClicked
import scala.swing.event.KeyPressed
import scala.swing.event.Key


object View extends SimpleSwingApplication {
  
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
  
  println("Give the number of players\nMaximum of 4\n")
  
  var numberOfPlayers = readInt()
  
  while(numberOfPlayers < 1 || numberOfPlayers > 4){
    println("Give a valid number of players\nMaximum of 4\n")  
    numberOfPlayers = readInt()
  }
  
  val game = new MainWindow(new Map(15))
  
  val startWindow = new Frame
  
  val options = List(1, 2, 3, 4)
  
  val quitButton = new Button("Quit")
  
  val onePlayerButton = new Button("1 Player Game")
  
  val twoPlayerButton = new Button("2 Player Game")
  
  val endWindow = new Frame
      
  val player1   = new Player(1, game.map, false)
  val player2   = new Player(2, game.map, false)
  val player3   = new Player(3, game.map, false)
  val player4   = new Player(4, game.map, false)
  val player2AI = new AI(2, game.map)

  
  def makeGame(numOfPlayers : Int) {
    if(numOfPlayers == 1){
      game.map.addPlayers(Buffer((1, player1), (2, player2AI)))
    }else if(numOfPlayers == 2){
      game.map.addPlayers(Buffer((1, player1), (2, player2)))
    }else if(numOfPlayers == 3){
      game.map.addPlayers(Buffer((1, player1), (2, player2), (3, player3)))
    }else {
      game.map.addPlayers(Buffer((1, player1), (2, player2), (3, player3), (4, player4)))
    }
  }
  
  game.map.prepareGameField
  
  makeGame(numberOfPlayers)
  
  val gamePlayer1 = game.map.getPlayer(1)
  val gamePlayer2 = game.map.getPlayer(2)
  val gamePlayer3 = game.map.getPlayer(3)
  val gamePlayer4 = game.map.getPlayer(4)
  val player1livesLabel = new Label("Player 1 lives: 3")
  val player2livesLabel = new Label("Player 2 lives: 3")
  val player3livesLabel = new Label("Player 3 lives: 3")
  val player4livesLabel = new Label("Player 4 lives: 3")
  
  
  val timer: Timer = new Timer(170, new AbstractAction(){
      def actionPerformed(e : ActionEvent){
        
        val player1lives = if(game.map.getPlayer(1).isEmpty) 0 else game.map.getPlayer(1).get.showLives
        
        val player2lives = if(game.map.getPlayer(2).isEmpty) 0 else game.map.getPlayer(2).get.showLives
        
        var player3lives = 3
        
        var player4lives = 3
        
        if(numberOfPlayers == 3){
          player3lives = if(game.map.getPlayer(3).isEmpty) 0 else game.map.getPlayer(3).get.showLives
        }
        
        if(numberOfPlayers == 4){
          player3lives = if(game.map.getPlayer(3).isEmpty) 0 else game.map.getPlayer(3).get.showLives
          player4lives = if(game.map.getPlayer(4).isEmpty) 0 else game.map.getPlayer(4).get.showLives
        }
        

        player1livesLabel.text = ("Player 1 lives: " +  player1lives)
        
        player2livesLabel.text = ("Player 2 lives: " +  player2lives)
        
        if(numberOfPlayers == 3){
          player3livesLabel.text = ("Player 3 lives: " +  player3lives)
        }
        
        if(numberOfPlayers == 4){
          player3livesLabel.text = ("Player 3 lives: " +  player3lives)
          player4livesLabel.text = ("Player 4 lives: " +  player4lives)
        }
        
        
        
        
        if(game.map.numberOfPlayers == 1){
          timer.stop()
          Dialog.showMessage(endWindow, "Game over\nPlayer " + game.map.remainingPlayersInGame.head._1 + " wins")
          quit()
        }else if(game.map.numberOfPlayers == 0){
          timer.stop()
          Dialog.showMessage(endWindow, "Game over\nGame ended in draw")
          quit()
        }
        if(!game.map.getPlayer(2).isEmpty && game.map.getPlayer(2).get.isAI){
          game.map.getPlayer(2).get.move(Direction.Up)
        }
        game.repaint()
        
        
      }
      
    })
  timer.start
  
  val hitTimer: Timer = new Timer(10, new AbstractAction(){
      def actionPerformed(e : ActionEvent){
        if(game.map.grid(gamePlayer1.get.getPosition._2)(gamePlayer1.get.getPosition._1).getType == "Exploading" && !gamePlayer1.get.isStun){
          gamePlayer1.get.hit
        }
        if(game.map.grid(gamePlayer2.get.getPosition._2)(gamePlayer2.get.getPosition._1).getType == "Exploading" && !gamePlayer2.get.isStun){
          gamePlayer2.get.hit
        }
        if(!gamePlayer3.isEmpty && game.map.grid(gamePlayer3.get.getPosition._2)(gamePlayer3.get.getPosition._1).getType == "Exploading" && !gamePlayer3.get.isStun){
          gamePlayer3.get.hit
        }
        if(!gamePlayer4.isEmpty && game.map.grid(gamePlayer4.get.getPosition._2)(gamePlayer4.get.getPosition._1).getType == "Exploading" && !gamePlayer4.get.isStun){
          gamePlayer4.get.hit
        }
      }
    })
  
  hitTimer.start
  val buttonBox = new BoxPanel(Orientation.Vertical)
  buttonBox.contents += player1livesLabel
  buttonBox.contents += player2livesLabel
  if(this.numberOfPlayers == 3){
    buttonBox.contents += player3livesLabel
  }
  if(numberOfPlayers == 4){
    buttonBox.contents += player3livesLabel
    buttonBox.contents += player4livesLabel
  }
  buttonBox.contents += quitButton
  
  val masterBox = new BoxPanel(Orientation.Horizontal)
  masterBox.contents += game
  masterBox.contents += buttonBox
    
  def top = new MainFrame{
    
    listenTo(quitButton, onePlayerButton, twoPlayerButton)
    
    title = "Bomberman"
    
    preferredSize = new Dimension(700, 680)
    
    contents = masterBox
    
    
    
    resizable = true
   
    reactions += {
      case ButtonClicked(source) => 
        if(source == quitButton ){
          quit()
        }
            
    }
    
   
    val audioIn = AudioSystem.getAudioInputStream(new File("audio/Music.wav"))
    val clip = AudioSystem.getClip
    clip.open(audioIn)
    clip.loop(Int.MaxValue)
  }
}