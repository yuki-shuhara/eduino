package processing.app;

import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class WorkingSpace extends JPanel{
  
  public JPanel CreaPanel;
  
  WorkingSpace(){
    this.setLayout(null);
    //this.addMouseListener(new WorkingspaceListener());
    initCreaPanel();
    this.add(CreaPanel);
  }
  
  private void initCreaPanel(){
    CreaPanel = new JPanel();
    CreaPanel.setOpaque(false);
    CreaPanel.setLayout(null);
    CreaPanel.setBounds(0,0, Short.MAX_VALUE, Short.MAX_VALUE);
  }
}
