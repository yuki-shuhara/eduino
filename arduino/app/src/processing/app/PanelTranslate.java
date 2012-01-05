package processing.app;

import java.awt.Color;
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
  
  final int height;
  final int width;
  final int x=300;
  final int y=30;
  Color color;
  
  PanelTranslate(int w, int h, Color c){
    System.out.println("createPanelTranslate");
    panelmove = new PanelMove(this);
    height = h;
    width = w;
    color = c;
    this.addMouseListener(panelmove);
    this.addMouseMotionListener(panelmove);
    this.setOpaque(false);
    this.setBounds(x, y, height, width);
  }
  
  public boolean getContains(int px, int py){
    return polygon.contains(px,py);
  }
  
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g.create();
    //polygon.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    g2.setPaint(Color.orange);
    g2.fill(polygon);
    g2.dispose();
  }

}     

