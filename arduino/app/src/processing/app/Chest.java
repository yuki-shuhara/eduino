package processing.app;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import processing.app.Editor.FileDropHandler;
import processing.app.Editor.TextAreaPopup;
import processing.app.syntax.JEditTextArea;
import processing.app.syntax.PdeTextAreaDefaults;


public class Chest extends Editor implements ActionListener {
  
  Chest(Base ibase, String path, int[] location){
    //super();
    this.base = ibase;

    Base.setIcon(this);

    // Install default actions for Run, Present, etc.
    resetHandlers();

//edus_111012-comentout
    // add listener to handle window close box hit event
    addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          base.handleClose(Chest.this);
        }
      });
    // don't close the window when clicked, the app will take care
    // of that via the handleQuitInternal() methods
    // http://dev.processing.org/bugs/show_bug.cgi?id=440
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    // When bringing a window to front, let the Base know
    addWindowListener(new WindowAdapter() {
        public void windowActivated(WindowEvent e) {
//          System.err.println("activate");  // not coming through
          base.handleActivated(Chest.this);
          // re-add the sub-menus that are shared by all windows
          fileMenu.insert(sketchbookMenu, 2);
          fileMenu.insert(examplesMenu, 3);
          sketchMenu.insert(importMenu, 4);
          toolsMenu.insert(boardsMenu, numTools);
          toolsMenu.insert(serialMenu, numTools + 1);
        }

        // added for 1.0.5
        // http://dev.processing.org/bugs/show_bug.cgi?id=1260
        public void windowDeactivated(WindowEvent e) {
//          System.err.println("deactivate");  // not coming through
          fileMenu.remove(sketchbookMenu);
          fileMenu.remove(examplesMenu);
          sketchMenu.remove(importMenu);
          toolsMenu.remove(boardsMenu);
          toolsMenu.remove(serialMenu);
        }
      });
//edue
    
    //PdeKeywords keywords = new PdeKeywords();
    //sketchbook = new Sketchbook(this);

    if (serialMonitor == null) {
      serialMonitor = new SerialMonitor(Preferences.get("serial.port"));
      serialMonitor.setIconImage(getIconImage());
    }
    
    buildMenuBar();

    // For rev 0120, placing things inside a JPanel
    Container contentPain = getContentPane();
    contentPain.setLayout(new BorderLayout());
    JPanel pain = new JPanel();
    pain.setLayout(new BorderLayout());
    contentPain.add(pain, BorderLayout.CENTER);

    Box box = Box.createVerticalBox();
    Box upper = Box.createVerticalBox();
    
//edus_11101X -added
    
    JPanel field = new JPanel();
    field.setLayout(new BorderLayout());
    JPanel workspace = new JPanel();
    workspace.setLayout(null);
    
    /*JPanel warehouse = new JPanel();
    warehouse.setLayout(null);
    */
    
    JPanel aaa = new JPanel();
    int count = 10;
    GridLayout layout = new GridLayout(count, 1);
    //layout.setHgap(20);
    layout.setVgap(30);
    

    CreatePanel panelA = new CreatePanel();
    aaa.add(panelA.create());
    
    aaa.setLayout(layout);
    
    //edus_111019-edueここでパネルを作成する

    //warehouse.setLayout(null);

    //aaa.add(panelA.base);
    
    //JScrollBar bar = new JScrollBar(JScrollBar.VERTICAL, 1, 100, 1, 200);
   // warehouse.add(bar);


    
    JScrollPane warehouse = new JScrollPane(aaa);
    //warehouse.setLayout(null);
    //warehouse.setViewportView(aaa);
    //Panel(warehouse);
    
    //validate();
    
    
    field.add(warehouse, BorderLayout.WEST);
    field.add(workspace, BorderLayout.CENTER);
    
//edue
    
    
    if (toolbarMenu == null) {
      toolbarMenu = new JMenu();
      base.rebuildToolbarMenu(toolbarMenu);
    }
    toolbar = new EditorToolbar(this, toolbarMenu);
    upper.add(toolbar);

    header = new EditorHeader(this);
    
//edus_111012 -comentout
    /*upper.add(header);*/
//edue
    
    textarea = new JEditTextArea(new PdeTextAreaDefaults());
    textarea.setRightClickPopup(new TextAreaPopup());
    textarea.setHorizontalOffset(6);

    // assemble console panel, consisting of status area and the console itself
    consolePanel = new JPanel();
    consolePanel.setLayout(new BorderLayout());

    status = new EditorStatus(this);
    consolePanel.add(status, BorderLayout.NORTH);

    console = new EditorConsole(this);
    // windows puts an ugly border on this guy
    console.setBorder(null);
    lineStatus = new EditorLineStatus(textarea);

//edus_11101X -added
    
//edue
    
//edus_111012 -comentout
    consolePanel.add(console, BorderLayout.CENTER);
    consolePanel.add(lineStatus, BorderLayout.SOUTH);
//edue
    
//edus -testplay
    upper.add(field);
    //upper.add(textarea);
//edue
 
    splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                               upper, consolePanel);

    splitPane.setOneTouchExpandable(true);
    // repaint child panes while resizing
    splitPane.setContinuousLayout(true);
    // if window increases in size, give all of increase to
    // the textarea in the uppper pane
    splitPane.setResizeWeight(1D);

    // to fix ugliness.. normally macosx java 1.3 puts an
    // ugly white border around this object, so turn it off.
    splitPane.setBorder(null);

    // the default size on windows is too small and kinda ugly
    int dividerSize = Preferences.getInteger("editor.divider.size");
    if (dividerSize != 0) {
      splitPane.setDividerSize(dividerSize);
    }
    
    splitPane.setMinimumSize(new Dimension(600, 400));
    box.add(splitPane);
   
    
    // hopefully these are no longer needed w/ swing
    // (har har har.. that was wishful thinking)
    listener = new EditorListener(this, textarea);
    pain.add(box);

    // get shift down/up events so we can show the alt version of toolbar buttons
    textarea.addKeyListener(toolbar);

    pain.setTransferHandler(new FileDropHandler());

//    System.out.println("t1");

    // Finish preparing Editor (formerly found in Base)
    pack();

//    System.out.println("t2");

    // Set the window bounds and the divider location before setting it visible
    setPlacement(location);


    // If the window is resized too small this will resize it again to the
    // minimums. Adapted by Chris Lonnen from comments here:
    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4320050
    // as a fix for http://dev.processing.org/bugs/show_bug.cgi?id=25
    final int minW = Preferences.getInteger("editor.window.width.min");
    final int minH = Preferences.getInteger("editor.window.height.min");
    addComponentListener(new java.awt.event.ComponentAdapter() {
        public void componentResized(ComponentEvent event) {
          setSize((getWidth() < minW) ? minW : getWidth(),
                  (getHeight() < minH) ? minH : getHeight());
        }
      });

//    System.out.println("t3");

    // Bring back the general options for the editor
    applyPreferences();

//    System.out.println("t4");

    // Open the document that was passed in
    boolean loaded = handleOpenInternal(path);
    if (!loaded) sketch = null;

//    System.out.println("t5");

    // All set, now show the window
    //setVisible(true);
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    
  }

}
