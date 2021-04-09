package Common.Messages.StatusUpdates;

import Common.*;

public class PlayerHandUpdate extends StatusUpdate
{
   private String playerHand;
   
   
   
   public PlayerHandUpdate( PlayerHand hand )
   {
      super();
      this.playerHand = hand.toString();
   }
   
   
   
   public String getHandUpdate()
   {
      return this.playerHand;
   }
   
}
