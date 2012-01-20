package processing.app;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class LedPanel extends PanelTranslate implements ActionListener{
  final static long blockId = 3;
  
  /*グラデーデション用に2色あるだけ*/
  private Color colorright = new Color(0,0,0);
  private Color colorleft = new Color(0,0,0);
  
  private PanelTranslate nextPanel;
  private PanelTranslate beforePanel;
  
  int Xarray[] = {0, WIDTH, WIDTH, 0};
  int Yarray[] = {0, 0, HEIGHT, HEIGHT};
  
  static int HEIGHT = 40; 
  static int WIDTH = 120;

  private int x, y;//このタイルの設置座標
  
  /*NextPanel用のセットポジション*/
  private int xposition = this.x;
  private int yposition = this.y + HEIGHT;
  
  private Polygon outLine = new Polygon();
  
  private Polygon polygon = new Polygon();
  private String source="";
  
  JComboBox ledCombo;
  DefaultComboBoxModel com;
  public Object [] combostr ={"点灯", "消灯"};
  JLabel label;
  String select="HIGH";
  
  LedPanel(int x, int y) {
    super(blockId);
    this.x = x;
    this.y = y;
    setPolygon();
    setOutLine();
    addedParts();
    super.setBounds(this.x, this.y, WIDTH, HEIGHT);
    
  }
  
  @Override
  public void uncouple(PanelTranslate p){
    if(super.getNextPanelTranslate() == p){super.setNextPanelTranslate(null);}

    System.out.println("**********\np:"+p+"\nNext:"+super.getNextPanelTranslate());
  }
  
  public void uncouple(){
    if(super.getBeforePanelTranslate() != null){
      super.getBeforePanelTranslate().uncouple(this);
      super.setBeforePanelTranslate(null);
    }
  }
  
  
  private void setPolygon(){
    polygon = new Polygon(Xarray, Yarray, Xarray.length);
  }
  
  private void setOutLine(){
   
     outLine.reset();
     for(int i=0; i<4; i++){
       outLine.addPoint(this.x+polygon.xpoints[i], this.y+polygon.ypoints[i]);
     }
     
     
  }
  

  public PanelTranslate getNextPanelTranslate(){
    return nextPanel;
  }
  
  public PanelTranslate getBeforePanelTranslate(){
    return beforePanel;
  }
  
  public void setNextPanelTranslate(PanelTranslate np){
    this.nextPanel = np;
  }
  
  public void setBeforePanelTranslate(PanelTranslate bp){
    this.beforePanel = bp;
  
    
  @Override
  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
    setOutLine();
        
    //
    if(super.getNextPanelTranslate() != null){
      
      super.getNextPanelTranslate().setLocation(xposition+x, yposition+y);
    }
    //
    
    return;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g.create();
    //polygon.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    
//    BasicStroke bs = new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
//    BasicStroke bs = new BasicStroke(0.5f);
//    g2.setStroke(bs);
    
    GradientPaint gp = new GradientPaint(0f, 0f, colorleft, (float)WIDTH, (float)HEIGHT, colorright);
    g2.setPaint(gp);
    g2.fill(polygon);
    g2.dispose();
  }

  
  @Override
  public boolean inline(int x, int y) {
   return polygon.contains(x, y);
  }
  
  @Override
  public PanelTranslate getContain(int x, int y){
    if(outLine.contains(x, y)){return super.getNextPanelTranslate();}
    
    return null;
  }


  @Override
  public void setPanelTranslate(int x, int y, PanelTranslate p) {
    if(super.nextSetis){
      
      if(outLine.contains(x,y)){
        if(super.getNextPanelTranslate() == null){
          super.setNextPanelTranslate(p);
        }
        else{
          super.getNextPanelTranslate().setBeforePanelTranslate(p);
          p.setNextPanelTranslate(super.getNextPanelTranslate());
          super.setNextPanelTranslate(p);
          p.setBeforePanelTranslate(this);
        }
        super.setLocation(x, y);
      }


    }
    return;
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
  public String code(){
    source = "digitalWrite(LED, "+select+");\n";
    return source;
  }

  @Override
  public void actionPerformed(ActionEvent list) {
    if(ledCombo.getSelectedItem().equals(combostr[0]))select = "HIGH";
    if(ledCombo.getSelectedItem().equals(combostr[1]))select = "LOW";
  }
 
}