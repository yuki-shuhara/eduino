package processing.app;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class CreateButton implements ActionListener{
  final int LOOP = 0;
  final int SEG = 1;
  final int LED = 2;
  final int DELAY = 3;
  final int SWITCH = 4;
  
  JPanel field;
  CreatePanel CreatePanel;
  

  String buttonLabel[] = {"繰り返しタイル作成", "７セグメント用タイル作成", "ＬＥＤ用タイル作成",
      "待機タイル作成", "スイッチ用タイル作成"};
  String buttonName[] = {"Loop", "Seg", "Led", "Delay", "Switch"};
  final int BUTTON_COUNT  = 5;
  static final int BUTTON_X = 10;
  static final int BUTTON_GAP = 10;//ボタンの間隔
  static final int BUTTON_HEIGHT = 40;
  static final int BUTTON_WIDTH= 200;
  
  
  CreateButton(JPanel field){
    CreatePanel = new CreatePanel();
    this.field = field;
    setButton();
  }
  
//  void setButton(){
//    button[0] =  new Button("テスト");
//    button[0].addActionListener(this);
//    button[0].setActionCommand(buttonName[0]);
//    button[0].setBounds(BUTTON_X, BUTTON_GAP+BUTTON_HEIGHT*0, BUTTON_WIDTH, BUTTON_HEIGHT);
////    button[0].setBounds(10, 50, 200, 50);
//    field.add(button[0]);
//  }
//  
  void setButton(){
    Button button[] = new Button[BUTTON_COUNT];
    for(int i = 0; i < BUTTON_COUNT; i++){
      button[i] = new Button(buttonLabel[i]);
      button[i].addActionListener(this);
      button[i].setActionCommand(buttonName[i]);
      button[i].setBounds(BUTTON_X, BUTTON_GAP*(i+1)+BUTTON_HEIGHT*i, BUTTON_WIDTH, BUTTON_HEIGHT);
      field.add(button[i]);
    }
  }
 
  @Override
  public void actionPerformed(ActionEvent push) {
    field.add(CreatePanel.create(push.getActionCommand()));
    //System.out.println(push.getActionCommand());
  }
  
}
