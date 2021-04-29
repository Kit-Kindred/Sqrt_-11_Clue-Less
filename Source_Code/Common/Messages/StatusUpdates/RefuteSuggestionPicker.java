package Common.Messages.StatusUpdates;

import Common.PlayerHand;


/**
 * Update sent from the server to the client when the client has one or more 
 * card(s) that can refute a suggestion.
 * 
 * @author DavidC
 *
 */
public class RefuteSuggestionPicker extends StatusUpdate
{

   private static final long serialVersionUID = 4544589638073938112L;
   private PlayerHand hand;
   private String PlayerID; // The name of the player making the suggestion
   
   public RefuteSuggestionPicker()
   {
      super();
   }
   
   public RefuteSuggestionPicker( String PlayerID, PlayerHand hand )
   {
      super();
      this.PlayerID = PlayerID;
      this.hand = hand;
   }
   
   public PlayerHand getHand()
   {
      return this.hand;
   }
   
   public String getPlayer()
   {
      return this.PlayerID;
   }
   
}
