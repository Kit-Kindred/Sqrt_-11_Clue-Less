package Common;

public class WeaponCard extends Card
{

   // Must instantiate the weapon card with the weapon type
   WeaponCard( WeaponType weaponType )
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


   public String getWeaponType()
   {
      return this.weaponType.toString();

   }


   enum WeaponType
   {
      ROPE, LEAD_PIPE, KNIFE, WRENCH, CANDLESTICK, REVOLVER;
   }


   WeaponType weaponType;

   /*
    * Test method 
    * public static void main( String[] args ) {
    * 
    * WeaponType rope = WeaponType.ROPE; WeaponCard test = new WeaponCard(
    * rope );
    * 
    * System.out.println( test.getWeaponType() );
    * 
    * }
    */

}
