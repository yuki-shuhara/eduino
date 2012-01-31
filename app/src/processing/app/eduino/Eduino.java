package processing.app.eduino;

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
    
    public Eduino(){
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
      
      WorkingSpace.setStartPanel(CreatePanel.create("Start"), PanelMove);

    }
    
    public String compile(){
      String source="#include <Arduino.h>\n" +
      		"#define LED 13\n" +
      		"#define TEMP 5\n" +
      		"#define LIGHT 4\n" +
      		"#define WhiteSw 7\n" +
      		"#define RedSw 8\n" +
      		"#define OrangeSw 9\n" +
      		"int tmp=0;\n" +
      		"int light=0;\n" +
      		"void setup(){\n" +
      		"pinMode(LED, OUTPUT);\n" +
      		"pinMode(WhiteSw, INPUT);\n" +
      		"pinMode(RedSw, INPUT);\n" +
      		"pinMode(OrangeSw, INPUT);\n" +
      		"\n}\n";
      
      if(WorkingSpace.getLoop() != null){
        source = source + WorkingSpace.getLoop().code();
      }     
      return source;
    }
 

}