package processing.app;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class PanelMove extends MouseAdapter{
    private int dx;
    private int dy;
    private int x=0; //パネルのｘ座標
    private int y=0; //パネルのy座標
    
    PanelTranslate PanelTranslate;
    boolean press=false;
    
    PanelMove(PanelTranslate paneltranslate){
      this.PanelTranslate = paneltranslate;
    }
    
    public void mouseDragged(MouseEvent e){
      if(press){
        x = e.getXOnScreen() - dx;
        y = e.getYOnScreen() - dy;
      
        PanelTranslate.setLocation(x, y);
      }
    }
    
     
    public void mousePressed(MouseEvent e){
//      System.out.println("pressed");
      /*内包されているかのif文*/
        if(PanelTranslate.getContains(e.getX(), e.getY())){
          press = true;
          dx = e.getXOnScreen() - PanelTranslate.getX();
          dy = e.getYOnScreen() - PanelTranslate.getY();
        }
    }
    
    public void mouseReleased(MouseEvent e){
//      System.out.println("released");
      press = false;
    }
    
    public void mouseMoved(MouseEvent e) {
      //mouseEntered(e);
    }
    
    public void mouseClicked(MouseEvent e){
      //System.out.println("Click");
    }
    
    public void mouseEntered(MouseEvent e){
     
    }
  
    public void mouseExited(MouseEvent e){
    
    }
   
}
