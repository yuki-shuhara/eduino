package processing.app;

import java.awt.Polygon;

public class NonPanel extends PanelTranslate{
  
  static int HEIGHT = 0;
  static int WIDTH = 0;
  
  NonPanel(){
    super(HEIGHT, WIDTH);
  }
  @Override
  public String code() {
    return null;
  }

  @Override
  void setPolygon() {
    int Xarray[] = {0, WIDTH, WIDTH, 20, 20, WIDTH, WIDTH, 0};
    int Yarray[] = {0, 0, 50, 50, HEIGHT-30, HEIGHT-30, HEIGHT, HEIGHT};
    Polygon polygon = new Polygon(Xarray, Yarray, Xarray.length);
    super.polygon = polygon;
  }
  

}
