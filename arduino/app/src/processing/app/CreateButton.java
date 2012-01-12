package processing.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class CreateButton extends JPanel implements ActionListener{
//  final int LOOP = 0;
//  final int SEG = 1;
//  final int LED = 2;
//  final int DELAY = 3;
//  final int SWITCH = 4;
  
  WorkingSpace WorkingSpace;
  CreatePanel CreatePanel;
  PanelMove PanelMove;
  
  String buttonLabel[] = {"繰り返しタイル作成", "７セグメント用タイル作成", "ＬＥＤ用タイル作成",
      "待機タイル作成", "スイッチ用タイル作成"};
  String buttonName[] = {"Loop", "Seg", "Led", "Delay", "Switch"};
  final int BUTTON_COUNT  = 5;
  final int BUTTON_GAP = 10;//ボタンの間隔
  final int BUTTON_HEIGHT = 30;
  final int FONT_SIZE = 20;
  //static final Color BackColor = new Color(r, g, b);
  
  
  CreateButton(WorkingSpace WorkingSpace, CreatePanel CreatePanel, PanelMove PanelMove){
    this.WorkingSpace = WorkingSpace;
    this.CreatePanel = CreatePanel;
    this.PanelMove = PanelMove;
    initPanel();
    setButton();
  }
  
  void initPanel(){
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setBackground(Color.GRAY);
    //this.setBackground(BackColor);
  }
   
  void setButton(){
    JButton button[] = new JButton[BUTTON_COUNT];
    for(int i = 0; i < BUTTON_COUNT; i++){
      button[i] = new JButton(buttonLabel[i]);
      button[i].addActionListener(this);
      button[i].setActionCommand(buttonName[i]);
      button[i].setMaximumSize(new Dimension(Short.MAX_VALUE, BUTTON_HEIGHT));
      //button[i].setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
      this.add(Box.createRigidArea(new Dimension(1, BUTTON_GAP)));
      this.add(button[i]);
    }
  }
 
  @Override
  public void actionPerformed(ActionEvent push) {
    PanelTranslate p = CreatePanel.create(push.getActionCommand());
    if(p==null){return;}
    p.addMouseListener(PanelMove);
    p.addMouseMotionListener(PanelMove);
    WorkingSpace.PlacedPanel(p);

    WorkingSpace.moveToFront(p);
    WorkingSpace.add(p);
    WorkingSpace.repaint();
    //System.out.println(push.getActionCommand());
  }
}
