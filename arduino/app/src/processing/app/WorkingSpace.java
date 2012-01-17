package processing.app;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class WorkingSpace extends JLayeredPane{
  
  //public JPanel CreaPanel;
  
  private PanelTranslate placedPanel[];
  private int MAX_PANEL = 1000;
  private int count=1;
  private boolean full;
  
  WorkingSpace(){
    this.setLayout(null);
    reset();
    full = true;
    //this.addMouseListener(new WorkingspaceListener());
//    initCreaPanel();
//    this.add(CreaPanel, JLayeredPane.DRAG_LAYER);
  }
  
  public void reset(){
    this.removeAll();
    count = 1;
    placedPanel = new PanelTranslate[MAX_PANEL];
  }
  
  public void setStartPanel(PanelTranslate startpanel, PanelMove move){
    startpanel.addMouseListener(move);
    startpanel.addMouseMotionListener(move);
    
    placedPanel[0] = startpanel;
    placedPanel[0].setLocation(150,150);
    this.add(placedPanel[0]);
  }
  
  public void PlacedPanel(PanelTranslate pane){
//    super.add(pane);
    if(!full){
      for(int i=0; i<count;i++){
        if(placedPanel[i]==null){
           placedPanel[i] = pane;
           return;
        }
      }
      full = true;
    }
      placedPanel[count] = pane;
   // System.out.println("name:"+placedPanel[count]+"id:"+count);
      count++;
    
  }
  
  public void removePanel(PanelTranslate pane){
    full = false;
    for(int i=0; i < count; i++){
      if(placedPanel[i] == pane){
        if(placedPanel[i].getNextPanelTranslate() != null){
          removePanel(placedPanel[i].getNextPanelTranslate());
        }
        else{
          placedPanel[i] = null;
        }
      }
    }
    this.remove(pane);
  }
  
  public PanelTranslate checkOverlap(PanelTranslate p){
    for(int i=0; i < count; i++){
      
      //System.out.println("placedPanel["+i+"]"+placedPanel[i]);
      
      if(placedPanel[i] != null){
        if(placedPanel[i] != p){
          if(placedPanel[i].getContains(p.x, p.y)) return placedPanel[i];
        }
      }
    }
    for(int i=0; i < count; i++){
      if(placedPanel[i] != null){
        if(placedPanel[i] != p){
          if(placedPanel[i].getContains(p.x, p.y + p.height)) return placedPanel[i];  
        }
      }
    }
//  if(placedPanel[i].getContains(p.width+checkLine, p.y-checkLine)) return placedPanel[i];
//  if(placedPanel[i].getContains(p.width+checkLine, p.height+checkLine)) return placedPanel[i];
        return null;
  }
  
  protected PanelTranslate getLoop(){
    return placedPanel[0];
    //本番はループタイルを探す
  }
  
//  private void initCreaPanel(){
//    CreaPanel = new JPanel();
//    CreaPanel.setOpaque(false);
//    CreaPanel.setLayout(null);
//    CreaPanel.setBounds(0,0, Short.MAX_VALUE, Short.MAX_VALUE);
//
//  }
}
