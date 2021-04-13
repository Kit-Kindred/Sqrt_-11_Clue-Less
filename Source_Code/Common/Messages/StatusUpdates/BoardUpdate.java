package Common.Messages.StatusUpdates;

import Common.Board;

/**
 * Board update which contains a serialized Board object. The Board state is
 * sent from the server to each client after each player action to ensure
 * that each player has updated game state at all time. This message is 
 * deserialized and processed by each client.
 * 
 * @author DavidC
 *
 */
public class BoardUpdate extends StatusUpdate
{

   /**
    * Sends the serializable object board
    */
   private static final long serialVersionUID = -3873643820049641645L;
   
   private Board board;
   
   
   public BoardUpdate( Board board )
   {
      super();
      this.board = board;
   }
   
   public Board getBoard()
   {
      return this.board;
   }
}
