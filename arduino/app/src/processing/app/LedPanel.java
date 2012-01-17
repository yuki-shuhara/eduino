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

  static int HEIGHT = 40;
  static int WIDTH = 120;
//  static Color color = Color.orange;
  static long blockid;
  String select="HIGH";

   JComboBox ledCombo;
   DefaultComboBoxModel com;
   public Object [] combostr ={"点灯", "消灯"};
   JLabel label;

  LedPanel(){
    super(WIDTH, HEIGHT, Color.green, new Color(0,0.3f,0), 0, HEIGHT);
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
   @Override
   public void setLocation(int x, int y){
     super.setLocation(x, y);
   }
  

  public void setPolygon(){
    int Xarray[] = {0, WIDTH, WIDTH, 0};
    int Yarray[] = {0, 0, HEIGHT, HEIGHT};
    Polygon polygon = new Polygon(Xarray, Yarray, Xarray.length);
    super.polygon = polygon;
    super.setOutLine();
  }
  
  public String code(){
    return "digitalWrite(LED," + select + ");\n";
  }


  @Override
  public void actionPerformed(ActionEvent list) {
    if(ledCombo.getSelectedItem().equals(combostr[0]))select = "HIGH";
    if(ledCombo.getSelectedItem().equals(combostr[1]))select = "LOW";
  }
}

