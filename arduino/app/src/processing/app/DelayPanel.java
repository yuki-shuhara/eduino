package processing.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DelayPanel extends PanelTranslate {

  static int HEIGHT = 40;
  static int WIDTH = 100;
//  static Color color = Color.orange;
  static long blockid;
  
  
   JLabel label;
   JTextField text;

  DelayPanel(){
    super(WIDTH, HEIGHT, Color.red, Color.magenta, 0, HEIGHT);
    setPolygon();
    addedParts();
  }
  
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    text.setBounds(10, HEIGHT/2-10, 30, 20);
    label.setBounds(50, HEIGHT/2-10, 35, 20);
  }
  

   void addedParts(){
     text = new JTextField("1000");
     text.setColumns(4);
     super.add(text);
     
     label = new JLabel("ミリ秒待つ");
     super.add(label);
   }
  

  public void setPolygon(){
    int Xarray[] = {0, WIDTH, WIDTH, 0};
    int Yarray[] = {0, 0, HEIGHT, HEIGHT};
    Polygon polygon = new Polygon(Xarray, Yarray, Xarray.length);
    super.polygon = polygon;
    super.setOutLine();
  }
  
  public String code(){return "delay("+text.getText()+");\n" ;}



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