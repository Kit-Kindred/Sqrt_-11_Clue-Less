package Common;

public class CharacterCard extends Card
{

   private static final long serialVersionUID = 60228553259945900L;

   public enum CharacterName
   {
      COLONEL_MUSTARD, MISS_SCARLET, PROFESSOR_PLUM, MR_GREEN, MRS_WHITE,
      MRS_PEACOCK;

   }

   public static String characterTwoLetter(CharacterName cn)
   {
      switch(cn)
      {
         case MR_GREEN ->
                 {
                    return "[MG]";
                 }
         case MRS_WHITE ->
                 {
                    return "[MW]";
                 }
         case MRS_PEACOCK ->
                 {
                    return "[MP]";
                 }
         case MISS_SCARLET ->
                 {
                    return "[MS]";
                 }
         case PROFESSOR_PLUM ->
                 {
                    return "[PP]";
                 }
         case COLONEL_MUSTARD ->
                 {
                    return "[CM]";
                 }
         default ->
                 {
                    return "[UN]";
                 }
      }
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
        return (((CharacterCard)obj).characterName == characterName);
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
      
      String str = String.valueOf( this.characterName );
      str = str.toLowerCase();
      if( str.contains( "_" ) )
      {
         str = str.replace( "_", "\s" );
      }
   
      StringBuilder output = new StringBuilder( str );
      int i = 0;
      do {
         output.replace(i, i + 1, output.substring(i,i + 1).toUpperCase());
         i =  output.indexOf(" ", i) + 1;
      } while (i > 0 && i < output.length());
      
      return output.toString();

   }

   public CharacterName getCharacterEnum()
   {
       return this.characterName;
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
