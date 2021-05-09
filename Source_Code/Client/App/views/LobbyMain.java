package Client.App.views;

import Client.ClueLessClient;
import Common.*;
import Common.Messages.ActionRequests.MoveRequest;
import Common.Messages.StatusUpdates.AccuseNotification;
import Common.Messages.StatusUpdates.PlayerHandUpdate;
import Common.Messages.StatusUpdates.RefuteSuggestionPicker;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;


public class LobbyMain extends JFrame
{

   private JPanel               contentPane;
   private JPanel               mainLobbyPanel;
   private JPanel               mainGamePanel;
   private MainPanel            mainPanel;
   private LobbyInitialPanel    joinPanel;
   private LobbyStatusPanel     statusPanel;

   private AccuseDialog accuseDialog;
   private SuggestDialog suggestDialog;
   private Detective_pad detectivePad;
   private EndGameDialog endDialog;

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
      client = c;setTitle( "ClueLess" );
      initLobbyComponents();createEvents();
      ( (CardLayout) mainLobbyPanel.getLayout() )
      .next( mainLobbyPanel );
      setBounds( 100, 100, 1000, 630 );
      ( (CardLayout) contentPane.getLayout() )
         .next( contentPane );
      CardPanel[] cardsTest = new CardPanel[6];
      cardsTest[0] = new CardPanel( new CharacterCard( CharacterCard.CharacterName.COLONEL_MUSTARD ) );
      cardsTest[1] = new CardPanel( new CharacterCard( CharacterCard.CharacterName.MISS_SCARLET ) );
      cardsTest[2] = new CardPanel( new RoomCard( RoomCard.RoomName.BALL_ROOM ) );
      cardsTest[3] = new CardPanel( new RoomCard( RoomCard.RoomName.CONSERVATORY) );
      cardsTest[4] = new CardPanel( new WeaponCard( WeaponCard.WeaponType.ROPE ) );
      cardsTest[5] = new CardPanel( new WeaponCard( WeaponCard.WeaponType.KNIFE ) );
      for( CardPanel card: cardsTest ){mainPanel.cardsPictureBorderPanel.add( card );}
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

      accuseDialog = new AccuseDialog(this, "Accuse");
      suggestDialog = new SuggestDialog(this, "Suggest");
      endDialog = new EndGameDialog( this, "Game Over!");
      detectivePad = new Detective_pad( this, "Detective Pad");


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

      mainPanel.actionPanel.suggestButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
           suggest();
         }
      });

      mainPanel.actionPanel.accuseButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            accuse();
         }
      });

      mainPanel.actionPanel.dPad.moveLeftButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e)
          {
              move(MoveRequest.Move.LEFT);
          }
      });

    mainPanel.actionPanel.dPad.moveRightButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e)
      {
          move(MoveRequest.Move.RIGHT);
      }
    });

    mainPanel.actionPanel.dPad.moveUpButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            move(MoveRequest.Move.UP);
        }
    });

    mainPanel.actionPanel.dPad.moveDownButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          move(MoveRequest.Move.DOWN);
        }
    });

    mainPanel.actionPanel.dPad.moveShortcutButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            move(MoveRequest.Move.SHORTCUT);
        }
    });
    
    mainPanel.actionPanel.detectivePadButton.addActionListener( new ActionListener() {

      @Override
      public void actionPerformed( ActionEvent e )
      {
         detectivePad.open();
      }       
    });

    // Ok button will change GUI back to start game frame
    endDialog.addButtonListener( new ActionListener()
       {

         @Override
         public void actionPerformed( ActionEvent e )
         {
            setSize( 500, 362 );
            ( (CardLayout) contentPane.getLayout() )
            .previous( contentPane );

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
               setSize( 1200, 900 );
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

      client.addPropertyChangeListener("RefuteSuggestionPicker", new PropertyChangeListener() {
          @Override
          public void propertyChange(PropertyChangeEvent evt) {
              refute((RefuteSuggestionPicker) evt.getNewValue());
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

            mainPanel.boardPicture.updateBoard(client.getBoard());
            
            tryEnableSuggestButton();
         }
      });

      // client.addPropertyChangeListener("GameBoard", new PropertyChangeListener() {
      //    @Override
      //    public void propertyChange(PropertyChangeEvent evt) {
      //        System.out.println("got the GameBoard");
      //       mainPanel.boardPicture.updateBoard((Board) evt.getNewValue());
      //    }
      // });

      client.addPropertyChangeListener("PlayerHand", new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            //for(Card c : ((PlayerHand)evt.getNewValue()).getCards())
            //{
            //   mainPanel.cardsPictureBorderPanel.addCard(c);
            //}
            for(RoomCard r : ((PlayerHand)evt.getNewValue()).getRooms())
            {
               mainPanel.cardsPictureBorderPanel.addCard(r);
            }
            for(WeaponCard w : ((PlayerHand)evt.getNewValue()).getWeapons())
            {
               mainPanel.cardsPictureBorderPanel.addCard(w);
            }
            for(CharacterCard c : ((PlayerHand)evt.getNewValue()).getCharacters())
            {
               mainPanel.cardsPictureBorderPanel.addCard(c);
            }
         }
      });

      client.addPropertyChangeListener("PlayerTurn", new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            //System.out.println("Received Player Turn");
            mainPanel.actionPanel.accuseButton.setEnabled((boolean) evt.getNewValue());

            boolean canSuggest = (boolean) evt.getNewValue() && client.getRoom() != null;
            mainPanel.actionPanel.suggestButton.setEnabled(canSuggest);

            mainPanel.actionPanel.endTurnButton.setEnabled((boolean) evt.getNewValue());
            mainPanel.actionPanel.dPad.enableDpad((boolean) evt.getNewValue());
            /*if((boolean) evt.getNewValue()){

            }
            else
            {
               mainPanel.cardsPictureBorderPanel.emptySelection();
            }*/
         }
      });

      client.addPropertyChangeListener("EndGame", new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {

            /* This is most likely not the best medium to accomplish this type
             * of event, but we don't have too much time to experiment with other
             * listeners.
             */
            processGameEnd( (AccuseNotification) evt.getNewValue() );
         }
      });
   }

   public void accuse()
   {
      accuseDialog.open();
      SuggestHand accuseHand = accuseDialog.getAccuseHand();
      if (accuseHand != null)
      {
         client.accuse(accuseHand);
      }
   }

  public void suggest()
  {
     RoomCard cr = client.getRoom();
     if (cr != null)
     {
        suggestDialog.open(cr);
        SuggestHand suggestHand = suggestDialog.getSuggestHand();
        if (suggestHand != null)
        {
           client.suggest(suggestHand);
        }
     }
  }

  public void tryEnableSuggestButton()
  {
      mainPanel.actionPanel.suggestButton.setEnabled(client.canSuggest());
  }

  public void refute(RefuteSuggestionPicker rs)
  {
      RefuteDialog refuteDialog = new RefuteDialog(this, "Refute", rs);
      Card selection = refuteDialog.getRefutationChoice();
      if(selection != null)
      {
          client.refute(rs.getPlayer(), selection);
      }
  }

   public void move(MoveRequest.Move m)
   {
      client.move( m );
   }

   public void processGameEnd( AccuseNotification accuseNotification )
   {
      endDialog.addPlayer( accuseNotification.PlayerName );
      endDialog.addSolution( accuseNotification.AccuseHand );
      endDialog.setContent();
      endDialog.open();
   }
}
