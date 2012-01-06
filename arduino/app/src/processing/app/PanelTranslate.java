package processing.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;


import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

abstract class PanelTranslate extends JPanel {

//  final long id;
  protected String code;
  PanelMove panelmove;
  protected Polygon polygon;
  
  abstract String code();
  abstract void setPolygon();
  abstract void addedParts();
  
  final int height;
  final int width;
  final int x=300;
  final int y=30;
  private Color colorleft;
  private Color colorright;
  
  public Component component[];
  
  PanelTranslate(int h, int w){
    height = h;
    width = w;
  }
  
  PanelTranslate(int w, int h, Color c, Color c2){
    //System.out.println("createPanelTranslate");
    panelmove = new PanelMove(this);
    height = h;
    width = w;
    colorleft = c;
    colorright = c2;
    this.addMouseListener(panelmove);
    this.addMouseMotionListener(panelmove);
    this.setOpaque(false);
    this.setBounds(x, y, width, height);
  }
  
  public boolean getContains(int px, int py){
    return polygon.contains(px,py);
  }
  
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g.create();
    //polygon.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    
//    BasicStroke bs = new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    BasicStroke bs = new BasicStroke(2.0f);
    g2.setStroke(bs);
    
    GradientPaint gp = new GradientPaint(0f, 0f, colorleft, (float)width, (float)height, colorright);
    g2.setPaint(gp);
    g2.fill(polygon);
    g2.dispose();
  }

}     

