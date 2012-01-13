package processing.app;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class Eduino extends JPanel{

    private int BorderLine = 200;
    WorkingSpace WorkingSpace;
//    PanelMove PanelMove;
//    CreatePanel CreatePanel;
//    CreateButton CreateButton;
//    JSplitPane splitpane;
    
    Eduino(){
      this.setBackground(Color.white); 
      this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      CreatePanel CreatePanel = new CreatePanel();     
      WorkingSpace = new WorkingSpace();
      PanelMove PanelMove = new PanelMove(WorkingSpace);
      
      CreateButton CreateButton = new CreateButton(WorkingSpace, CreatePanel, PanelMove);
      
      CreateButton.setMaximumSize(new Dimension(BorderLine, Short.MAX_VALUE));
      WorkingSpace.setMinimumSize(new Dimension(200, 400));
      WorkingSpace.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
      
      JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, CreateButton, WorkingSpace);
      splitpane.setDividerSize(5);
      splitpane.setDividerLocation(BorderLine);
      splitpane.setContinuousLayout(true);
      this.add(splitpane);

    }
    
    public String compile(){
      String source="#include <Arduino.h>\n" +
      		"#define LED 13\n" +
      		"void setup(){\n" +
      		"pinMode(LED, OUTPUT);" +
      		"\n}\n";
      
      if(WorkingSpace.getLoop() != null){
        source = source + WorkingSpace.getLoop().code();
      }     
      return source;
    }
 

}