package processing.app;

public class CreatePanel{
  
  CreatePanel(){

  }
  public PanelTranslate create(String s){
    if(s.equals("Loop")){
      return new LoopPanel();
    }
    if(s.equals("Seg")){
      return new SegPanel();
    }
    if(s.equals("Led")){
      return new LedPanel();
    }
    if(s.equals("Delay")){
      return new DelayPanel();
    }
    if(s.equals("Switch")){
      return new SwitchPanel();
    }

//    return new NonPanel();
    return null;
  }
  
 
}