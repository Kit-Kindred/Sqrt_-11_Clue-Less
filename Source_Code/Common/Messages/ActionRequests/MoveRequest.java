package Common.Messages.ActionRequests;


public class MoveRequest extends ActionRequest
{
   
   /**
    * Using a string to keep track of where the player wants to move. This seemed the
    * most flexible until we figure out if we want to use room names to dictate 
    * movement, or if we want to use directions.
    */
   public String moveDirection;
   
   public int PlayerID;
   
   public MoveRequest( int PlayerID, String moveDirection )
   {
      super();
      this.PlayerID = PlayerID;
      this.moveDirection = moveDirection;
   }

}
