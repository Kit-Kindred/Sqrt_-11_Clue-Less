package Common.Messages.StatusUpdates;

import Common.*;

public class PlayerHandUpdate extends StatusUpdate
{
   
   /**
    * Sends the serializable object PlayerHand
    */
   private static final long serialVersionUID = -1385435942123491943L;
   private PlayerHand playerHand;
   
   
   public PlayerHandUpdate( PlayerHand hand )
   {
      super();
      this.playerHand = hand;
   }
   
   
   
   public PlayerHand getHandUpdate()
   {
      return this.playerHand;
   }
   
}
