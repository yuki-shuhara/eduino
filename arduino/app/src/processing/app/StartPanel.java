package processing.app;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Polygon;

import javax.swing.JLabel;


public class StartPanel extends PanelTranslate{
  final static long blockId = 0;
  
  /*グラデーデション用に2色あるだけ*/
  private Color colorright = new Color(255,192,203);
  private Color colorleft = new Color(255,192,203);
  
  int Xarray[] = {0, WIDTH, WIDTH, BAR_WIDTH, BAR_WIDTH, WIDTH, WIDTH, 0};
  int Yarray[] = {0, 0, TOP_HEIGHT, TOP_HEIGHT, TOP_HEIGHT+BAR_HEIGHT, TOP_HEIGHT+BAR_HEIGHT, HEIGHT, HEIGHT};
  
  static int HEIGHT = 160; //=topheight+barheight+bottomheight
  static int WIDTH = 180;
  static int TOP_HEIGHT = 50;
  static int BAR_HEIGHT = 60;
  static int BAR_WIDTH = 20;
  static int BOTTOM_HEIGHT = 50;
  private int x, y;//このタイルの設置座標
  
  /*NextPanel用のセットポジション*/
//  private int xposition = this.x;
//  private int yposition = this.y + HEIGHT;
  
  /*間に挟まるパネル用のセットポジション*/
  private PanelTranslate firstPanel;
  private int firstposition_x =this.x + BAR_WIDTH;
  private int firstposition_y =this.y + TOP_HEIGHT;
  private Polygon firstOutLine = new Polygon();
  
  private Polygon polygon = new Polygon();
  private String source="";
  

  StartPanel(int x, int y) {
    super(blockId);
    firstPanel = null;
    this.x = x;
    this.y = y;
    setPolygon();
    setOutLine();
    super.setBounds(this.x, this.y, WIDTH, HEIGHT);
  }
  
  @Override
  public void uncouple(PanelTranslate p){
    if(firstPanel == p){firstPanel = null;}
    
  }
  
  @Override
  public void uncouple(){
//
  }
  
  
  
  private void setPolygon(){
    polygon = new Polygon(Xarray, Yarray, Xarray.length);
  }
  
  private void setOutLine(){

    //
     firstOutLine.reset();
     for(int i=0; i<3; i++){
       firstOutLine.addPoint(this.x+polygon.xpoints[i], this.y+polygon.ypoints[i]);
     }
     firstOutLine.addPoint(this.x, this.y+TOP_HEIGHT);
    
    //
     
  }
  

  @Override
  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
    setOutLine();
    
    //
    if(firstPanel != null){
      firstPanel.setLocation(firstposition_x+x, firstposition_y+y);
    }
    //
    return;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g.create();
    //polygon.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    
    int sum = 0;
    PanelTranslate t = super.getNextPanelTranslate();
    while(t != null){
      sum = sum + t.getHeight();
      t = t.getNextPanelTranslate();
    }
    if(sum < 1) sum = BAR_HEIGHT;
    BAR_HEIGHT = sum;
    setHEIGHT();
    super.setSize(WIDTH, HEIGHT);
    //this.setPolygon();
    
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
    if(firstOutLine.contains(x, y)){return firstPanel;}
    //if(OutLine.contains(x, y)) return nextPanel;
    
    return null;
  }


  @Override
  public void setPanelTranslate(int x, int y, PanelTranslate p) {
    if(super.nextSetis){
      PanelTranslate setPosition = getContain(x, y);
      if(setPosition != null){
        setPosition = p;
        p.setBeforePanelTranslate(this);
        super.setLocation(x, y);
      }
    }     
  }
  
  @Override
  public String code(){
     source = "void loop(){\n";
     
    PanelTranslate panel = super.getNextPanelTranslate();
    while(panel != null){
      source = source + panel.code();
      panel = panel.getNextPanelTranslate();
    }
      source = source + "exit(1);\n}";
      
    return source;
  }
  
  private void setHEIGHT(){
    HEIGHT = TOP_HEIGHT+BAR_HEIGHT+BOTTOM_HEIGHT;
  }
 
}
