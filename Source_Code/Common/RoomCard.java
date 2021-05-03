package Common;

public class RoomCard extends Card
{

   private static final long serialVersionUID = 7704989267257720833L;

   public enum RoomName
   {
      BALL_ROOM, BILLIARD_ROOM, CONSERVATORY, DINING_ROOM, HALL, KITCHEN,
      LIBRARY, LOUNGE, STUDY;
   }


   RoomName roomName;


   /**
    * Must instantiate the room card with the specific room name
    *
    * @param roomName
    *        Name of the room
    */
   public RoomCard( RoomName roomName )
   {

      try
      {
         this.roomName = roomName;
      }

      // Guards for invalid room names
      catch( Exception e )
      {
         e.printStackTrace();
      }

   }


   /**
    * Instantiate the room card with the specific room name and pass a boolean
    * representing whether or not a custom name should be used
    *
    * @param roomName
    *        The name of the Room
    * @param modifyRoomName
    *        True if custom name is used
    */
   RoomCard( RoomName roomName, boolean modifyRoomName )
   {

      try
      {
         this.roomName = roomName;

         if( modifyRoomName )
         {
            this.modifyName();
         }

      }

      // Guards for invalid room names
      catch( Exception e )
      {
         e.printStackTrace();
      }

   }

   @Override
   public boolean equals(Object obj)
   {
      if(obj == null)
      {
         return false;
      }
      if (obj.getClass() != this.getClass())
      {
         return false;
      }
      return (((RoomCard)obj).roomName == roomName);
   }

   /**
    * Looks into a custom file, reads the name associated with the assigned
    * room name, and replaces the room name with the custom name
    */
   public void loadCustomRoomName()
   {

   }


   /**
    * @return The specific room name represented by this instance
    */
   public String getRoomName()
   {
      return String.valueOf( this.roomName );

   }

   public RoomName getRoomEnum()
   {
       return this.roomName;
   }

   /*
    * Test Method public static void main( String[] args ) {
    *
    * RoomName name = RoomName.MR_GREEN; RoomCard test = new RoomCard( name );
    *
    * System.out.println( test.getRoomName() );
    *
    * }
    */

}
