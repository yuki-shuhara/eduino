package processing.app;

public class CreatePanel{
  private int x, y; 
  
  CreatePanel(){
    y = 10;
    x = 5;
  }
  public PanelTranslate create(String s){
    
    if(x > 200){
      x = 5;
      y += 10;
    }
    
    if(y > 100){
      x = 5;
      y = 5;
    }
    
    x += 15;
    
    if(s.equals("Start")){
      return new StartPanel(x, y);
    }
   
    if(s.equals("Loop")){
      return new LoopPanel(x, y);
    }
    if(s.equals("Seg")){
      return new SegPanel(x, y);
    }
    if(s.equals("Led")){
      return new LedPanel(x, y);
    }
    if(s.equals("Delay")){
      return new DelayPanel(x, y);
    }
    if(s.equals("Switch")){
      //return new SwitchPanel(x, y);
    }
    if(s.equals("Temperature")){
      //return new TemperaturePanel(x, y);
    }
    if(s.equals("Lightsensor")){
      //return new LightsensorPanel(x, y);
    }

    return null;
  }
  
 
}