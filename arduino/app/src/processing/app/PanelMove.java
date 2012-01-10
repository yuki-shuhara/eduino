package processing.app;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PanelMove implements MouseListener, MouseMotionListener{
    private int dx;
    private int dy;
    private int x=0; //パネルのｘ座標
    private int y=0; //パネルのy座標
    
    WorkingSpace WorkingSpace;
    PanelTranslate GettingPanel;
    private boolean remove=false;
  
    PanelMove(WorkingSpace WorkingSpace){
      this.WorkingSpace = WorkingSpace;
    }
    
    public void mouseDragged(MouseEvent e){
        x = e.getXOnScreen() - dx;
        y = e.getYOnScreen() - dy;
        
        GettingPanel.setLocation(x, y);
        //System.out.println("x:"+x + "@y:"+y);
      
    }
    
     
    public void mousePressed(MouseEvent e){
//      System.out.println("pressed");
      GettingPanel = (PanelTranslate)e.getComponent();
      /*内包されているかのif文*/
        if(GettingPanel.getContains(e.getX(), e.getY())){
          dx = e.getXOnScreen() - GettingPanel.getX();
          dy = e.getYOnScreen() - GettingPanel.getY();
          
          WorkingSpace.remove(GettingPanel);
          WorkingSpace.CreaPanel.add(GettingPanel);
          
        }
    }
    
    public void mouseReleased(MouseEvent e){
//      System.out.println("released");
      if(x < -50|| y < -50){
        remove = true;
      }
      if(x < 0){
        x = 1;
      }
      if(y < 0){
        y = 1;
      }
      GettingPanel.setLocation(x, y);

      WorkingSpace.CreaPanel.remove(GettingPanel);
      if(remove){remove=false;}
      else{WorkingSpace.add(GettingPanel);}
      WorkingSpace.repaint();
      
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
