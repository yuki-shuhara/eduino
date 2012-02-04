package processing.app.eduino;


public class CreatePanel{
  private int px, py; 
  
  CreatePanel(){
    px = 10;
    py = 10;
  }
  
  public PanelTranslate create(String s, int x, int y){
	      
	      if(s.equals("Start")){
	        return new StartPanel(x, y);
	      }
	      if(s.equals("Loop")){
	        return new LoopPanel(x, y);
	      }
	      if(s.equals("Setseg")){
	        return new SetSegmentPanel(x, y);
	      }
	      if(s.equals("Led")){
	        return new LedPanel(x, y);
	      }
	      if(s.equals("Delay")){
	        return new DelayPanel(x, y);
	      }
	      if(s.equals("Switch")){
	        return new SwitchPanel(x, y);
	      }
	      if(s.equals("Temperature")){
	        return new TemperaturePanel(x, y);
	      }
	      if(s.equals("Lightsensor")){
	        return new LightsensorPanel(x, y);
	      }
	      if(s.equals("For")){
	        return new ForPanel(x, y);
	      }
	      if(s.equals("Seg")){
	        return new SegmentPanel(x, y);
	      }
	      return null;
	    }
  
  
  public PanelTranslate create(String s){
	  if(px > 200){
		  px = 5;
		  py += 20;
	  }
			      
	  if(py > 100){
		  px = 10;
		  py = 10;
	  }
	      
	      px += 20;
	      
	  return this.create(s, px, py);
  }
}