package processing.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

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
  
  PanelTranslate(int w, int h){
    height = h;
    width = w;
    this.addMouseListener(panelmove);
    this.addMouseMotionListener(panelmove);
    this.setOpaque(false);
    this.setBounds(x, y, height, width);
  }
  
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g.create();

    g2.setPaint(Color.orange);
    g2.fill(polygon);
    g2.dispose();
  }

}     

