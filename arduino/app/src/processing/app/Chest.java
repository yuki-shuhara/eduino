package processing.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import processing.app.Editor.FileDropHandler;
import processing.app.Editor.TextAreaPopup;
import processing.app.syntax.JEditTextArea;
import processing.app.syntax.PdeTextAreaDefaults;



public class Chest extends Editor implements ActionListener {

  public int count = 10; //倉庫パネル表示数
  public int PANEL_MAX = 1000; //最大パネル作成可能数
  public int LINE = 280; //境界線

  public CreatePanel[] factory; //パネル作成クラス。ActionListenerも含まれている
  public MyMouseListener mylistener; //Mouseイベント関連
  
  public JPanel[] basepane;
  public JPanel[] pane;
  public JPanel dummy;
  public JPanel creaPanel; //透明のパネル
  public JPanel field; //Chestクラスの土台パネル
  public JPanel workspace;
  public JPanel scroll;
  public JScrollPane warehouse;
  private int panelcount;
  
   
  Chest(Base ibase, String path, int[] location){
    //super(ibase, path, location);
    this.base = ibase;

    Base.setIcon(this);

    // Install default actions for Run, Present, etc.
    resetHandlers();

//edus_111012-comentout
    // add listener to handle window close box hit event
    addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          base.handleClose(Chest.this);
        }
      });
    // don't close the window when clicked, the app will take care
    // of that via the handleQuitInternal() methods
    // http://dev.processing.org/bugs/show_bug.cgi?id=440
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    // When bringing a window to front, let the Base know
    addWindowListener(new WindowAdapter() {
        public void windowActivated(WindowEvent e) {
//          System.err.println("activate");  // not coming through
          base.handleActivated(Chest.this);
          // re-add the sub-menus that are shared by all windows
          fileMenu.insert(sketchbookMenu, 2);
          fileMenu.insert(examplesMenu, 3);
          sketchMenu.insert(importMenu, 4);
          toolsMenu.insert(boardsMenu, numTools);
          toolsMenu.insert(serialMenu, numTools + 1);
        }

        // added for 1.0.5
        // http://dev.processing.org/bugs/show_bug.cgi?id=1260
        public void windowDeactivated(WindowEvent e) {
//          System.err.println("deactivate");  // not coming through
          fileMenu.remove(sketchbookMenu);
          fileMenu.remove(examplesMenu);
          sketchMenu.remove(importMenu);
          toolsMenu.remove(boardsMenu);
          toolsMenu.remove(serialMenu);
        }
      });
//edue
    
    //PdeKeywords keywords = new PdeKeywords();
    //sketchbook = new Sketchbook(this);

    if (serialMonitor == null) {
      serialMonitor = new SerialMonitor(Preferences.get("serial.port"));
      serialMonitor.setIconImage(getIconImage());
    }
    
    buildMenuBar();

    // For rev 0120, placing things inside a JPanel
    Container contentPain = getContentPane();
    contentPain.setLayout(new BorderLayout());
    JPanel pain = new JPanel();
    pain.setLayout(new BorderLayout());
    contentPain.add(pain, BorderLayout.CENTER);

    Box box = Box.createVerticalBox();
    Box upper = Box.createVerticalBox();
      
    field = new JPanel();
    workspace = new JPanel();
    scroll = new JPanel(); //スクロールバーの枠内に表示するパネル
    dummy = new JPanel(); //CreaPanel上でのみ動かされるパネル
 
    field.setLayout(null); //土台パネル
    scroll.setLayout(null);
    
    /*** ↓これがないとスクロールバーが正常に表示されない↓ ***/
    scroll.setPreferredSize(new Dimension(280, 600)); 
    /*** setLayout=Nullではスクロールバーが使えないが、setPref～(new Dimension(x, y))を用いることで表示させることが可 ***/
  
    mylistener = new MyMouseListener();
    dummy.addMouseListener(mylistener); //ダミーパネルを登録
    dummy.addMouseMotionListener(mylistener);
    
    panelcountinit(); //panelcount初期化
   
    factory = new CreatePanel[1000]; 
    factory[0] = new CreatePanel();

    basepane = new JPanel[10]; //10種類のパネルの生成が可能
    pane = new JPanel[1000];
    int y = 20;

    /*** basepanel[]は画面左側のscrollに表示するためだけのパネル ***/
    for(int i=0; i < count; i++){
      basepane[i] = new JPanel();
      basepane[i] = factory[0].create(i);
      basepane[i].setLocation(20, y);
      
      /*** リスナー登録はListenerとMotionListenerの２つが必要 ***/
      basepane[i].addMouseListener(mylistener); //リスナー登録
      basepane[i].addMouseMotionListener(mylistener);
      scroll.add(basepane[i]);
      y = y + panelHeight(basepane[i]) + 20; 

      /*** どのパネルか判別するためのint IDを記載 
       * 1 = Loopパネル
       * 2 = 出力パネル
       * 3 = 遅延パネル
       * 4～ defaultで遅延パネルが作成される***/
      
      basepane[i].setName(Integer.toString(i));
    }

    creaPanel = new JPanel();
    creaPanel.setLayout(null);
    /*** CreaPanelを透明化***/
    creaPanel.setOpaque(false);
    
    creaPanel.setBounds(0, 0, 600, 600);
    workspace.setBounds(280, 0, 400, 600);
    

    warehouse = new JScrollPane(scroll);
    warehouse.setBounds(0, 0, 280, 400);
    warehouse.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    field.add(creaPanel);
    field.add(warehouse);
   

    
