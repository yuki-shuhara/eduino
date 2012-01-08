package processing.app;

import javax.swing.JPanel;

public class WorkingSpace extends JPanel{
  
  JPanel CreaPanel;
  
  
  WorkingSpace(){
    this.setLayout(null);
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
