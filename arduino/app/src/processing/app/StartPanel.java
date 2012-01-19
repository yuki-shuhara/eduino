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
  private Color colorright = new Color(0,0,0);
  private Color colorleft = new Color(0,0,0);
  
  int Xarray[] = {0, WIDTH, WIDTH, BAR_WIDTH, BAR_WIDTH, WIDTH, WIDTH, 0};
  int Yarray[] = {0, 0, TOP_HEIGHT, TOP_HEIGHT, TOP_HEIGHT+BAR_HEIGHT, TOP_HEIGHT+BAR_HEIGHT, HEIGHT, HEIGHT};
  
  static int HEIGHT = 110; //=topheight+barheight+bottomheight
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
    
  }
  
  private void setPolygon(){
    polygon = new Polygon(Xarray, Yarray, Xarray.length);
  }
  
  private void setOutLine(){

     firstOutLine.reset();
     
     for(int i=0; i<4; i++){
       firstOutLine.addPoint(this.x+, i)
     }
    
  }
  

  @Override
  public void setPosition() {
    if(firstPanel != null){
      firstPanel.setLocation(firstposition_x+x, firstposition_y+y);
    }
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
    //super.repaint();
  }

  
  @Override
  public boolean inline(int x, int y) {
   return polygon.contains(x, y);
  }
  
  @Override
  public boolean getContain(int x, int y){
    if(firstOutLine.contains(x, y)) return true;
    //if(
    return false;
  }

  @Override
  public PanelTranslate checkSetPosition(int x, int y) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setPanelTranslate(PanelTranslate p) {

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
