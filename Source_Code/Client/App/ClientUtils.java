package Client.App;

import java.awt.EventQueue;

import Client.App.views.*;

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
   public static void launchGUI()
   {
      
      EventQueue.invokeLater( new Runnable()
      {

         public void run()
         {
            try
            {
               LobbyMain gui = new LobbyMain();
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
   public static void main( String args[] )
   {

      launchGUI();
   }
   
   
   
}
