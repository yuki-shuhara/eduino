package processing.app;

public class CreatePanel{
  
  CreatePanel(){

  }
  public PanelTranslate create(String s){
    
    if(s.equals("Start")){
      return new StartPanel();
    }
   
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
    if(s.equals("Temperature")){
      return new TemperaturePanel();
    }
    if(s.equals("Lightsensor")){
      return new LightsensorPanel();
    }

//    return new NonPanel();
    return null;
  }
  
 
}