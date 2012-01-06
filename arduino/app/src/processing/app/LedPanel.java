package processing.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class LedPanel extends PanelTranslate implements ActionListener{

  
  static int x = 2, y = 2;
  static int HEIGHT = 40;
  static int WIDTH = 120;
//  static Color color = Color.orange;
  static long blockid;

   JComboBox ledCombo;
   DefaultComboBoxModel com;
   public Object [] combostr ={"点灯", "消灯"};
   JLabel label;

  LedPanel(){
    super(WIDTH+x*2, HEIGHT+y*2, Color.green, new Color(0,0.3f,0));
    setPolygon();
    addedParts();
  }
  
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    label.setBounds(10, HEIGHT/2-10, 35, 20);
    ledCombo.setBounds(50, HEIGHT/2-10, 55, 20);
  }
  

   void addedParts(){
     label = new JLabel("LEDを");
     super.add(label);
     
      ledCombo = new JComboBox();
      ledCombo.addActionListener(this);
      com = new DefaultComboBoxModel(combostr);
      ledCombo.setModel(com);
      super.add(ledCombo);
     
   }
  

  public void setPolygon(){
    int Xarray[] = {x, WIDTH, WIDTH, x};
    int Yarray[] = {y, y, HEIGHT, HEIGHT};
    Polygon polygon = new Polygon(Xarray, Yarray, Xarray.length);
    super.polygon = polygon;
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

