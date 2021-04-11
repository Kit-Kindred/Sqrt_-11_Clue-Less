package Common.Messages.ActionRequests;

import Common.Card;

/**
 * Sent from the client to the server in response. The card in the message will
 * be used to refute a suggestion.
 * @author DavidC
 *
 */
public class RefuteSuggestionResponse extends ActionRequest
{

   private static final long serialVersionUID = -9219931959986925246L;
   private Card card;
   private String toPlayer; //ID reference to the player who's being refuted
   
   public RefuteSuggestionResponse()
   {
      super("");    
   }
   
   
   public RefuteSuggestionResponse( String fromPlayerName, String toPlayerName, Card card )
   {
      super( fromPlayerName );    
      this.toPlayer = toPlayerName;
      this.card = card;
   }
   
   
   public Card getCard()
   {
      return this.card;
   }
   
   public String getToPlayer()
   {
      return this.toPlayer;
   }
   
}
