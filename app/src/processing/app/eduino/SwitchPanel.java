package processing.app.eduino;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;



public class SwitchPanel extends PanelTranslate implements ActionListener{
  final static long blockId = 6;


  private PanelTranslate nextPanel;
  private PanelTranslate beforePanel;
  
  /*間に挟まるパネルの付け根firstPanel, secondPanel...*/
  private PanelTranslate firstPanel;
  private PanelTranslate secondPanel;
  
  /*グラデーデション用に2色あるだけ*/
  private Color colorright = new Color(149, 179, 215);
  private Color colorleft = new Color(149, 179, 215);
  
  /*タイルサイズ*/
  private int HEIGHT = 220; //=topheight*2+barheight1+barheight2++bottomheight
  private int WIDTH = 160;
  private int TOP_HEIGHT = 50;
  private int FIRST_BAR_HEIGHT = 20;
  private int SECOND_BAR_HEIGHT = 20;
  private int BAR_WIDTH = 20;
  private int BOTTOM_HEIGHT = 20;
  private int x, y;//このタイルの設置座標

  
  
  /*NextPanel用のセットポジション*/
  private int xposition = 0;
  private int yposition = HEIGHT;
  private Polygon outLine = new Polygon();
  
  /*間に挟まるパネル用のセットポジション*/
  private int firstposition_x =BAR_WIDTH;
  private int firstposition_y =TOP_HEIGHT;
  private Polygon firstOutLine = new Polygon();
  
  private int secondposition_x =BAR_WIDTH;
  private int secondposition_y =TOP_HEIGHT*2+FIRST_BAR_HEIGHT;
  private Polygon secondOutLine = new Polygon();
  
  /*タイル描画用*/
  private Polygon polygon = new Polygon();
  
  /*ソースコード格納用*/
  private String source="";
  private String select="WhiteSw";
  
  /*表示用*/
  private JLabel ifLabel;
  private JLabel elseLabel;
  
  JComboBox switchCombo;
  DefaultComboBoxModel com;
  public Object [] combostr ={"白スイッチ", "橙スイッチ", "赤スイッチ"};
  
  private int LABEL_X = 20;
  private int LABEL_WIDTH = 75;
  private int LABEL_HEIGHT = 20;
  

  SwitchPanel(int x, int y) {
    firstPanel = null;
    secondPanel = null;
    this.x = x;
    this.y = y;
    nextPanel = null;
    beforePanel = null;
    setPolygon();
    setOutLine();
    addedParts();
    super.setBounds(this.x, this.y, WIDTH, HEIGHT);
  }
  @Override
  public long getBlockId(){
    return blockId;
  }
  
  @Override
  public void uncouple(PanelTranslate p){
    if(getNextPanelTranslate() == p){setNextPanelTranslate(null);}
    if(firstPanel == p){firstPanel = null;}
    if(secondPanel == p){secondPanel = null;}

    //if
  }
  
  @Override
  public void uncouple(){
    if(getBeforePanelTranslate() != null){
      getBeforePanelTranslate().uncouple(this);
      setBeforePanelTranslate(null);
    }
  }
  @Override
  public PanelTranslate getNextPanelTranslate(){
    return this.nextPanel;
  }
  
  @Override
  public PanelTranslate getBeforePanelTranslate(){
    return this.beforePanel;
  }
  
  @Override
  public void setNextPanelTranslate(PanelTranslate np){
    nextPanel = np;
    return;
  }
  
  @Override
  public void setBeforePanelTranslate(PanelTranslate bp){
    beforePanel = bp;
    return;
  }
  @Override
  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
    setHEIGHT();
    setOutLine();

    super.setLocation(x, y);
    
