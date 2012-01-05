package processing.app;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;


public class CreatePanel{

  
  CreatePanel(){
   
  }
  public PanelTranslate create(String s){
    if(s.equals("Loop")){
      return new LoopPanel();
    }
    if(s.equals("Seg")){
      //new SegPanel();
    }
    if(s.equals("Led")){
      //new LedPanel();
    }
    if(s.equals("Delay")){
      //new DelayPanel();
    }
    if(s.equals("Switch")){
      //new SwitchPanel();
    }

    return new NonPanel();
  }
  
 
}