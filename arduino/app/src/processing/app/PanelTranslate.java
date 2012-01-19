package processing.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

abstract class PanelTranslate extends JPanel {

  private PanelTranslate nextPanel;
  private PanelTranslate beforePanel;
  
  final long blockId;
  

    
  abstract String code(); //スケッチコード
  abstract public boolean inline(int x, int y); //クリック時のポリゴン内外の判定用 true=in false=out
  abstract public PanelTranslate checkSetPosition(int x, int y); //内部処理用連結 return→ firstPosition, secondPosition, nextPanel, beforePanel
  abstract public void setPanelTranslate(PanelTranslate p);
  abstract public void setPosition();//くっついてるタイルの移動処理用
  abstract public boolean getContain(int x, int y);
  
  PanelTranslate(long blockId){
    this.blockId = blockId;
    reset();
    this.setOpaque(false);
  }
  
  public void reset(){
    nextPanel = null;
    beforePanel = null;
  }
  
  public PanelTranslate getNextPanelTranslate(){
    return nextPanel;
  }
  
  public PanelTranslate getBeforePanelTranslate(){
    return beforePanel;
  }
  
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
  }
  
  @Override
  public void setLocation(int x, int y){//外見処理用連結
    super.setLocation(x, y);
    
    setPosition();
    if(this.nextPanel != null){
      nextPanel.setLocation(x, y);
    }
  }


}     

