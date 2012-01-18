package processing.app;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Polygon;

import javax.swing.JLabel;


public class SwitchPanel extends PanelTranslate implements ActionListener{
  
  static int HEIGHT = 220; //=topheight+barheight+bottomheight
  static int WIDTH = 150;
  static int TOP_HEIGHT = 50;
  static int BAR_HEIGHT1 = 50;
  static int BAR_HEIGHT2 = 50;
  static int BAR_WIDTH = 20;
  static int BOTTOM_HEIGHT = 20;
//  static Color color = Color.orange;
  static long blockid;

  JLabel firstLabel, secondLabel;;
  String select="SWITCH1";
  
  JComboBox switchCombo;
  DefaultComboBoxModel com;
  public Object [] combostr ={"スイッチ１", "スイッチ２"};
  
  Polygon firstOutLine;
  Polygon secondOutLine;

  SwitchPanel(){
    super(WIDTH, HEIGHT, new Color(0f, 0.7f, 0f), new Color(0f, 0.8f, 0f), 0, HEIGHT);
    setPolygon();
    addedParts();
  }
  public void setHeight(){
    HEIGHT = TOP_HEIGHT*2+BAR_HEIGHT1+BAR_HEIGHT2+BOTTOM_HEIGHT;
  }
  @Override
  public void setbeforePanelTranslate(PanelTranslate beforePanel){
    super.setbeforePanelTranslate(beforePanel);
 
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
      firstLabel = new JLabel("がONのとき");
      secondLabel= new JLabel("OFFのとき");
      super.add(firstLabel);
      
      switchCombo = new JComboBox();
      switchCombo.addActionListener(this);
      com = new DefaultComboBoxModel(combostr);
      switchCombo.setModel(com);
      super.add(switchCombo);
      
      super.add(secondLabel);
   }
   
   
   protected void paintComponent(Graphics g){
     super.paintComponent(g);
     
     int sum = 0;
     PanelTranslate t = super.getNextPanelTranslate();//要修正
     while(t != null){
       sum = sum + t.height;
       t = t.getNextPanelTranslate();
     }
     if(sum < 1) sum = BAR_HEIGHT1;
   BAR_HEIGHT1 = sum;
   setHeight();
   super.setSize(WIDTH, HEIGHT);
   this.setPolygon();
   
     firstLabel.setBounds(80, TOP_HEIGHT/2-10, 75, 20);
     secondLabel.setBounds(WIDTH/5, TOP_HEIGHT+BAR_HEIGHT1+TOP_HEIGHT/2-10, WIDTH/3, 20);
     switchCombo.setBounds(10, TOP_HEIGHT/2-10, 70, 20);
   }
  

  public void setPolygon(){
    int Xarray[] = {0, WIDTH, WIDTH, BAR_WIDTH, BAR_WIDTH, WIDTH, WIDTH, BAR_WIDTH, BAR_WIDTH, WIDTH, WIDTH, 0};
    int Yarray[] = {0, 0, TOP_HEIGHT, TOP_HEIGHT, TOP_HEIGHT+BAR_HEIGHT1, TOP_HEIGHT+BAR_HEIGHT1, TOP_HEIGHT*2+BAR_HEIGHT1, TOP_HEIGHT*2+BAR_HEIGHT1,
                    TOP_HEIGHT*2+BAR_HEIGHT1+BAR_HEIGHT2, TOP_HEIGHT*2+BAR_HEIGHT1+BAR_HEIGHT2, HEIGHT, HEIGHT};
    Polygon polygon = new Polygon(Xarray, Yarray, Xarray.length);
    super.polygon = polygon;
    super.setOutLine();
    
  }
  
  public String code(){
    
   String source = "if(digitalRead("+select+")){\n";
   
   PanelTranslate panel1 = super.getNextPanelTranslate();//要編集
   while(panel1 != null){
     System.out.println(source);
     source = source + panel1.code();
     panel1 = panel1.getNextPanelTranslate();
   }
   
     source = source + "\n}else{\n";
   
     PanelTranslate panel2 = super.getNextPanelTranslate();//要編集
     while(panel2 != null){
       System.out.println(source);
       source = source + panel2.code();
       panel2 = panel2.getNextPanelTranslate();
     }
     
     source = source + "\n}\n";
     
   return source;
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    if(switchCombo.getSelectedItem().equals(combostr[0]))select = "SWITCH1";
    if(switchCombo.getSelectedItem().equals(combostr[1]))select = "SWITCH2";
    
  }
}