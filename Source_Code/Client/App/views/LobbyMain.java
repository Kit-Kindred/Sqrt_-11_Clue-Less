package Client.App.views;

import Client.ClueLessClient;
import Common.Player;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class LobbyMain extends JFrame
{

   private JPanel               contentPane;
   private JPanel               mainLobbyPanel;
   private JPanel               mainGamePanel;
   private MainPanel            mainPanel;
   private LobbyInitialPanel    joinPanel;
   private LobbyStatusPanel     statusPanel;
   private final ClueLessClient client;

   /**
    * Create the frame.
    */
   public LobbyMain(ClueLessClient c)
   {
      client = c;
      setTitle( "ClueLess" );
      initLobbyComponents();
      // initGameComponents();
      createEvents();

   }
   
   
   /**
    * Adding this method to bypass the lobby state. It's useful for viewing 
    * how the client looks without having to spawn a bunch of client instances.
    */
   public LobbyMain(ClueLessClient c, Boolean test)
   {
      client = c;
      setTitle( "ClueLess" );
      initLobbyComponents();
      // initGameComponents();
      createEvents();
      ( (CardLayout) mainLobbyPanel.getLayout() )
      .next( mainLobbyPanel );
      setBounds( 100, 100, 1000, 630 );
      ( (CardLayout) contentPane.getLayout() )
         .next( contentPane );
   }

   
   


   /**
    * Creates and initializes the swing components that make up the
    * application.
    */
   public void initLobbyComponents()
   {

      /*
       * Default/Root JFrame with a JPanel where the contents are laid out.
       * Defines the main contentPane.
       */
      setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      setBounds( 243, 156, 500, 362 );
      contentPane = new JPanel();
      contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
      setContentPane( contentPane );
      CardLayout cardMain = new CardLayout( 0, 0 );
      contentPane.setLayout( cardMain );

      /*
       * Surrounding frame that contains the control frames. This should allow
       * for switching context/views after the player clicks join.
       */
      mainLobbyPanel = new JPanel();
      mainLobbyPanel.setBounds( 243, 156, 500, 362 );
      contentPane.add( mainLobbyPanel );

      mainGamePanel = new JPanel();
      //mainGamePanel.setBounds( 0, 0, 1000, 725 );
      contentPane.add( mainGamePanel );

      // Create our two views.
      joinPanel = new LobbyInitialPanel();  // Beginning frame that appears
                                            // first.
      statusPanel = new LobbyStatusPanel(); // Frame that appears after
                                            // joining.
      mainPanel = new MainPanel();          // Frame that appears when the
                                            // server starts the game

      // Set the card layout and add the views to it
      CardLayout card = new CardLayout( 0, 0 );
      mainLobbyPanel.setLayout( card );
      mainLobbyPanel.add( joinPanel, "name_805697666643900" );
      mainLobbyPanel.add( statusPanel, "name_secondaryPanel" );


      mainGamePanel.add( mainPanel, "name_mainGamePanel" );
      mainGamePanel.setLayout( card );

   }


   /**
    * Creates all the event handlers that are needed in the app. Temporarily
    * using this as an initializer for the ActionListeners as well.
    */
   public void createEvents()
   {

      /*
       * Transitions the pre-joined state to the joined state. Doesn't handle
       * errors just yet - always assumes a successful join.
       */
      joinPanel.joinGameButton.addActionListener( new ActionListener()
      {

         @Override
         public void actionPerformed( ActionEvent e )
         {
            // TODO: add error checking just in case. But like, probably won't get to it...

            String serverIP = joinPanel.serverIPTextField.getText();
            int serverPort = Integer.parseInt(joinPanel.serverPortTextField.getText());
            String playerName = joinPanel.playerNameTextField.getText();

            // Structure doesn't really let us init the client anywhere else. Whoops
            client.init(serverIP, serverPort, playerName);

            statusPanel.playerNameLabel.setText( playerName );
            ( (CardLayout) mainLobbyPanel.getLayout() )
               .next( mainLobbyPanel );

         }

      } );

      statusPanel.startGameButton.addActionListener(new ActionListener()
      {

         @Override
         public void actionPerformed( ActionEvent e )
         {
            client.startGame();
         }

      });

      mainPanel.actionPanel.endTurnButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            client.endTurn();
         }
      });

      mainPanel.chatBox.sendBox.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            client.sendChatMessage((String) mainPanel.chatBox.sendTo.getSelectedItem(),
                                   mainPanel.chatBox.sendBox.getText(),
                                   mainPanel.chatBox.sendTo.getSelectedIndex() == 0);
            mainPanel.chatBox.sendBox.setText("");
         }
      });

      // Whenever a player connects
      client.addPropertyChangeListener("startPlayer", new PropertyChangeListener() {

         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            statusPanel.startGameButton.setEnabled((boolean) evt.getNewValue());
            if((boolean) evt.getNewValue())
            {
               statusPanel.startGameButton.setToolTipText("Start the game!");
            }
            else
            {
               statusPanel.startGameButton.setToolTipText("Only the host can start the game!");
            }
         }
      });

      client.addPropertyChangeListener("activeGame", new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            if ((boolean) evt.getNewValue())  // Server started game
            {
               setBounds( 100, 100, 1000, 630 );
               ( (CardLayout) contentPane.getLayout() )
                       .next( contentPane );
            }
         }
      });

      client.addPropertyChangeListener("LogReceived", new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            mainPanel.theLog.log(
                    ((ClueLessClient.LogPair)evt.getNewValue()).color,
                    ((ClueLessClient.LogPair)evt.getNewValue()).msg);
         }
      });

      client.addPropertyChangeListener("PlayerUpdate", new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            mainPanel.chatBox.sendTo.removeAllItems();
            mainPanel.chatBox.sendTo.addItem("[All]");
            for (Player p : ((ArrayList<Player>) evt.getNewValue()))
            {
               if (!p.PlayerName.equals("") && !p.PlayerName.equals(client.getPlayerName()))
               {
                  mainPanel.chatBox.sendTo.addItem(p.PlayerName);
               }
            }
         }
      });

   }
   
   


}
