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

  
  final long blockId;
  public boolean nextSetis = true;

    
  abstract String code(); //スケッチコード
  abstract public boolean inline(int x, int y); //クリック時のポリゴン内外の判定用 true=in false=out
  abstract public void setPanelTranslate(int x, int y, PanelTranslate p); //内部処理用連結 return→ firstPosition, secondPosition, nextPanel, beforePanel
  abstract public void setPosition(int x, int y);//くっついてるタイルの移動処理用
  abstract public PanelTranslate getContain(int x, int y);
  abstract public void uncouple(PanelTranslate p);
  abstract public void uncouple();
  
  PanelTranslate(long blockId){
    this.blockId = blockId;
    reset();
    this.setOpaque(false);
  }
  
  public void reset(){
    nextPanel = null;
    beforePanel = null;
  }
  
  
  
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
  }
  
  @Override
  public void setLocation(int x, int y){//外見処理用連結
    super.setLocation(x, y);

  System.out.println("nextPanel"+nextPanel);
    setPosition(x, y);
//    if(this.nextPanel != null){
//      System.out.println("nextPanel"+nextPanel);
//      nextPanel.setLocation(x, y);
//    }
  }


}     

