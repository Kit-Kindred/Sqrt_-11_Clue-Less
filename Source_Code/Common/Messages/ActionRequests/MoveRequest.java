package Common.Messages.ActionRequests;


public class MoveRequest extends ActionRequest
{
   public Move moveDirection;

   /**
    * Enumerate valid moves for a player - these will be interpreted by 
    * action request listener
    */
   public enum Move
   {
      UP, DOWN, LEFT, RIGHT, STAY, SHORTCUT;
   }   
   
   public MoveRequest( String PlayerName, Move moveDirection )
   {     
      super();
      this.PlayerName = PlayerName;
      this.moveDirection = moveDirection;
   }

   /**
    * example use case of move request with enumarated move set 
    *
    * @param args
    */
   //public static void main(String[] args)
   //{
   //   MoveRequest move = new MoveRequest(1, Move.UP);
   //}

}
