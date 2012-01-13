package processing.app;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Polygon;

import javax.swing.JLabel;

public class SegPanel extends PanelTranslate{
  
  static int HEIGHT = 100;
  static int WIDTH = 130;
  static int TOP_HEIGHT = 40;
  static int BOTTOM_HEIGHT = 20;
  static int BAR_WIDTH = 20;
//  static Color color = Color.blue;
  static long blockid;

  JLabel label;
  private boolean middle;
  private boolean middleset=false;
  
  PanelTranslate middlePanel = null;
  
  Polygon topOutLine;
  
//  LoopPanel(){
//    super(blockid);
//    
//  }
  SegPanel(){
    super(WIDTH, HEIGHT, Color.blue, Color.cyan, 0, HEIGHT);
    setPolygon();
    addedParts();
  }
  

  void addedParts(){
      label = new JLabel("７セグメント表示");
      super.add(label);
  }
  
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    label.setBounds(WIDTH/10, TOP_HEIGHT/2-10, WIDTH-WIDTH/5, 20);
  }

  public void setPolygon(){
    int Xarray[] = {0, WIDTH, WIDTH, BAR_WIDTH, BAR_WIDTH, WIDTH, WIDTH, 0};
    int Yarray[] = {0, 0, TOP_HEIGHT, TOP_HEIGHT, HEIGHT-BOTTOM_HEIGHT, HEIGHT-BOTTOM_HEIGHT, HEIGHT, HEIGHT};
    Polygon polygon = new Polygon(Xarray, Yarray, Xarray.length);
    super.polygon = polygon;
    this.setOutLine();
  }
  
  @Override
  public boolean getContains(int x, int y){
    boolean result;
    middle = false;
    result = topOutLine.contains(x,y);
    if(result){
      middle = true;
      return result;
    }
    else{
      return super.getContains(x, y);
    }
  }

  @Override
  public void setbeforePanelTranslate(PanelTranslate beforePanel){
    if(beforePanel==null){
      if(middlePanel != null){
        middlePanel = null;
      }
      if(beforePanelTranslate != null){
        beforePanelTranslate.setNextPanelTranslate(null);
        beforePanelTranslate = null;
      }
      return;
    }
    beforePanelTranslate = beforePanel;
  }
  
  
  @Override
  public void setNextPanelTranslate(PanelTranslate nextPane){
    if(middle){
      if(middlePanel == null){
        if(nextPane.getNextPanelTranslate() == null){
          middleset=true;
          middlePanel = nextPane;
          nextPane.setbeforePanelTranslate(this);
          middlePanel.setLocation(super.x+BAR_WIDTH ,super.y+TOP_HEIGHT);
        }
      }
      
    }
    else{
      super.setNextPanelTranslate(nextPane);
    }
  }
  
  @Override
  protected void setOutLine(){
    int px, py;
 
    outLine = new Polygon();
    topOutLine = new Polygon();
    
    for(int i=0; i < 3; i++){
      px = polygon.xpoints[i];
      py = polygon.ypoints[i];
      topOutLine.addPoint(px+x, py+y);
    }
    px = 0;
    py = polygon.ypoints[3];
    topOutLine.addPoint(px+x, py+y);
    
    
    px = polygon.xpoints[4]+BAR_WIDTH;
    py = polygon.ypoints[4];
    super.outLine.addPoint(px+x, py+y);

    for(int i=5; i < 8; i++){

      px = polygon.xpoints[i];
      py = polygon.ypoints[i];
      super.outLine.addPoint(px+x, py+y);
    }
    
       //outLine = new Polygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
  }
  
  @Override
  public void setLocation(int x, int y){
    super.setLocation(x, y);
    if(middlePanel != null){
      middlePanel.setLocation(super.x+BAR_WIDTH ,super.y+TOP_HEIGHT);
    }
  }
  
  public String code(){
    String source="//7seg,Start";
    
   if(middlePanel != null){ 
     source = source  + middlePanel.code();
   }
   
   source = source + "//7Seg,End\n";
   
   return source;
  }
}