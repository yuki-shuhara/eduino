package processing.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class LoopPanel extends PanelTranslate{
  
  static int HEIGHT = 250;
  static int WIDTH = 220;
  static long blockid;

  
//  LoopPanel(){
//    super(blockid);
//    
//  }
  LoopPanel(){
    super(HEIGHT, WIDTH);
    setPolygon();
  }

  public void setPolygon(){
    int Xarray[] = {0, WIDTH, WIDTH, 20, 20, WIDTH, WIDTH, 0};
    int Yarray[] = {0, 0, 50, 50, HEIGHT-30, HEIGHT-30, HEIGHT, HEIGHT};
    Polygon polygon = new Polygon(Xarray, Yarray, Xarray.length);
    super.polygon = polygon;
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