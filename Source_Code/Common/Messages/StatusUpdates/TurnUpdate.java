package Common.Messages.StatusUpdates;

import Common.Player;

/**
 * This message is used exclusively to send client updates to update the turn
 * status. The clients can keep track of the players' turn status to only
 * show/allow actions during the player's turn.
 * 
 * @author DavidC
 *
 */
public class TurnUpdate extends StatusUpdate
{
   
   public String TurnPlayer;
   
   public TurnUpdate( String turnPlayer )
   {
      super();  
      this.TurnPlayer = turnPlayer;
   }
   
   
}
