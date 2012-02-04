package processing.app.eduino;

public class SketchHeader {
  
  final int Start = 0;
  final int Loop = 1;
  final int For = 2;
  final int Led = 4;
  final int Delay = 5;
  final int Switch = 6;
  final int Seg = 7;
  final int Temperature = 20;
  final int Lightsensor = 21;

  WorkingSpace workingspace;
  String header;
  String setup;
  String method;
  
  SketchHeader(WorkingSpace WorkingSpace){
    this.workingspace = WorkingSpace;
  }
  
  protected String getSetup(){
    /**don't change blockidArg[0]*/
    long blockidArg[] = new long[workingspace.MAX_PANEL];
    header = "#include <Arduino.h>\n";
    setup ="void setup(){\n";
    method = "";
    
    int tile = 1;
    blockidArg[0] = (long)tile;
    
    PanelTranslate pt = workingspace.getLoop();
    
    if(pt != null){
      blockidArg = pt.getBlockId(blockidArg);
  
      for(int i = 1; i < blockidArg[0]; i++){
        if(blockidArg[i] == Led){
          header = header + "#define LED 13\n";
          setup = setup + "pinMode(LED, OUTPUT);\n";
          break;
        }
      }
      
      for(int i = 1; i < blockidArg[0]; i++){
        if(blockidArg[i] == Switch){
          header = header + "#define WhiteSw 7\n" +
          		              "#define RedSw 8\n" +
          		              "#define OrangeSw 9\n";
          setup = setup + "pinMode(WhiteSw, INPUT);\n"+
          		            "pinMode(RedSw, INPUT);\n" +
          		            "pinMode(OrangeSw, INPUT);\n";
          break;
        }
      }
        
      for(int i = 1; i < blockidArg[0]; i++){
        if(blockidArg[i] == Seg){
          header = header + "#define SEG1 3\n" +
                            "#define SEG2 4\n" +
                            "#define SEG3 5\n" +
                            "#define SEG4 6\n" +
                            "int digit[4] = {0, 0, 0, 0};\n" +
                            "boolean numArray[10][4] = {{0, 0, 0, 0} //0\n" +
                          ",{0, 0, 0, 1} //1\n" +
                          ",{0, 0, 1, 0} //2\n" +
                          ",{0, 0, 1, 1} //3\n" +
                          ",{0, 1, 0, 0} //4\n" +
                          ",{0, 1, 0, 1} //5\n" +
                          ",{0, 1, 1, 0} //6\n" +
                          ",{0, 1, 1, 1} //7\n" +
                          ",{1, 0, 0, 0} //8\n" +
                          ",{1, 0, 0, 1} //9\n};\n";
          
          setup = setup + "pinMode(SEG1, OUTPUT);\n" +
                          "pinMode(SEG2, OUTPUT);\n" +
                          "pinMode(SEG3, OUTPUT);\n" +
                          "pinMode(SEG4, OUTPUT);\n" +
                          "setdigit(8888);\n";
          
          method = method + "void displaynum() {\n" +
                            "int low = 1;  int com = 6;\n" + 
                            "for (int i = 0; i < 4; i++) {\n" +
                            "setnum(digit[i]);\n" +
                            "for (int j = 4; j > 0; j--) {\n" +
                            "if (j == low) {\n" +
                            "digitalWrite(com, LOW);\n" +
                            "} else {\n" +
                            "digitalWrite(com, HIGH);" +
                            "}\ncom--;\n}\n" +
                            "delay(5);\n" +
                            "low++;\ncom = 6;\n}\n}"+ 
                            "void setdigit(int n) {\n" +
                            "for (int i = 0; i < 4; i++) {\n" +
                            "digit[i] = n % 10;\n" +
                            "n = n / 10;\n" +
                            "Serial.print(n);\n}\n}\n" + 
                            "void setnum(int n) {\n" +
                            "for (int i = 4; i >= 0; i--) {\n" +
                            "if (numArray[n][i] == 0) {\n" +
                            "digitalWrite(SEG4 - i, LOW);\n" +
                            "}\nelse {\n" +
                            "digitalWrite(SEG4 - i, HIGH);\n}\n}\n}\n";
          break;
        }
      }
      
      for(int i = 1; i < blockidArg[0]; i++){
        if(blockidArg[i] == Temperature){
          header = header + "#define TEMP 5\n";
          setup = setup + "analogReference(INTERNAL);\n";
          
          method = method + "int gettempl(){\n" +
          		              "int val;\ndouble volt;\ndouble templ;\n" +
          		              "val = analogRead(templPin);\n" +
          		              "volt = 1.1*val/1024.0;\n" +
          		              "templ = (volt-0.6)*100.0;\n" +
          		              "return (int) (templ * 100);\n}\n\n";
          break;
        }
        
      }
  
      for(int i = 1; i < blockidArg[0]; i++){
        if(blockidArg[i] == Lightsensor){
          header = header + "#define LIGHT 4\n";
          break;
        }
      }
      

    }
    header = header +"\n";
    setup = setup + "}\n";
    method = method + "\n";
    
    return header + setup + method;
  }
  
}
