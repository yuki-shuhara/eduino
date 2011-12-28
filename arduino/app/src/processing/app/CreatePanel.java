package processing.app;

import javax.swing.Box;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;

import processing.app.Editor.FileDropHandler;
import processing.app.Editor.TextAreaPopup;
import processing.app.syntax.JEditTextArea;
import processing.app.syntax.PdeTextAreaDefaults;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/* CreatePanelクラスでは、パネルを作成する。また、今後の実装で、データの管理も行うようにする*/

public class CreatePanel extends JPanel implements ActionListener{
  
 
   public JPanel pane;
   public final int ID;
   private int type;
  
   private boolean link;
   private boolean toplevel;
   
  /*コンボボックス用*/
   public JComboBox jComboBox1;
   public JComboBox jComboBox2;
   public DefaultComboBoxModel com1,com2,com3,com4;
   public JComboBox combo = new JComboBox();
   public JComboBox action1 = new JComboBox();
   public JComboBox action2 = new JComboBox();
   public Object [] combo1 ={"LED", "モーター"};
   public Object [] combo2 ={"光らせる", "光らせない"};
   public Object [] combo3 ={"回転させる", "止める"};
   public Object [] combo4 ={"-", "-"};
  /*ここまでコンボボックス用*/
  
  /*ラベル用*/
    public JLabel jLabel;
  /*ここまでラベル用*/
  
  /*テキストボックス用*/
    public JTextField text1;
    
    CreatePanel(){
      pane = new JPanel();
      link = true;
      toplevel = true;
      ID = 0;
    }
    
    CreatePanel(int panelcount){
      pane = new JPanel();
      link = true;
      toplevel = true;
      ID = panelcount;
      pane.setName(Integer.toString(ID));
    }


    /*** 外部操作可能な部分 
     * @return ***/
    public void create(int x){
      //エラー文挿入予定      
      switch(x){
      case 1:loopPanel(); //ループパネルの作成
              break;
      case 2:outPutPanel(); //出力パネルの作成
              break;
      case 3:delayPanel();  //遅延パネルの作成
              break;
      case 4:startPanel();
              break;
      case 5:endPanel();
              break;
      default:delayPanel();
              break; //何もしない
      }
      type = x;
     }
    
    public JPanel create(JPanel p, int x){
      pane = p;
      create(x);
      return pane;
    }
    
    public JPanel Panelreturn(){
      return pane;
    }
    

    
    /*** 結合用Boolean変数 ***/
    public boolean link(){
      return link;
    }
    
    public void changelink(boolean t){
      link = t;
    }
    
    public boolean toplevel(){
      return toplevel;
    }
    
    public void changetoplevel(boolean t){
      toplevel = t;
    }
    
    public String getcode(String str){
      switch(ID){
      case 1: str = str + "void loop(){"; break;
      case 2: str = str + ""; break;
      case 3: str = str + ""; break;
      case 4: str = str + ""; break;
      case 5: str = str + ""; break;
      default: break;
      }
      return str;
    }
    
    /***  JPanelのオーバーロード ***/
    public int getX(){
      return pane.getX();
    }
    
    public int getY(){
      return pane.getY();
    }
    
    public int getWidth(){
      return pane.getWidth();
    }
    
    public int getHeight(){
      return pane.getHeight();
    }
    
    public void setLocation(int x, int y){
      pane.setLocation(x, y);
    }
    
    public void setSize(int w, int h){
      pane.setSize(w, h);
    }
    
  /*ここまでテキストボックス*/
  /*public CreatePanel(){
    outPutPanel();
    addListener();
    basePanel();
    //factory(); //テスト用にfactoryを指定。本番は所定のパネル作成関数に振り分けるようにする
  }*/
  
 /*  public CreatePanel(JPanel pane, int x){ //コンストラクタ　引数は土台JPanelと作るパネル指定のint
     
     this.pane = pane; //広域JPanelに引き継いだJPanelを設置
     
     switch(x){
     case 0:loopPanel(); //ループパネルの作成
             break;
     case 1:outPutPanel(); //出力パネルの作成
             break;
     case 2:delayPanel();  //遅延パネルの作成
             break;
     default:break; //何もしない
    }*/ //コンストラクタは削除予定

