package Common;

public class CharacterCard extends Card
{

   private static final long serialVersionUID = 60228553259945900L;

   public enum CharacterName
   {
      COLONEL_MUSTARD, MISS_SCARLET, PROFESSOR_PLUM, MR_GREEN, MRS_WHITE,
      MRS_PEACOCK;
      
   }


   CharacterName characterName;


   /**
    * Must instantiate the character card with the specific character
    * 
    * @param characterName
    *        Name of the character
    */
   public CharacterCard( CharacterName characterName )
   {

      try
      {
         this.characterName = characterName;
      }

      // Guards for invalid character names
      catch( Exception e )
      {
         e.printStackTrace();
      }

   }


   /**
    * Instantiate the character card with the specific character name and pass
    * a boolean representing whether or not a custom name should be used
    * 
    * @param characterName
    *        The name of the Character
    * @param modifyCharacterName
    *        True if custom name is used
    */
   CharacterCard( CharacterName characterName, boolean modifyCharacterName )
   {

      try
      {
         this.characterName = characterName;

         if( modifyCharacterName )
         {
            this.modifyName();
         }

      }

      // Guards for invalid character names
      catch( Exception e )
      {
         e.printStackTrace();
      }

   }

   
   /**
    * Looks into a custom file, reads the name associated with the assigned
    * character name, and replaces the character name with the custom name
    */
   public void loadCustomCharacterName()
   {
      
   }
   

   /**
    * @return The specific character name represented by this instance
    */
   public String getCharacterName()
   {
      return String.valueOf( this.characterName );

   }

   /*
    * Test Method public static void main( String[] args ) {
    * 
    * CharacterName name = CharacterName.MR_GREEN; CharacterCard test = new
    * CharacterCard( name );
    * 
    * System.out.println( test.getCharacterName() );
    * 
    * }
    */

}
