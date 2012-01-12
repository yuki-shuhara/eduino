package processing.app;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class SwitchPanel extends PanelTranslate implements ActionListener{

  static int HEIGHT = 40;
  static int WIDTH = 120;
//  static Color color = Color.orange;
  static long blockid;

   JLabel label;

  SwitchPanel(){
    super(WIDTH, HEIGHT, new Color(0.5f, 0.5f, 0.5f), new Color(0.3f, 0.3f, 0.3f), 0, HEIGHT);
    setPolygon();
    addedParts();
  }
  
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
  }
  

   void addedParts(){
     label = new JLabel();
     //label.setIcon("");
     super.add(label);
   }
  

  public void setPolygon(){
    int Xarray[] = {0, WIDTH, WIDTH, 0};
    int Yarray[] = {0, 0, HEIGHT, HEIGHT};
    Polygon polygon = new Polygon(Xarray, Yarray, Xarray.length);
    super.polygon = polygon;
    super.setOutLine();
  }
  
  public String code(){return "";/*仮設置*/}


  @Override
  public void actionPerformed(ActionEvent list) {
    // TODO Auto-generated method stub
    
  };
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
