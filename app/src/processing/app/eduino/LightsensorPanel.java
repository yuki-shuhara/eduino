package processing.app.eduino;

  import java.awt.Color;
  import java.awt.GradientPaint;
  import java.awt.Graphics;
  import java.awt.Graphics2D;
  import java.awt.Polygon;
  import javax.swing.JLabel;

  import java.awt.Color;
  import java.awt.GradientPaint;
  import java.awt.Graphics;
  import java.awt.Graphics2D;

  import java.awt.Polygon;

  import javax.swing.JLabel;

  public class LightsensorPanel extends PanelTranslate{
    final long blockId = 21;
    
    private boolean nextSetis;
    
    private PanelTranslate nextPanel;
    private PanelTranslate beforePanel;
    
    /**間に挟まるパネルの付け根firstPanel, secondPanel...*/
    //private PanelTranslate firstPanel;
    
    /**グラデーデション用に2色あるだけ*/
    private Color colorright = new Color(191, 191, 191);
    private Color colorleft = new Color(191, 191, 191);
    
    /**タイルサイズ*/
    private int HEIGHT = 50;
    private int WIDTH = 100;

    private int x, y;//このタイルの設置座標

    
    /**NextPanel用のセットポジション*/
    private int xposition = 0;
    private int yposition = HEIGHT;
    private Polygon outLine = new Polygon();
    
    /**間に挟まるパネル用のセットポジション*/
//    private int firstposition_x =this.x + BAR_WIDTH;
//    private int firstposition_y =this.y + TOP_HEIGHT;
//    private Polygon firstOutLine = new Polygon();
    
    /**タイル描画用*/
    private int Xarray[] = {0, 10, 10, WIDTH, WIDTH, 0};
    private int Yarray[] = {0, 0, 10, 10, HEIGHT, HEIGHT};
    private Polygon polygon = new Polygon();
    
    /**ソースコード格納用*/
    private String source="";
    
    /**表示用*/
    JLabel lightLabel;
    
    private int LABEL_X = 20;
    private int LABEL_WIDTH = 80;
    private int LABEL_HEIGHT = 20;
    
    

    LightsensorPanel(int x, int y) {
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
      return blockidArg;
    }
    
    @Override
    public void uncouple(PanelTranslate p){
      //System.out.println("p:"+p+" getnext:"+getNextPanelTranslate());
      if(getNextPanelTranslate() == p){setNextPanelTranslate(null);}
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
      return nextPanel;
    }
    
    @Override
    public PanelTranslate getBeforePanelTranslate(){
      return beforePanel;
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
      setOutLine();
      super.setLocation(x, y);
      
      //
      if(nextPanel != null){
        nextPanel.setPosition(xposition+x, yposition+y);
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
      
//      BasicStroke bs = new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
//      BasicStroke bs = new BasicStroke(0.5f);
//      g2.setStroke(bs);
      
      lightLabel.setBounds(LABEL_X, HEIGHT/2-10, LABEL_WIDTH, LABEL_HEIGHT);
      
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
    public boolean getContain(PanelTranslate p){
      //if(outLine.contains(p.getX(), p.getY())){return true;}
      //if(OutLine.contains(x, y)) return nextPanel;
      
      return false;
    }


    @Override
    public void setPanelTranslate(PanelTranslate p) {
      if(outLine.contains(p.getX(), p.getY())){
        if(getNextPanelTranslate() == null){
          setNextPanelTranslate(p);
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
      }
      
      return;
    }
    
    @Override
    public String code(){
      source = "analogRead(LIGHT)";
      return source;
    }
    
   private void addedParts(){
     lightLabel = new JLabel("明るさ");
     this.add(lightLabel);
     
   }
    
    
    private void setPolygon(){
      polygon = new Polygon(Xarray, Yarray, Xarray.length);
    }
    
    private void setOutLine(){
      //
       outLine.reset();
       outLine.addPoint(0, 10);
       outLine.addPoint(WIDTH, 10);
       outLine.addPoint(WIDTH, HEIGHT);
       outLine.addPoint(WIDTH, HEIGHT); 
       
    }
    public int getHeight(){
      return this.HEIGHT;
    }
    
   
  }


