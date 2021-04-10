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
   
   
   /**
    * 
    * @return The specific weapon name represented by this instance
    */
   public String getWeaponType()
   {
      return String.valueOf( this.weaponType );

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