//edue
    
    
    if (toolbarMenu == null) {
      toolbarMenu = new JMenu();
      base.rebuildToolbarMenu(toolbarMenu);
    }
    toolbar = new EditorToolbar(this, toolbarMenu);
    upper.add(toolbar);

    header = new EditorHeader(this);
    
//edus_111012 -comentout
    /*upper.add(header);*/
//edue
    
    textarea = new JEditTextArea(new PdeTextAreaDefaults());
    textarea.setRightClickPopup(new TextAreaPopup());
    textarea.setHorizontalOffset(6);

    // assemble console panel, consisting of status area and the console itself
    consolePanel = new JPanel();
    consolePanel.setLayout(new BorderLayout());

    status = new EditorStatus(this);
    consolePanel.add(status, BorderLayout.NORTH);

    console = new EditorConsole(this);
    // windows puts an ugly border on this guy
    console.setBorder(null);
    lineStatus = new EditorLineStatus(textarea);

//edus_11101X -added
    
//edue
    
//edus_111012 -comentout
    consolePanel.add(console, BorderLayout.CENTER);
    consolePanel.add(lineStatus, BorderLayout.SOUTH);
//edue
    
//edus -testplay
    upper.add(field);
    //upper.add(textarea);
//edue
 
    splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                               upper, consolePanel);

    splitPane.setOneTouchExpandable(true);
    // repaint child panes while resizing
    splitPane.setContinuousLayout(true);
    // if window increases in size, give all of increase to
    // the textarea in the uppper pane
    splitPane.setResizeWeight(1D);

    // to fix ugliness.. normally macosx java 1.3 puts an
    // ugly white border around this object, so turn it off.
    splitPane.setBorder(null);

    // the default size on windows is too small and kinda ugly
    int dividerSize = Preferences.getInteger("editor.divider.size");
    if (dividerSize != 0) {
      splitPane.setDividerSize(dividerSize);
    }
    
    splitPane.setMinimumSize(new Dimension(600, 400));
    box.add(splitPane);
   
    
    // hopefully these are no longer needed w/ swing
    // (har har har.. that was wishful thinking)
    listener = new EditorListener(this, textarea);
    pain.add(box);

    // get shift down/up events so we can show the alt version of toolbar buttons
    textarea.addKeyListener(toolbar);

    pain.setTransferHandler(new FileDropHandler());

//    System.out.println("t1");

    // Finish preparing Editor (formerly found in Base)
    pack();

//    System.out.println("t2");

    // Set the window bounds and the divider location before setting it visible
    setPlacement(location);


    // If the window is resized too small this will resize it again to the
    // minimums. Adapted by Chris Lonnen from comments here:
    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4320050
    // as a fix for http://dev.processing.org/bugs/show_bug.cgi?id=25
    final int minW = Preferences.getInteger("editor.window.width.min");
    final int minH = Preferences.getInteger("editor.window.height.min");
    addComponentListener(new java.awt.event.ComponentAdapter() {
        public void componentResized(ComponentEvent event) {
          setSize((getWidth() < minW) ? minW : getWidth(),
                  (getHeight() < minH) ? minH : getHeight());
        }
      });

//    System.out.println("t3");

    // Bring back the general options for the editor
    applyPreferences();

//    System.out.println("t4");

    // Open the document that was passed in
    boolean loaded = handleOpenInternal(path);
    if (!loaded) sketch = null;

