package processing.app.eduino;

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

  abstract public boolean getnextSetis(); 
  abstract String code(); //スケッチコード
  abstract public long getBlockId(); 
  
  abstract public boolean inline(int x, int y); //クリック時のポリゴン内外の判定用 true=in false=out
  abstract public boolean getContain(int x, int y);
  
  abstract public void setPanelTranslate(PanelTranslate p); //内部処理用連結 return→ firstPosition, secondPosition, nextPanel, beforePanel
  abstract public void setPosition(int x, int y);//くっついてるタイルの移動処理用

  abstract public void uncouple(PanelTranslate p);
  abstract public void uncouple();//クリック時のタイル引き離しメソッド
  
  abstract public PanelTranslate getNextPanelTranslate();
  abstract public PanelTranslate getBeforePanelTranslate();
  abstract public void setNextPanelTranslate(PanelTranslate np);
  abstract public void setBeforePanelTranslate(PanelTranslate bp);
  
  abstract public int getHeight();
  
  PanelTranslate(){
    this.setOpaque(false);
  }
  
  
//  @Override
//  public void paintComponent(Graphics g){
//    super.paintComponent(g);
//  }
  
//  @Override
//  public void setLocation(int x, int y){//外見処理用連結
//    this.setLocation(x, y);
//
//    setPosition(x, y);
////    if(this.nextPanel != null){
////      System.out.println("nextPanel"+nextPanel);
////      nextPanel.setLocation(x, y);
////    }
//  }


}     

