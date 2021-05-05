package Client.App;

import java.awt.EventQueue;

import Client.App.views.*;
import Client.ClueLessClient;

/**
 * Contains helper methods used to create views and launch the GUI.
 * @author DavidC
 *
 */
public class ClientUtils
{
   
   /**
    * Create the main GUI component and launch the game.
    */
   public static void launchGUI(ClueLessClient client)
   {
      
      EventQueue.invokeLater( new Runnable()
      {

         public void run()
         {
            try
            {
//               LobbyMain gui = new LobbyMain(client);
               LobbyMain gui = new LobbyMain( client, true ); // This is used for
                                                        // testing to bypass
                                                        // the lobby state.
               gui.setVisible( true );
            } catch( Exception e )
            {
               e.printStackTrace();
            }

         }

         
      } );
      
   }
   
   
   /**
    * Test main method to launch the GUI.
    */
   public static void main( String[] args )
   {
      final ClueLessClient client = new ClueLessClient();  // Just gonna pass this guy everywhere I guess
      launchGUI(client);
   }
   
   
   
}
