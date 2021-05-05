package Common;

public class WeaponCard extends Card
{

   private static final long serialVersionUID = 1234167749488657323L;

   public enum WeaponType
   {
      ROPE, LEAD_PIPE, KNIFE, WRENCH, CANDLESTICK, REVOLVER;
   }


   WeaponType weaponType;


   /**
    * Must instantiate the weapon card with the weapon type
    *
    * @param weaponType
    *        Name of the weapon
    */
   public WeaponCard( WeaponType weaponType )
   {

      try
      {
         this.weaponType = weaponType;
      }

      // Guards for invalid weapon types
      catch( Exception e )
      {
         e.printStackTrace();
      }

   }


   /**
    * Instantiate the weapon type with the specific name and pass a boolean to
    * indicate whether or not a custom name is used
    *
    * @param weaponType
    *        Name of the weapon
    * @param modifyWeaponName
    *        True if custom names should be used
    */
   WeaponCard( WeaponType weaponType, boolean modifyWeaponName )
   {

      try
      {
         this.weaponType = weaponType;

         if (modifyWeaponName)
         {
            this.modifyName();
         }
      }

      // Guards for invalid weapon types
      catch( Exception e )
      {
         e.printStackTrace();
      }

   }


   /**
    * Looks into a custom file, reads the name associated with the assigned
    * weapon name, and replaces the weapon name with the custom name
    */
   public void loadCustomWeaponName()
   {

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
      return (((WeaponCard)obj).weaponType == weaponType);
   }

   /**
    *
    * @return The specific weapon name represented by this instance
    */
   public String getWeaponType()
   {
      String str = String.valueOf( this.weaponType );
      str = str.toLowerCase();
      if( str.contains( "_" ) )
      {
         str = str.replace( "_", "\s" );
         
         StringBuilder output = new StringBuilder( str );
         int i = 0;
         do {
            output.replace(i, i + 1, output.substring(i,i + 1).toUpperCase());
            i =  output.indexOf(" ", i) + 1;
         } while (i > 0 && i < output.length());
         
         return output.toString();
      }
      
      return str;

   }

   public WeaponType getWeaponEnum()
   {
       return this.weaponType;
   }

   /*
    * Test method public static void main( String[] args ) {
    *
    * WeaponType rope = WeaponType.ROPE; WeaponCard test = new WeaponCard(
    * rope );
    *
    * System.out.println( test.getWeaponType() );
    *
    * }
    */

}
