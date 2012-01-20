package processing.app;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DelayPanel extends PanelTranslate{
  final static long blockId = 3;
  
  /*グラデーデション用に2色あるだけ*/
  private Color colorright = new Color(0,0,0);
  private Color colorleft = new Color(0,0,0);
  
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
  
  JLabel label;
  JTextField text;
  
  DelayPanel(int x, int y) {
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
    text = new JTextField("1000");
    text.setColumns(5);
    super.add(text);
    
    label = new JLabel("ミリ秒待つ");
    super.add(label);
  }
  
  @Override
  public String code(){
    source = "delay("+ text.getText()+ ");\n";
    return source;
  }


 
}