package processing.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

abstract class PanelTranslate extends JPanel {

  private long blockId;
  protected String code;
  //PanelMove panelmove;
  protected Polygon polygon;
  protected Polygon outLine;
  
  abstract String code();
  abstract void setPolygon();
  abstract void addedParts();
  
  protected int height;
  protected int width;
  protected int x=100;
  protected int y=30;
  private  int setPositionX, setPositionY;
  private Color colorleft;
  private Color colorright;
   
  private PanelTranslate nextPanelTranslate;
  protected PanelTranslate beforePanelTranslate;
  
  //public Component component[];
  
  PanelTranslate(){
//    setPositionX = 0;
//    setPositionY = 0;
  }
  
  PanelTranslate(int w, int h, Color c, Color c2, int positionx, int positiony){
    //System.out.println("createPanelTranslate");
    setPositionX = positionx;
    setPositionY = positiony;
    height = h;
    width = w;
    colorleft = c;
    colorright = c2;
    this.setOpaque(false);
    this.setBounds(x,y,w,h);
    beforePanelTranslate = null;
    nextPanelTranslate = null;
    outLine = new Polygon();
  }
  
  public boolean getContains(int px, int py){
    return outLine.contains(px,py);
  }
  
  public boolean inLine(int px, int py){
    return polygon.contains(px,py);
  }
  
  public void setbeforePanelTranslate(PanelTranslate beforePanel){
    if(beforePanel==null){
      if(beforePanelTranslate != null){
        beforePanelTranslate.nextPanelTranslate = null;
        this.beforePanelTranslate = null;
      }
      
      return;
    }
    beforePanelTranslate = beforePanel;
  }
  
  int a=0,b=0;
  int c=0;
  
  public void setNextPanelTranslate(PanelTranslate nextPanel){
    
      if(nextPanel == null){
        nextPanelTranslate = null;
        return;
      }
      if(nextPanelTranslate == null){//くっつける処理
        nextPanel.beforePanelTranslate = this;
        nextPanelTranslate = nextPanel;
        //System.out.println("next=null:"+a++);
      }
      else{//割り込み処理
        this.nextPanelTranslate.beforePanelTranslate = nextPanel;
        nextPanel.nextPanelTranslate = nextPanelTranslate;
        this.nextPanelTranslate = nextPanel;
        nextPanel.beforePanelTranslate = this;
      }

      System.out.println("setNextPanel:"+c++);
      this.setLocation(x, y);
      
  }
  
  public PanelTranslate getNextPanelTranslate(){
    return nextPanelTranslate;
  }
  
  public void setId(long id){
    blockId = id; 
  }
  
  public long getId(){
    return blockId;
  }
  
  protected void setOutLine(){
    int px, py;
    outLine.reset();
    for(int i=0; i<polygon.npoints; i++){
      px = polygon.xpoints[i];
      py = polygon.ypoints[i];
      outLine.addPoint(px+x, py+y);
    }
    //outLine = new Polygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
  }
  
int d=0;
  @Override
  public void setLocation(int x, int y){
    
    super.setLocation(x, y);
    this.x=x;
    this.y=y;
    setOutLine();
    if(nextPanelTranslate == null){
      return;
    }
    else{ 
      System.out.println(this+"setLocation_null:"+d++);
      nextPanelTranslate.setLocation(this.x + setPositionX, this.y + setPositionY);
      return;
    }
    
  }
  
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g.create();
    //polygon.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    
//    BasicStroke bs = new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
//    BasicStroke bs = new BasicStroke(0.5f);
//    g2.setStroke(bs);
    
    GradientPaint gp = new GradientPaint(0f, 0f, colorleft, (float)width, (float)height, colorright);
    g2.setPaint(gp);
    g2.fill(polygon);
    g2.dispose();
    //super.repaint();
  }

}     

