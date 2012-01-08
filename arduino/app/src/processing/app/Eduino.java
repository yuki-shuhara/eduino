package processing.app;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Eduino extends JPanel{
  
    public int BorderLine = 200;

    WorkingSpace WorkingSpace;
    CreatePanel CreatePanel;
    CreateButton CreateButton;
    
    Eduino(){
      this.setBackground(Color.white); 
      this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      CreatePanel = new CreatePanel();
      
      WorkingSpace = new WorkingSpace();
      CreateButton = new CreateButton(WorkingSpace, CreatePanel);
      
      CreateButton.setMaximumSize(new Dimension(BorderLine, Short.MAX_VALUE));
      WorkingSpace.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
      
      this.add(CreateButton);
      this.add(WorkingSpace);
    }
 

}
