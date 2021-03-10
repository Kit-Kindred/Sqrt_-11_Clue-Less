package Common;

public class CharacterCard extends Card
{

   // Must instantiate the character card with the specific character
   CharacterCard( CharacterName characterName )
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


   public String getCharacterName()
   {
      return this.characterName.toString();

   }


   enum CharacterName
   {
      COLONEL_MUSTARD, MISS_SCARLET, PROFESSOR_PLUM, MR_GREEN, MRS_WHITE,
      MRS_PEACOCK;
   }


   CharacterName characterName;

   /*
    * Test Method 
    * public static void main( String[] args ) {
    * 
    * CharacterName name = CharacterName.MR_GREEN; CharacterCard test = new
    * CharacterCard( name );
    * 
    * System.out.println( test.getCharacterName() );
    * 
    * }
    */

}