//    System.out.println("t5");

    // All set, now show the window
    //setVisible(true);
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    
  }
  
  public void panelcountinit(){
    panelcount = 0;
  }
  
  public int panelHeight(JPanel p){
    return p.getHeight();
  }
  
  
  public void compile() {
    String str = new String();
    str = "void setup() {pinMode(13, OUTPUT);}void loop() {digitalWrite(13, HIGH); delay(1000); igitalWrite(13, LOW);  delay(1000); }";
    
    super.textarea.setText(str);
    
//    editor.textarea = this.textarea;
  }
  

  
  class MyMouseListener extends MouseAdapter{
    private int dx;
    private int dy;
    private int x=0; //パネルのｘ座標
    private int y=0; //パネルのy座標
    private int tx=0, ty=0; //記憶用の変数
    private boolean flag;
 
 
    public void mouseDragged(MouseEvent e){
      x = e.getXOnScreen() - dx;
      y = e.getYOnScreen() - dy;
      
      if(x != tx || y !=ty){
        /*** 以前より動いていれば再描画を行う ***/
        repaint();
      }

      dummy.setLocation(x, y);
      tx = x;
      ty = y;
    }
    
     
    public void mousePressed(MouseEvent e){
      flag = false; //フラグの誤作動防止のためfalseをセット
      dummy = new JPanel();
      
      if(checkLine(e)){ 
        /*** fieldにおいて取得したパネルをそのまま動かす ***/
        dummy = getJPanel(e);
        field.remove(getJPanel(e));
        creaPanel.add(dummy);
      }
      else{
        /*** scrollにおいてクリックされたパネルの複製を作成する ***/
        flag = true;
        factory[0] = new CreatePanel();
        dummy = factory[0].create(getID(e));
        dummy.setLocation(((JPanel)e.getComponent()).getX(),((JPanel)e.getComponent()).getY());
        creaPanel.add(dummy);
        panelRepaint(dummy);
        dummy.setName(Integer.toString(getID(e)));   //IDセット   
      }

         repaint();

      dx = e.getXOnScreen() - dummy.getX();
      dy = e.getYOnScreen() - dummy.getY();

    }
    
    public void mouseReleased(MouseEvent e){
      //System.out.println("release");
      if(checkLine()){
        x = (x / 5) * 5;
        y = (y / 5) * 5; //座標を微調整
        dummy.setLocation(x, y);
        
        if(panelcount>PANEL_MAX){
           //もうパネル作れないよ、とエラー出す
        }
        
        if(flag){
          /*** flag=true のときはscrollから新たなパネルが生成されたことを意味する。
           * よって、ここで新たに生成されたパネルを作業パネル用としてpane[]に登録する。***/
          pane[panelcount] = new JPanel();
          factory[panelcount+1] = new CreatePanel();
          pane[panelcount] = factory[panelcount+1].create(getID(dummy));
          pane[panelcount].setLocation(dummy.getX(), dummy.getY());
          pane[panelcount].addMouseListener(mylistener);
          pane[panelcount].addMouseMotionListener(mylistener);
          field.add(pane[panelcount]);
          panelRepaint(pane[panelcount]);
          panelcount++;
        }
        else{
          /*** flag=false　のときは既存のパネルが選択されて動かされていることを意味する。
           * よって、選択されているパネルをfieldに追加するだけで処理を終える***/
          field.add(dummy);
        }
      }
      /*** パネル追加の処理があろうとなかろうと、CreaPanelは常に空の状態を保つ ***/
      creaPanel.remove(dummy);
      repaint();
    }
    
    public void  mouseMoved(MouseEvent e) {
      //System.out.println("Move");
    }
    
    public void mouseClicked(MouseEvent e){
      //System.out.println("Click");
    }
    
    public void mouseEntered(MouseEvent e){
      //System.out.println("Enter");
    }
    
    private boolean checkLine(MouseEvent e){
      if(((JPanel)e.getComponent()).getX()<LINE){
        return false;
      }
      else{
        return true;
      }
    }
    
    private boolean checkLine(){
      if(dummy.getX() < LINE){
        return false;}
      else{
        return true;
      }
    }
    
    /*** 再描画用の関数。JComponentのrepaint()とは違う ***/
    private void panelRepaint(JPanel p){
      p.invalidate();
      validate();
    }
    
    private int getID(MouseEvent e){
      return Integer.parseInt((e.getComponent()).getName());
    }
    
    private int getID(JPanel p){
      return Integer.parseInt(p.getName());
    }
    
    private JPanel getJPanel(MouseEvent e){
      return (JPanel)e.getComponent();
    }
    
  }//*** end MyMouseListener ***//
}//*** end chest_class ***//

