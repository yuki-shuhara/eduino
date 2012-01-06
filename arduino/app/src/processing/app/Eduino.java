package processing.app;

import java.awt.Color;

import javax.swing.JPanel;

public class Eduino extends JPanel{

    JPanel Field;
    CreatePanel CreatePanel;
    
    Eduino(){
      Field = new JPanel();
      Field.setBackground(Color.white); 
      Field.setLayout(null); //土台パネル
      CreatePanel = new CreatePanel();
      new CreateButton(Field, CreatePanel);
    }
 
     public JPanel ret(){
       return Field;
     }
}
