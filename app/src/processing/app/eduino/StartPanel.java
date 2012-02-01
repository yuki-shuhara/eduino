package processing.app.eduino;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Polygon;

import javax.swing.JLabel;



public class StartPanel extends PanelTranslate{
  final static long blockId = 0;
  
  private final boolean nextSetis;
  
  /**StartPanelでは不要*/
  private PanelTranslate nextPanel;
  private PanelTranslate beforePanel;
  
  /**間に挟まるパネルの付け根firstPanel, secondPanel...*/
  private PanelTranslate firstPanel;
  
  /**グラデーデション用に2色あるだけ*/
  private Color colorright = new Color(255,192,203);
  private Color colorleft = new Color(255,192,203);
  
  /**タイルサイズ*/
  private int HEIGHT = 160; //=topheight+barheight+bottomheight
  private int WIDTH = 180;
  private int TOP_HEIGHT = 50;
  private int BAR_HEIGHT = 60;
  private int BAR_WIDTH = 20;
  private int BOTTOM_HEIGHT = 50;
  private int x, y;//このタイルの設置座標

  
  
  /**NextPanel用のセットポジション*/
//  private int xposition = 0;
//  private int yposition = HEIGHT;
  
  /**間に挟まるパネル用のセットポジション*/
  private int firstposition_x = BAR_WIDTH;
  private int firstposition_y = TOP_HEIGHT;
  private Polygon firstOutLine = new Polygon();
  
  /**タイル描画用*/
  private Polygon polygon = new Polygon();
  
  /**ソースコード格納用*/
  private String source="";
  
  /**表示用*/
  private JLabel startLabel;
  private JLabel endLabel;
  
  private int LABEL_X = 60;
  private int LABEL_WIDTH = 100;
  private int LABEL_HEIGHT = 20;
  
  

  StartPanel(int x, int y) {
    firstPanel = null;
    this.x = x;
    this.y = y;
    nextPanel = null;
    beforePanel = null;
    nextSetis=true;
    setPolygon();
    setOutLine();
    addedParts();
    super.setBounds(this.x, this.y, WIDTH, HEIGHT);
  }
  
  @Override
  public boolean getnextSetis(){
    return nextSetis;
  }
  
  @Override
  public long getBlockId(){
    return blockId;
  }
  
  @Override
  public void uncouple(PanelTranslate p){
    if(firstPanel == p){firstPanel = null;}
    //if
  }
  
  @Override
  public void uncouple(){
//
  }
  @Override
  public PanelTranslate getNextPanelTranslate(){
    return nextPanel;
  }
  
  @Override
  public PanelTranslate getBeforePanelTranslate(){
    return beforePanel;
  }
  
  @Override
  public void setNextPanelTranslate(PanelTranslate np){
    //nextPanel = np;
    return;
  }
  
  @Override
  public void setBeforePanelTranslate(PanelTranslate bp){
    //beforePanel = bp;
    return;
  }
  @Override
  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
    setOutLine();
    super.setLocation(x, y);
    
    //
    if(firstPanel != null){
      firstPanel.setPosition(firstposition_x+x, firstposition_y+y);
    }
    //
    //
    //if(out...
    return;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g.create();
    //polygon.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    
    int sum = 0;
    PanelTranslate t = firstPanel;
    while(t != null){
      sum = sum + t.getHeight();
      t = t.getNextPanelTranslate();
    }
    if(sum < 1) sum = 50;
    BAR_HEIGHT = sum;
    setHEIGHT();
    super.setSize(WIDTH, HEIGHT);
    //this.setPolygon();
    
//    BasicStroke bs = new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
//    BasicStroke bs = new BasicStroke(0.5f);
//    g2.setStroke(bs);
    
    startLabel.setBounds(LABEL_X, 15, LABEL_WIDTH, LABEL_HEIGHT);
    endLabel.setBounds(LABEL_X, TOP_HEIGHT+BAR_HEIGHT+15, LABEL_WIDTH, LABEL_HEIGHT);
    setPolygon();
    setOutLine();
    
    GradientPaint gp = new GradientPaint(0f, 0f, colorleft, (float)WIDTH, (float)HEIGHT, colorright);
    g2.setPaint(gp);
    g2.fill(polygon);
    g2.dispose();
  }

  
  @Override
  public boolean inline(int px, int py) {
   return polygon.contains(px, py);
  }
  
  @Override
  public boolean getContain(int px, int py){
    if(firstOutLine.contains(px, py)){return true;}
    //if(OutLine.contains(x, y)) return nextPanel;
    
    return false;
  }


  @Override
  public void setPanelTranslate(PanelTranslate p) {
    if(firstOutLine.contains(p.getX(), p.getY())){
      if(firstPanel == null){
        firstPanel = p;
        p.setBeforePanelTranslate(this);
        setPosition(this.x, this.y);
      }
      else{        
        PanelTranslate lastPanel = p;
        while(lastPanel.getNextPanelTranslate() != null){
          lastPanel = lastPanel.getNextPanelTranslate();
        }
        firstPanel.setBeforePanelTranslate(lastPanel);
        lastPanel.setNextPanelTranslate(firstPanel);
        
        firstPanel = p;
        p.setBeforePanelTranslate(this);
        setPosition(this.x, this.y);
      }
    }
    return;
  }
  
  @Override
  public String code(){
     source = "void loop(){\n";
     
    PanelTranslate panel = firstPanel;
    while(panel != null){
      source = source + panel.code();
      panel = panel.getNextPanelTranslate();
    }
      source = source + "exit(1);\n}\n";
      
    return source;
  }
  
 private void addedParts(){
   startLabel = new JLabel("プログラム開始");
   endLabel = new JLabel("プログラム終了");
   this.add(startLabel);
   this.add(endLabel);
 }
  
  
  private void setPolygon(){
    int Xarray[] = {0, WIDTH, WIDTH, BAR_WIDTH, BAR_WIDTH, WIDTH, WIDTH, 0};
    int Yarray[] = {0, 0, TOP_HEIGHT, TOP_HEIGHT, TOP_HEIGHT+BAR_HEIGHT, TOP_HEIGHT+BAR_HEIGHT, HEIGHT, HEIGHT};
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
     
  }

  private void setHEIGHT(){
    HEIGHT = TOP_HEIGHT+BAR_HEIGHT+BOTTOM_HEIGHT;
  }
 
  public int getHeight(){
    return HEIGHT;
  }
  
}
