package processing.app;



import java.awt.Color;
import java.awt.Graphics;

import java.awt.Polygon;

import javax.swing.JLabel;

public class LoopPanel extends PanelTranslate{
  
  static int HEIGHT = 110; //=topheight+barheight+bottomheight
  static int WIDTH = 150;
  static int TOP_HEIGHT = 50;
  static int BAR_HEIGHT = 50;
  static int BAR_WIDTH = 20;
  static int BOTTOM_HEIGHT = 10;
//  static Color color = Color.orange;
  static long blockid;
  
  
  
  JLabel label;

  
//  LoopPanel(){
//    super(blockid);
//    
//  }
  LoopPanel(){
    super(WIDTH, HEIGHT, Color.orange, Color.yellow, BAR_WIDTH, TOP_HEIGHT);
    setPolygon();
    addedParts();
  }
  public void setHeight(){
    HEIGHT = TOP_HEIGHT+BAR_HEIGHT+BOTTOM_HEIGHT;
  }
  @Override
  public void setbeforePanelTranslate(PanelTranslate beforePanel){
    //super.setbeforePanelTranslate(beforePanel);
  }
  
  @Override
  public void setNextPanelTranslate(PanelTranslate nextPanel){
    super.setNextPanelTranslate(nextPanel);
    
    
  }
  

   void addedParts(){
      label = new JLabel("繰り返す");
      super.add(label);
   }
   
   protected void paintComponent(Graphics g){
     super.paintComponent(g);
     
     int sum = 0;
     PanelTranslate t = super.getNextPanelTranslate();
     while(t != null){
       sum = sum + t.height;
       t = t.getNextPanelTranslate();
     }
     if(sum < 50) sum = 50;
   BAR_HEIGHT = sum;
   setHeight();
   super.setSize(WIDTH, HEIGHT);
   this.setPolygon();
   
     label.setBounds(WIDTH/3, TOP_HEIGHT/2-10, WIDTH/3, 20);
   }
  

  public void setPolygon(){
    int Xarray[] = {0, WIDTH, WIDTH, BAR_WIDTH, BAR_WIDTH, WIDTH, WIDTH, 0};
    int Yarray[] = {0, 0, TOP_HEIGHT, TOP_HEIGHT, TOP_HEIGHT+BAR_HEIGHT, TOP_HEIGHT+BAR_HEIGHT, HEIGHT, HEIGHT};
    Polygon polygon = new Polygon(Xarray, Yarray, Xarray.length);
    super.polygon = polygon;
    super.setOutLine();
    
  }
  
  public String code(){
    
   String source = "void loop(){\n";
   
   PanelTranslate panel = super.getNextPanelTranslate();
   while(panel != null){
     System.out.println(source);
     source = source + panel.code();
     panel = panel.getNextPanelTranslate();
   }
   
     source = source + "}";
   
   return source;
  }
}