   /*** Create "StartPanel"***/
   public void startPanel(){
     pane.setSize(150, 50);
     Label("開始");
     pane.setBackground(Color.ORANGE);
   }
   
   public void endPanel(){
     pane.setSize(150, 50);
     Label("終了");
     pane.setBackground(Color.ORANGE);
   }
   
  
   /*** Crate "OutputPanel" ***/
   public void outPutPanel(){

    pane.setSize(190, 50);
    comboBox();
    pane.setBackground(Color.BLUE); 
  }
  
   /*** Create "DelayPanel" ***/
   public void delayPanel(){

    pane.setSize(180, 50);
    textBox("1000");
    Label(2);
    pane.setBackground(Color.YELLOW); 
  }
  
  /*** Create "LoopPanel" ***/
   public void loopPanel(){
    textBox();
    Label(2);
    pane.setSize(100, 30);
    pane.setBackground(Color.ORANGE); 
    
  }
  
  /*********************************************/
   /*** Create BasePanel ***/
  /*public void basePanel(){
    base = new JPanel();
    base.setPreferredSize(new Dimension(130,130));
    base.add(pane);
    //pane = base;
  }*/
  
   /*** Create Panel ***/
  /*public void panelInit(){
    pane = new JPanel();
    pane.setPreferredSize(new Dimension(190, 50));

  }
  */

   /*** Create TextBox ***/
    public void textBox(){
    text1 = new JTextField();
    text1.setColumns(4);
    pane.add(text1);
  }
   public void textBox(String str){
    text1 = new JTextField();
    text1.setColumns(4);
    text1.setText(str);
    pane.add(text1);
  }
  
  
   /*** Create Label ***/
   public void Label(int select){
    jLabel = new JLabel();
    
    switch(select){
    case 1: jLabel.setText("を");
            break;
    case 2: jLabel.setText("秒待つ");
            break;
    default:jLabel.setText("");
    }
    
    pane.add(jLabel);
  }
   
   public void Label(String str){
     jLabel = new JLabel(str);
     pane.add(jLabel);
   }
   
   /*** Create ComboBox ***/
    public void comboBox() {
    com1= new DefaultComboBoxModel(combo1);
    com2= new DefaultComboBoxModel(combo2);
    com3= new DefaultComboBoxModel(combo3);
    com4= new DefaultComboBoxModel(combo4);
    initComponents();
    jComboBox1.setModel(com1);
    jComboBox2.setModel(com2);

    pane.add(jComboBox1);
    Label(1);
    pane.add(jComboBox2);
}

    /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the Form Editor.
    */
    // <editor-fold defaultstate="collapsed" desc=" 生成されたコード">
    public void initComponents() {
    jComboBox1 = new JComboBox();
    jComboBox2 = new JComboBox();

    //getContentPane().setLayout(null);
    /*setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);*/
    
    jComboBox1.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
    jComboBox1ActionPerformed(evt);
    }
    });
    }
    /*getContentPane().add(jComboBox1);
    jComboBox1.setBounds(50, 60, 110, 21);

    getContentPane().add(jComboBox2);
    jComboBox2.setBounds(250, 60, 110, 21);

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width-408)/2, (screenSize.height-332)/2, 408, 332);
    }*/
    // </editor-fold>

    /***ComboBox1の動作で、ComboBox2を連動させる***/
    public void jComboBox1ActionPerformed(ActionEvent evt) {

    if (jComboBox1.getSelectedItem().equals(combo1[0])){
    jComboBox2.setModel(com2);
    }else if (jComboBox1.getSelectedItem().equals(combo1[1])){
    jComboBox2.setModel(com3);
    }else {
    jComboBox2.setModel(com4); //未使用
    }

   //edus_111019 ここに処理コードを追加: edue
}
    /*** END CreateComboBox ***/
    
    
   

    
    @Override
    public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
    }
    
}
  
