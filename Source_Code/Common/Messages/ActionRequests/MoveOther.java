package Common.Messages.ActionRequests;

import Common.CharacterCard;
import Common.CharacterCard.*;

public class MoveOther extends ActionRequest
{
   
   private static final long serialVersionUID = -7616726615345579861L;
   // TODO: Make these private and add getters
   public CharacterName playerName;
   public int xPos;
   public int yPos;
   
   
   public MoveOther( int xPos, int yPos, CharacterCard.CharacterName playerName )
   {
      this.xPos = xPos;
      this.yPos = yPos;
      this.playerName = playerName;
   }
   
   
   
}
