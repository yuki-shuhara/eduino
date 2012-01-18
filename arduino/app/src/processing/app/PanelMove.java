package processing.app;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PanelMove implements MouseListener, MouseMotionListener{
    private int dx;
    private int dy;
    private int x=0; //パネルのｘ座標
    private int y=0; //パネルのy座標
    
    private int RemoveLine = -50;
    private int checkLine = 0;
    private boolean remove;  
    private boolean press;
    
    WorkingSpace WorkingSpace;
    PanelTranslate GettingPanel;
    PanelTranslate PracedPanel;
    
  
    PanelMove(WorkingSpace WorkingSpace){
      this.WorkingSpace = WorkingSpace;
      remove = false;
      press = false;
    }
    
    int a=0;
    public void mouseDragged(MouseEvent e){
      if(press){
        x = e.getXOnScreen() - dx;
        y = e.getYOnScreen() - dy;
        
        System.out.println("MouseDragged:"+a++);
        GettingPanel.setLocation(x, y);
      }
    }
    
     
    public void mousePressed(MouseEvent e){

      System.out.println("pressed");
      GettingPanel = (PanelTranslate)e.getComponent();
      //System.out.println(GettingPanel.code());
      /*内包されているかのif文*/
        if(GettingPanel.inLine(e.getX(), e.getY())){
          press = true;
          dx = e.getXOnScreen() - GettingPanel.getX();
          dy = e.getYOnScreen() - GettingPanel.getY();
          
          WorkingSpace.moveToFront(GettingPanel);
          GettingPanel.setbeforePanelTranslate(null);
          
          PanelTranslate t = GettingPanel;
          int c=0;
          while(t != null){
            c++;
            System.out.println(c+"\n" +t +"\n************************\n");
            System.out.println("Next:"+t.getNextPanelTranslate()+"\nBefore:"+t.beforePanelTranslate+"\n");
            t = t.getNextPanelTranslate();
          }
          
        }
    }
    
    public void mouseReleased(MouseEvent e){
      press = false;
//      System.out.println("released");
      if(x < RemoveLine|| y < RemoveLine){
        remove = true;
      }
      if(x < 0){
        x = 1;
        GettingPanel.setLocation(x, y);
      }
      if(y < 0){
        y = 1;
        GettingPanel.setLocation(x, y);
      }

        


      if(remove){
        remove=false;
        WorkingSpace.removePanel(GettingPanel);
      }
      else{
        PanelTranslate PT = WorkingSpace.checkOverlap(GettingPanel);
        System.out.println("HitPanel:"+PT);
        if(PT != null){
          PT.setNextPanelTranslate(GettingPanel);
        }

      }
        WorkingSpace.repaint();
    }
    
    public void mouseMoved(MouseEvent e) {
      //mouseEntered(e);
    }
    
    public void mouseClicked(MouseEvent e){
      //System.out.println("Click");
    }
    
    public void mouseEntered(MouseEvent e){
     //System.out.println("enter");
    }
  
    public void mouseExited(MouseEvent e){
    //System.out.println("exit");
    }
   
}
