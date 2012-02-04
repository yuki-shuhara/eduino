package processing.app.eduino;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class Eduino extends JPanel{

    private int BorderLine = 200;
    private WorkingSpace WorkingSpace;
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
      
      WorkingSpace.setStartPanel(CreatePanel.create("Start", 200, 200), PanelMove);

    }
    
    public String compile(){
      String source ="";
      SketchHeader sh = new SketchHeader(WorkingSpace);
      sh = new SketchHeader(WorkingSpace);
      
      try{
        source = (WorkingSpace.getLoop()).code();
        source = sh.getSetup() + source ;
      }
      catch(NullPointerException e){
        System.out.println("開始・終了タイルが見つかりません");  
      }
      
      System.out.println(source);
      return source;
    }
 

}