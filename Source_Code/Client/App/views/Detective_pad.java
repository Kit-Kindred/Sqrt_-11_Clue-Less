package Client.App.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class Detective_pad extends JDialog {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame parent;
	
	
JTabbedPane config = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.WRAP_TAB_LAYOUT);

  public Detective_pad( JFrame frame, String title ) {
    super( frame, title, true);
    this.parent = frame;


    JPanel SusPane = new JPanel();
    SusPane.setLayout(new BoxLayout(SusPane, BoxLayout.Y_AXIS));
    JTextArea question = new JTextArea("\n\tDetective Case Notes: Suspects");
    question.setEditable(false);
    question.setMaximumSize(new Dimension(600, 50));
    question.setAlignmentX(0.2f);
    
    question.setBackground(new Color(153,153,153));
    SusPane.setBackground(new Color(204,204,204));
    
    JCheckBox suspect1 = new JCheckBox("Colonel Mustard", false);
    JCheckBox suspect2 = new JCheckBox("Miss Scarlet", false);
    JCheckBox suspect3 = new JCheckBox("Reverend Green", false);
    JCheckBox suspect4 = new JCheckBox("Mrs. Peacock", false);
    JCheckBox suspect5 = new JCheckBox("Professor Plum", false);
    JCheckBox suspect6 = new JCheckBox("Mrs. White", false);

    JPanel WepPane = new JPanel();
    WepPane.setLayout(new BoxLayout(WepPane, BoxLayout.Y_AXIS));
    JTextArea question2 = new JTextArea("\n\tDetective Case Notes: Weapons");
    
    question2.setEditable(false);
    question2.setMaximumSize(new Dimension(600, 50));
    question2.setAlignmentX(0.2f);
    question2.setBackground(WepPane.getBackground());
    
    question2.setBackground(new Color(153,153,153));
    WepPane.setBackground(new Color(204,204,204));

    JCheckBox weapon1CB = new JCheckBox("Revolver", false);
    JCheckBox weapon2CB = new JCheckBox("Candlestick", false);
    JCheckBox weapon3CB = new JCheckBox("Rope", false);
    JCheckBox weapon4CB = new JCheckBox("Wrench", false);
    JCheckBox weapon5CB = new JCheckBox("Dagger", false);
    JCheckBox weapon6CB = new JCheckBox("Lead Pipe", false);
    
    JPanel RoomPane = new JPanel();
    RoomPane.setLayout(new BoxLayout(RoomPane, BoxLayout.Y_AXIS));
    JTextArea question3 = new JTextArea("\n\tDetective Case Notes: Rooms");
    
    question3.setEditable(false);
    question3.setMaximumSize(new Dimension(600, 50));
    question3.setAlignmentX(0.2f);
    question3.setBackground(RoomPane.getBackground());
    
    question3.setBackground(new Color(153,153,153));
    RoomPane.setBackground(new Color(204,204,204));

    JCheckBox room1CB = new JCheckBox("Hall", false);
    JCheckBox room2CB = new JCheckBox("Lounge", false);
    JCheckBox room3CB = new JCheckBox("Kitchen", false);
    JCheckBox room4CB = new JCheckBox("Ballroom", false);
    JCheckBox room5CB = new JCheckBox("Dining Room", false);
    JCheckBox room6CB = new JCheckBox("Study", false);
    JCheckBox room7CB = new JCheckBox("Conservatory", false);
    JCheckBox room8CB = new JCheckBox("Billiard Room", false);
    JCheckBox room9CB = new JCheckBox("Library", false);
    
   
   
    SusPane.add(question);
    SusPane.add(suspect1);
    SusPane.add(suspect2);
    SusPane.add(suspect3);
    SusPane.add(suspect4);
    SusPane.add(suspect5);
    SusPane.add(suspect6);
    
    
    WepPane.add(question2);
    WepPane.add(weapon1CB);
    WepPane.add(weapon2CB);
    WepPane.add(weapon3CB);
    WepPane.add(weapon4CB);
    WepPane.add(weapon5CB);
    WepPane.add(weapon6CB);
    
    
    RoomPane.add(question3);
    RoomPane.add(room1CB);
    RoomPane.add(room2CB);
    RoomPane.add(room3CB);
    RoomPane.add(room4CB);
    RoomPane.add(room5CB);
    RoomPane.add(room6CB);
    RoomPane.add(room7CB);
    RoomPane.add(room8CB);
    RoomPane.add(room9CB);
    
    
   
    config.addTab("Suspects", null, SusPane);
    config.addTab("Weapons", null, WepPane);
    config.addTab("Rooms", null, RoomPane);
    
   
   

    getContentPane().add(config, BorderLayout.CENTER);
  }

  class TabManager implements ItemListener {
    Component tab;

    public TabManager(Component tabToManage) {
      tab = tabToManage;
    }

    public void itemStateChanged(ItemEvent ie) {
      int index = config.indexOfComponent(tab);
      if (index != -1) {
        config.setEnabledAt(index,
            ie.getStateChange() == ItemEvent.SELECTED);
      }
      Detective_pad.this.repaint();
    }
  }
  
  
  public void open()
  {
     setBounds( parent.getBounds().x + parent.getWidth()/4, 
        parent.getBounds().y + parent.getHeight()/4, 400, 350);
     setVisible( true );
  }

//  public static void main(String args[]) {
//    Detective_pad sc = new Detective_pad();
//    sc.setVisible(true);
//  }
}

           
         
