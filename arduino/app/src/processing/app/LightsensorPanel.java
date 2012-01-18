package processing.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class LightsensorPanel extends PanelTranslate {

  static int HEIGHT = 60;
  static int WIDTH = 100;
//  static Color color = Color.orange;
  static long blockid;
  String str;
  
   JLabel label;

   LightsensorPanel(){
    super(WIDTH, HEIGHT, new Color(2, 232, 238), new Color(2, 232, 240), 0, HEIGHT);
    setPolygon();
    addedParts();
  }
  
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    label.setBounds(10, HEIGHT/2-10, 80, 20);
  }
  
  @Override
  public void setLocation(int x, int y){
    super.setLocation(x, y);
  }
  

   void addedParts(){   
     label = new JLabel("光センサー");
     super.add(label);
   }
  

  public void setPolygon(){
    int Xarray[] = {0, WIDTH, WIDTH, 0};
    int Yarray[] = {0, 0, HEIGHT, HEIGHT};
    Polygon polygon = new Polygon(Xarray, Yarray, Xarray.length);
    super.polygon = polygon;
    super.setOutLine();
  }
  
  public String code(){
    return str = "light = analogRead(LIGHT);\n";
    }

}