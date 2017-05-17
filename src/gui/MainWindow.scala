package gui

import java.awt.Graphics2D

import scala.swing.GridPanel
import scala.swing.event.KeyPressed
import scala.swing.event.Key

import game._

class MainWindow (val map: Map) extends GridPanel(map.dimension, map.dimension){
  
  override def paintComponent(g: Graphics2D) = {
    for(i <- 0 until map.dimension; j <- 0 until map.dimension){
      g.setColor(map.grid(i)(j).getColor) 
      g.fillRect(j*41, i*41, 40, 40)
    }   
  }
  
  this.focusable = true
  this.requestFocus()
  this.listenTo(keys)
  
  this.reactions += {
    case KeyPressed(_, Key.W, _, _) => {if(!map.getPlayer(1).isEmpty){
                                          map.getPlayer(1).get.move(Direction.Up)
                                          this.repaint()
                                        }
                                        //println("w pressed") // for depugging purposes
    }
    case KeyPressed(_, Key.A, _, _) => {if(!map.getPlayer(1).isEmpty){
                                          map.getPlayer(1).get.move(Direction.Left)
                                          this.repaint()
                                        }
                                        //println("a pressed")
                                        }
    case KeyPressed(_, Key.S, _, _) => {if(!map.getPlayer(1).isEmpty){
                                          map.getPlayer(1).get.move(Direction.Down)
                                          this.repaint()
                                        }
                                        //println("s pressed")
                                        }
    case KeyPressed(_, Key.D, _, _) => {if(!map.getPlayer(1).isEmpty){
                                          map.getPlayer(1).get.move(Direction.Right)
                                          this.repaint()
                                        }
                                        //println("d pressed")
                                        }
    case KeyPressed(_, Key.Up, _, _) => {if(!map.getPlayer(2).isEmpty){
                                          map.getPlayer(2).get.move(Direction.Up)
                                          this.repaint()
                                         }
                                        //println("up pressed")
                                        }
    case KeyPressed(_, Key.Left, _, _) => {if(!map.getPlayer(2).isEmpty){
                                            map.getPlayer(2).get.move(Direction.Left)
                                            this.repaint()
                                          }
                                        //println("left pressed")
                                        }
    case KeyPressed(_, Key.Down, _, _) => {if(!map.getPlayer(2).isEmpty){
                                            map.getPlayer(2).get.move(Direction.Down)
                                            this.repaint()
                                          }
                                        //println("down pressed")
                                        }
    case KeyPressed(_, Key.Right, _, _) => {if(!map.getPlayer(2).isEmpty){
                                              map.getPlayer(2).get.move(Direction.Right)
                                              this.repaint()
                                           }
                                        //println("right pressed")
                                        }
    case KeyPressed(_, Key.Y, _, _) => {if(!map.getPlayer(3).isEmpty){
                                          map.getPlayer(3).get.move(Direction.Up)
                                          this.repaint()
                                        }
                                        //println("y pressed")
                                        }
    case KeyPressed(_, Key.G, _, _) => {if(!map.getPlayer(3).isEmpty){
                                          map.getPlayer(3).get.move(Direction.Left)
                                          this.repaint()
                                        }
                                        //println("g pressed")
                                        }
    case KeyPressed(_, Key.H, _, _) => {if(!map.getPlayer(3).isEmpty){
                                          map.getPlayer(3).get.move(Direction.Down)
                                          this.repaint()
                                        }
                                        //println("h pressed")
                                        }
    case KeyPressed(_, Key.J, _, _) => {if(!map.getPlayer(3).isEmpty){
                                          map.getPlayer(3).get.move(Direction.Right)
                                          this.repaint()
                                        }
                                        //println("j pressed")
                                        }
    case KeyPressed(_, Key.Numpad8, _, _) => {if(!map.getPlayer(4).isEmpty){
                                          map.getPlayer(4).get.move(Direction.Up)
                                          this.repaint()
                                         }
                                        //println("numpad8 pressed")
                                        }
    case KeyPressed(_, Key.Numpad4, _, _) => {if(!map.getPlayer(4).isEmpty){
                                            map.getPlayer(4).get.move(Direction.Left)
                                            this.repaint()
                                          }
                                        //println("numpad4 pressed")
                                        }
    case KeyPressed(_, Key.Numpad5, _, _) => {if(!map.getPlayer(4).isEmpty){
                                            map.getPlayer(4).get.move(Direction.Down)
                                            this.repaint()
                                          }
                                        //println("numpad5 pressed")
                                        }
    case KeyPressed(_, Key.Numpad6, _, _) => {if(!map.getPlayer(4).isEmpty){
                                              map.getPlayer(4).get.move(Direction.Right)
                                              this.repaint()
                                           }
                                        //println("right pressed")
                                        }
    case KeyPressed(_, Key.Space, _, _) => {if(!map.getPlayer(1).isEmpty){
                                              map.getPlayer(1).get.dropBomb
                                              this.repaint()
                                           }
                                        //println("space pressed")
                                        }
    case KeyPressed(_, Key.Enter, _, _) => {if(!map.getPlayer(2).isEmpty){
                                              map.getPlayer(2).get.dropBomb
                                              this.repaint()
                                           }
                                        //println("enter pressed")
                                        }
    case KeyPressed(_, Key.U, _, _) => {if(!map.getPlayer(3).isEmpty){
                                              map.getPlayer(3).get.dropBomb
                                              this.repaint()
                                           }
                                        //println("u pressed")
                                        } 
    case KeyPressed(_, Key.Numpad0, _, _) => {if(!map.getPlayer(4).isEmpty){
                                              map.getPlayer(4).get.dropBomb
                                              this.repaint()
                                           }
                                        //println("numpad0 pressed")
                                        } 
  }  
    
  
  
}