    //
    if(firstPanel != null){
      firstPanel.setPosition(firstposition_x+x, firstposition_y+y);
    }
    //
    if(secondPanel != null){
      secondPanel.setPosition(secondposition_x+x, secondposition_y+y);
    //
    }
    if(nextPanel != null){
      nextPanel.setPosition(xposition+x, yposition+y);
    }
    //
    //if(out...
    return;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    //polygon.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    
    int firstSum = 0;
    PanelTranslate t = firstPanel;
    while(t != null){
      firstSum += t.getHeight();
      t = t.getNextPanelTranslate();
    }
    if(firstSum< 1) firstSum = 20;
    FIRST_BAR_HEIGHT = firstSum;
    
    
    int secondSum = 0;
    PanelTranslate s = secondPanel;
    while(s != null){
      secondSum += s.getHeight();
      s = s.getNextPanelTranslate();
    }
    if(secondSum < 1) secondSum = 20;
    SECOND_BAR_HEIGHT = secondSum;
    
    setHEIGHT();
    super.setSize(WIDTH, HEIGHT);
    //this.setPolygon();
    
//    BasicStroke bs = new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
//    BasicStroke bs = new BasicStroke(0.5f);
//    g2.setStroke(bs);
    
    switchCombo.setBounds(LABEL_X, 15, LABEL_WIDTH, LABEL_HEIGHT);
    ifLabel.setBounds(LABEL_X+LABEL_WIDTH, 15, LABEL_WIDTH, LABEL_HEIGHT);
    elseLabel.setBounds(LABEL_X+5, TOP_HEIGHT+FIRST_BAR_HEIGHT+15, LABEL_WIDTH, LABEL_HEIGHT);
    
    
    setPolygon();
    setOutLine();
    Graphics2D g2 = (Graphics2D)g.create();
    GradientPaint gp = new GradientPaint(0f, 0f, colorleft, (float)WIDTH, (float)HEIGHT, colorright);
    g2.setPaint(gp);
    g2.fill(this.polygon);
    g2.dispose();
  }

  
  @Override
  public boolean inline(int px, int py) {
   return polygon.contains(px, py);
  }
  
  @Override
  public boolean getContain(int px, int py){
    if(firstOutLine.contains(px, py)){return true;}
    if(secondOutLine.contains(px, py)){return true;}
    if(outLine.contains(px, py)){return true;}
    
    return false;
  }


  @Override
  public void setPanelTranslate(PanelTranslate p) {
    //if文
    if(firstOutLine.contains(p.getX(), p.getY())){
      if(firstPanel == null){
        firstPanel = p;
        p.setBeforePanelTranslate(this);
        setPosition(this.x, this.y);
      }
      else{//割りこみ処理        
        PanelTranslate flastPanel = p;
        while(flastPanel.getNextPanelTranslate() != null){
          flastPanel = flastPanel.getNextPanelTranslate();
        }
        firstPanel.setBeforePanelTranslate(flastPanel);
        flastPanel.setNextPanelTranslate(firstPanel);
        
        firstPanel = p;
        p.setBeforePanelTranslate(this);
        setPosition(this.x, this.y);
      }
      return;
    }
    //else文
    if(secondOutLine.contains(p.getX(), p.getY())){
      if(secondPanel == null){
        secondPanel = p;
        p.setBeforePanelTranslate(this);
        setPosition(this.x, this.y);
      }
      else{//割りこみ処理        
        PanelTranslate slastPanel = p;
        while(slastPanel.getNextPanelTranslate() != null){
          slastPanel = slastPanel.getNextPanelTranslate();
        }
        secondPanel.setBeforePanelTranslate(slastPanel);
        slastPanel.setNextPanelTranslate(secondPanel);
        
        secondPanel = p;
        p.setBeforePanelTranslate(this);
        setPosition(this.x, this.y);
      }
      return;
    }
    //nextPanel用
    if(outLine.contains(p.getX(), p.getY())){
      if(nextPanel == null){
        nextPanel = p;
        p.setBeforePanelTranslate(this);
        setPosition(this.x, this.y);
      }
      else{//割りこみ処理        
        PanelTranslate lastPanel = p;
        while(lastPanel.getNextPanelTranslate() != null){
          lastPanel = lastPanel.getNextPanelTranslate();
        }
        nextPanel.setBeforePanelTranslate(lastPanel);
        lastPanel.setNextPanelTranslate(getNextPanelTranslate());
        
        setNextPanelTranslate(p);
        p.setBeforePanelTranslate(this);
        setPosition(this.x, this.y);
      }
      return;
    }
    
    return;
  }
  
  @Override
  public String code(){
     source = "if(digitalRead(" +select+ ")==HIGH){\n";
    PanelTranslate fpanel = firstPanel;
    while(fpanel != null){
      source = source + fpanel.code();
      fpanel = fpanel.getNextPanelTranslate();
    }
    
      source = source + "}\nelse{\n";
   
      PanelTranslate spanel = secondPanel;
      while(spanel != null){
        source = source + spanel.code();
        spanel = spanel.getNextPanelTranslate();
      }
      
      source = source + "}\n";
      
    return source;
  }
  
 private void addedParts(){
   ifLabel = new JLabel("がONのとき");
   elseLabel = new JLabel("OFFのとき");
   this.add(ifLabel);
   this.add(elseLabel);
   
   switchCombo = new JComboBox();
   switchCombo.addActionListener(this);
   com = new DefaultComboBoxModel(combostr);
   switchCombo.setModel(com);
   this.add(switchCombo);

 }
  
  
  private void setPolygon(){
    int Xarray[] = {0, WIDTH, WIDTH, BAR_WIDTH, BAR_WIDTH, WIDTH, WIDTH, BAR_WIDTH, BAR_WIDTH, WIDTH, WIDTH, 0};
    int Yarray[] = {0, 0, TOP_HEIGHT, TOP_HEIGHT,
        TOP_HEIGHT+FIRST_BAR_HEIGHT, TOP_HEIGHT+FIRST_BAR_HEIGHT, TOP_HEIGHT*2+FIRST_BAR_HEIGHT, TOP_HEIGHT*2+FIRST_BAR_HEIGHT,
        TOP_HEIGHT*2+FIRST_BAR_HEIGHT+SECOND_BAR_HEIGHT, TOP_HEIGHT*2+FIRST_BAR_HEIGHT+SECOND_BAR_HEIGHT, HEIGHT, HEIGHT};
    polygon.reset();
    polygon = new Polygon(Xarray, Yarray, Xarray.length);
  }
  
  private void setOutLine(){
    //
     firstOutLine.reset();
     for(int i=0; i<3; i++){
       firstOutLine.addPoint(this.x+polygon.xpoints[i], this.y+polygon.ypoints[i]);
     }
     firstOutLine.addPoint(this.x, this.y+TOP_HEIGHT);
    
    //
     secondOutLine.reset();
     secondOutLine.addPoint(this.x, this.y+TOP_HEIGHT+FIRST_BAR_HEIGHT);
     for(int i=0; i<2; i++){
       secondOutLine.addPoint(this.x+polygon.xpoints[i+5], this.y+polygon.ypoints[i+5]);
     }
     secondOutLine.addPoint(this.x, this.y+TOP_HEIGHT*2+FIRST_BAR_HEIGHT);
     
     outLine.reset();
     outLine.addPoint(this.x, this.y+TOP_HEIGHT*2+FIRST_BAR_HEIGHT+SECOND_BAR_HEIGHT);
     for(int i=0; i<3; i++){
       outLine.addPoint(this.x+polygon.xpoints[i+9], this.y+polygon.ypoints[i+9]);
     }
  }

  private void setHEIGHT(){
    HEIGHT = TOP_HEIGHT*2+FIRST_BAR_HEIGHT+SECOND_BAR_HEIGHT+BOTTOM_HEIGHT;
    secondposition_y =TOP_HEIGHT*2+FIRST_BAR_HEIGHT;
    yposition = HEIGHT;
  }
 
  public int getHeight(){
    return HEIGHT;
  }
  
  @Override
  public void actionPerformed(ActionEvent list) {
    if(switchCombo.getSelectedItem().equals(combostr[0]))select = "WhiteSw";
    if(switchCombo.getSelectedItem().equals(combostr[1]))select = "OrangeSw";
    if(switchCombo.getSelectedItem().equals(combostr[2]))select = "RedSw"; 
  }
}