package Server;
import Common.CharacterCard;
import Common.RoomCard;
import Common.SuggestHand;
import Common.WeaponCard;


/**
 * The secret cards that are checked against accusations.
 * @author DavidC
 *
 */
public class EnvelopeHand extends SuggestHand
{

   private static final long serialVersionUID = -7822711756834020831L;

   
   public EnvelopeHand()
   {
      super();
   }
   
   public EnvelopeHand( CharacterCard c, RoomCard r, WeaponCard w )
   {
      super( c, r, w );
   }
   
   
}
