package Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;


/**
 * Thread safe input object we can use instead of scanner.
 * 
 * https://www.javaspecialists.eu/archive/Issue153-Timeout-on-Console-Input.html
 *
 */
public class ConsoleInput
{

   public String read() throws IOException
   {
      ExecutorService ex    = Executors.newSingleThreadExecutor();
      String          input = null;

      try
      {

            Future< String > result = ex.submit( new ConsoleInputHandler() );

            try
            {
               input = result.get();
            } 
            catch( ExecutionException e )
            {
               e.getCause().printStackTrace();
            } 
            catch( InterruptedException e )
            {
               e.printStackTrace();
            }

      } finally
      {
         ex.shutdownNow();
      }

      return input;

   }

   
   /**
    * Need a better way of handling inputs. Using scanner causing blocking in the
    * main thread which doesn't allow for easy context switches.
    * https://stackoverflow.com/questions/4983065/how-to-interrupt-java-util-scanner-nextline-call
    *
    */
   public class ConsoleInputHandler implements Callable< String >
   {

      public synchronized String call() throws IOException
      {
         BufferedReader br = new BufferedReader(
            new InputStreamReader( System.in ) );
         String input = "";

         while( input.equals( "" ) )
         {
            System.out.print( ">> " );

            input = br.readLine();
            
            
//            try
//            {
//               // wait until we have data to complete a readLine()
//               while( !br.ready() /* ADD SHUTDOWN CHECK HERE */ )
//               {
//                  Thread.sleep( 200 );
//               }
//
//               input = br.readLine();
//               
//            } catch( InterruptedException e )
//            {
//               return null;
//            }

         } 

         return input;

      }

   }
}

