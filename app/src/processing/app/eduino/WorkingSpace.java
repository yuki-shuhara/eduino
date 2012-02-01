package processing.app.eduino;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class WorkingSpace extends JLayeredPane{
  
  
  private PanelTranslate placedPanel[];
  private int MAX_PANEL = 1000;
  private int count=1;
  
  WorkingSpace(){
    this.setLayout(null);
    reset();
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
    this.add(placedPanel[0]);
  }
  
  public void PlacedPanel(PanelTranslate pane){
    if(count>MAX_PANEL-1){
      System.out.println("これ以上タイルを生成できません。ウィンドウを閉じて新しくやり直してください。");
      return;
    }
      placedPanel[count] = pane;
   // System.out.println("name:"+placedPanel[count]+"id:"+count);
      this.add(placedPanel[count++]);
  }
  
  public void removePanel(PanelTranslate pane){
    for(int i=0; i < count; i++){
      if(placedPanel[i] == pane){
        
//        if(placedPanel[i].getBlockId() == 1 || placedPanel[i].getBlockId() == 2 || placedPanel[i].getBlockId() == 5){
//          
//        }
        
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
  
  public void checkOverlap(PanelTranslate p){
    if(p.getBlockId() == 0) return;//StartPanelは他のパネルに組み込まれてはならない
    
    for(int i=0; i < count; i++){     
      if(placedPanel[i] != null && placedPanel[i] != p){
        if(placedPanel[i].getnextSetis() && placedPanel[i].getContain(p.getX(), p.getY())){
          placedPanel[i].setPanelTranslate(p);
          return;
        }
      }
    }
    
    for(int i=0; i < count; i++){     
      if(placedPanel[i] != null && placedPanel[i] != p){
        if(placedPanel[i].getnextSetis() && placedPanel[i].getContain(p.getX(), p.getY()+p.getHeight())){
          placedPanel[i].setPanelTranslate(p);
          return;
        }
      }
    }
//  if(placedPanel[i].getContains(p.width+checkLine, p.y-checkLine)) return placedPanel[i];
//  if(placedPanel[i].getContains(p.width+checkLine, p.height+checkLine)) return placedPanel[i];

  }
  
  protected boolean checkSPanel(){
    for(int i=0; i<count; i++){
      if(placedPanel[i] != null){
        if(placedPanel[i].getBlockId() == 0) return true;
      }
    }
    return false;
  }
  
  protected PanelTranslate getLoop(){
    for(int i=0; i<count; i++){
      if(placedPanel[i] != null){
        if(placedPanel[i].getBlockId() == 0) return placedPanel[i];
      }
    }
    return null;
  }

}
