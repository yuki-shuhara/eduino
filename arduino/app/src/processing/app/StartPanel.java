package processing.app;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Polygon;

import javax.swing.JLabel;


public class StartPanel extends PanelTranslate{
  
  static int HEIGHT = 110; //=topheight+barheight+bottomheight
  static int WIDTH = 180;
  static int TOP_HEIGHT = 50;
  static int BAR_HEIGHT = 60;
  static int BAR_WIDTH = 20;
  static int BOTTOM_HEIGHT = 50;
//  static Color color = Color.orange;
  static long blockid;

  JLabel start, end;


  StartPanel(){
    super(WIDTH, HEIGHT, Color.red, Color.red, BAR_WIDTH, TOP_HEIGHT);
    setPolygon();
    addedParts();
  }
  public void setHeight(){
    HEIGHT = TOP_HEIGHT+BAR_HEIGHT+BOTTOM_HEIGHT;
  }
  @Override
  public void setbeforePanelTranslate(PanelTranslate beforePanel){
    //super.setbeforePanelTranslate(beforePanel);
    //開始・終了パネルの頭と後ろには何もくっつけさせないようにする
  }
  
  @Override
  public void setNextPanelTranslate(PanelTranslate nextPanel){
    super.setNextPanelTranslate(nextPanel);
    
    
  }
  @Override
  public void setLocation(int x, int y){
    super.setLocation(x, y);
  }

   void addedParts(){
      start = new JLabel("プログラム開始");
      end = new JLabel("プログラム終了");
      super.add(start);
      super.add(end);
   }
   
   protected void paintComponent(Graphics g){
     super.paintComponent(g);
     
     int sum = 0;
     PanelTranslate t = super.getNextPanelTranslate();
     while(t != null){
       sum = sum + t.height;
       t = t.getNextPanelTranslate();
     }
     if(sum < 1) sum = BAR_HEIGHT;
   BAR_HEIGHT = sum;
   setHeight();
   super.setSize(WIDTH, HEIGHT);
   this.setPolygon();
   
     start.setBounds(WIDTH/4, TOP_HEIGHT/2-10, WIDTH/2, 20);
     end.setBounds(WIDTH/4, TOP_HEIGHT+BAR_HEIGHT+BOTTOM_HEIGHT/2-10, WIDTH/2, 20);
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
   
     source = source + "exit(1);\n}";
   
   return source;
  }
}
