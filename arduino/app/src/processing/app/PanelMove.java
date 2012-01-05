package processing.app;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class PanelMove extends MouseAdapter{
    private int dx;
    private int dy;
    private int x=0; //パネルのｘ座標
    private int y=0; //パネルのy座標
    
    private PanelTranslate dummy;
    
    public void mouseDragged(MouseEvent e){
//      x = e.getXOnScreen() - dx;
//      y = e.getYOnScreen() - dy;
//      
//      dummy.setLocation(x, y);
//  
    }
    
     
    public void mousePressed(MouseEvent e){
//      /*内包されているかのif文*/
//      dummy = new JPanel();
//      dummy = (JPanel)e.getComponent();
//        
//      dx = e.getXOnScreen() - dummy.getX();
//      dy = e.getYOnScreen() - dummy.getY();
    }
    
    public void mouseReleased(MouseEvent e){
      
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
