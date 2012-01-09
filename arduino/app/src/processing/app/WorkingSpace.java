package processing.app;

import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class WorkingSpace extends JPanel{
  
  public JPanel CreaPanel;
  private boolean out;
  Polygon p;
  
  WorkingSpace(){
    this.setLayout(null);
    //this.addMouseListener(new WorkingspaceListener());
    initCreaPanel();
    this.add(CreaPanel);
    out = false;
    initPolygon();
  }
  
  private void initCreaPanel(){
    CreaPanel = new JPanel();
    CreaPanel.setOpaque(false);
    CreaPanel.setLayout(null);
    CreaPanel.setBounds(0,0, Short.MAX_VALUE, Short.MAX_VALUE);
  }
  
  private void initPolygon(){
    int x[] = {0, Short.MAX_VALUE, Short.MAX_VALUE, 0};
    int y[] = {0, 0, Short.MAX_VALUE, Short.MAX_VALUE};
    p = new Polygon(x,y,x.length);
  }
  
  public boolean checkOut(int x, int y){
    return p.contains(x,y);
  }

//  public class WorkingspaceListener implements MouseListener{
//    @Override
//    public void mouseClicked(MouseEvent e) {}
//    @Override
//    public void mouseEntered(MouseEvent e) {
//      out = false;
//      //System.out.println(out);
//    }
//    @Override
//    public void mouseExited(MouseEvent e) {
//      out = true;
//      //System.out.println(out);
//    }
//    @Override
//    public void mousePressed(MouseEvent e) {}
//    @Override
//    public void mouseReleased(MouseEvent e) {}
//  }


}
