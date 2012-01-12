package processing.app;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Polygon;

import javax.swing.JLabel;

public class SegPanel extends PanelTranslate{
  
  static int HEIGHT = 100;
  static int WIDTH = 130;
  static int TOP_HEIGHT = 40;
  static int BOTTOM_HEIGHT = 20;
  static int BAR_WIDTH = 20;
//  static Color color = Color.blue;
  static long blockid;

  JLabel label;
  
//  LoopPanel(){
//    super(blockid);
//    
//  }
  SegPanel(){
    super(WIDTH, HEIGHT, Color.blue, Color.cyan, BAR_WIDTH, TOP_HEIGHT);
    setPolygon();
    addedParts();
  }
  

  void addedParts(){
      label = new JLabel("７セグメント表示");
      super.add(label);
  }
  
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    label.setBounds(WIDTH/10, TOP_HEIGHT/2-10, WIDTH-WIDTH/5, 20);
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