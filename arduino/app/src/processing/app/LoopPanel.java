package processing.app;



import java.awt.Color;
import java.awt.Graphics;

import java.awt.Polygon;

import javax.swing.JLabel;

public class LoopPanel extends PanelTranslate{
  
  static int HEIGHT = 180;
  static int WIDTH = 150;
  static int TOP_HEIGHT = 50;
  static int BOTTOM_HEIGHT = 30;
  static int BAR_WIDTH = 20;
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
  
  

   void addedParts(){
      label = new JLabel("繰り返す");
      super.add(label);
   }
   
   protected void paintComponent(Graphics g){
     super.paintComponent(g);
     label.setBounds(WIDTH/3, TOP_HEIGHT/2-10, WIDTH/3, 20);
   }
  

  public void setPolygon(){
    int Xarray[] = {0, WIDTH, WIDTH, BAR_WIDTH, BAR_WIDTH, WIDTH, WIDTH, 0};
    int Yarray[] = {0, 0, TOP_HEIGHT, TOP_HEIGHT, HEIGHT-BOTTOM_HEIGHT, HEIGHT-BOTTOM_HEIGHT, HEIGHT, HEIGHT};
    Polygon polygon = new Polygon(Xarray, Yarray, Xarray.length);
    super.polygon = polygon;
    super.setOutLine();
    
  }
  
  public String code(){return "";/*仮設置*/};
//    
//   String source = "void loop(){\n";
//   
//   while(/*次のパネルが見つからないまで*/){
//     source = source + "Ｓｔｒｉｎｇのソースをくっつけていく";
//   }
//   
//   return source;
//  }
}