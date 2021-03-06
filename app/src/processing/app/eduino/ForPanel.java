package processing.app.eduino;

  import java.awt.Color;
  import java.awt.GradientPaint;
  import java.awt.Graphics;
  import java.awt.Graphics2D;

  import java.awt.Polygon;

import javax.swing.JLabel;
import javax.swing.JTextField;



  public class ForPanel extends PanelTranslate{
    final long blockId = 2;

    private boolean nextSetis; 
    
    private PanelTranslate nextPanel;
    private PanelTranslate beforePanel;
    
    /**間に挟まるパネルの付け根firstPanel, secondPanel...*/
    private PanelTranslate firstPanel;
    
    /**グラデーデション用に2色あるだけ*/
    private Color colorright = new Color(250, 192, 143);
    private Color colorleft = new Color(250, 192, 143);
    
    /**タイルサイズ*/
    private int HEIGHT = 120; //=topheight+barheight+bottomheight
    private int WIDTH = 160;
    private int TOP_HEIGHT = 50;
    private int BAR_HEIGHT = 50;
    private int BAR_WIDTH = 20;
    private int BOTTOM_HEIGHT = 20;
    private int x, y;//このタイルの設置座標

    
    
    /**NextPanel用のセットポジション*/
    private int xposition = 0;
    private int yposition = HEIGHT;
    private Polygon outLine = new Polygon();
    
    /**間に挟まるパネル用のセットポジション*/
    private int firstposition_x =BAR_WIDTH;
    private int firstposition_y =TOP_HEIGHT;
    private Polygon firstOutLine = new Polygon();
    
    /**タイル描画用*/
    private Polygon polygon = new Polygon();
    
    /**ソースコード格納用*/
    private String source="";
    
    /**表示用*/
    private JLabel forLabel;
    private JTextField text;
    
    private int LABEL_X = 30;
    private int LABEL_WIDTH = 60;
    private int LABEL_HEIGHT = 20;
    
    

    ForPanel(int x, int y) {
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
    protected void setnextSetis(boolean tf){
      this.nextSetis = tf;
    }
    
    @Override
    public long getBlockId(){
      return blockId;
    }
    
    @Override
    protected long[] getBlockId(long blockidArg[]){
      blockidArg[(int)blockidArg[0]++] = this.getBlockId();
      PanelTranslate p = firstPanel;
      while(p != null){
        blockidArg = p.getBlockId(blockidArg);
        p = p.getNextPanelTranslate();
      }
      return blockidArg;
    }
    
    @Override
    public void uncouple(PanelTranslate p){
      if(getNextPanelTranslate() == p){setNextPanelTranslate(null);}
      if(firstPanel == p){firstPanel = null;}
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
      
//      BasicStroke bs = new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
//      BasicStroke bs = new BasicStroke(0.5f);
//      g2.setStroke(bs);
      
      text.setBounds(LABEL_X, 15, 20, LABEL_HEIGHT);
      forLabel.setBounds(LABEL_X+25, 15, LABEL_WIDTH, LABEL_HEIGHT);
      setPolygon();
      setOutLine();
      
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
    public boolean getContain(PanelTranslate p){
      if(firstOutLine.contains(p.getX(), p.getY())){return true;}
      if(outLine.contains(p.getX(), p.getY())){return true;}
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
        return;
      }
      //
      if(outLine.contains(p.getX(), p.getY())){
        if(nextPanel == null){
          nextPanel = p;
          p.setBeforePanelTranslate(this);
          setPosition(this.x, this.y);
        }
        else{        
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
       source = "for(int i = 0; i < "+text.getText()+"; i++){\n";
      PanelTranslate panel = firstPanel;
      while(panel != null){
        source = source + panel.code();
        panel = panel.getNextPanelTranslate();
      }
        source = source + "}\n";
        
      return source;
    }
    
   private void addedParts(){
     forLabel = new JLabel("回繰り返す");
     this.add(forLabel);
     
     text = new JTextField("1");
     text.setColumns(3);
     super.add(text);
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
       
       outLine.reset();
       outLine.addPoint(this.x, this.y+TOP_HEIGHT+BAR_HEIGHT);
       for(int i=0; i<3; i++){
         outLine.addPoint(this.x+polygon.xpoints[i+5], this.y+polygon.ypoints[i+5]);
       }
    }

    private void setHEIGHT(){
      HEIGHT = TOP_HEIGHT+BAR_HEIGHT+BOTTOM_HEIGHT;
      yposition = HEIGHT;
    }
    

   
    public int getHeight(){
      return HEIGHT;
    }
  
}
