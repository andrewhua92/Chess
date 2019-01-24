import javax.swing.*;
import java.awt.event.*;
public class ChessListener implements MouseListener
{
   private ChessGUI gui;
   private Chess game;
   public ChessListener (Chess game, ChessGUI gui) 
   {
      this.game = game;
      this.gui = gui;
      gui.addListener (this);
   }
   
   public void mouseClicked (MouseEvent event) 
   {
      JLabel label = (JLabel) event.getComponent ();
      int row = gui.rowDetect(label);
      int column = gui.colDetect (label);
      game.play(row, column);     
   }
   
   public void mousePressed (MouseEvent event) {
   }
   
   public void mouseReleased (MouseEvent event) {
   }
   
   
   public void mouseEntered (MouseEvent event) {
   }
   
   public void mouseExited (MouseEvent event) {
   }

}