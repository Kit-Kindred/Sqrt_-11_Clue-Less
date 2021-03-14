package Common;

/*
 * This class might be useless; I was thinking it could be useful to have a
 * parent class for the card types so the enums don't get out of hand.
 * Fortunately, the inheritance is super "loose" so we can kill this easily
 */

public class Card
{

   private boolean nameModified = false;
   
   
   Card()
   {

   }

   
   
   /**
    * Sets an indicator so that the program knows that the card name needs
    * to be overwritten. This lets us keep the default names hard-coded in the
    * program as backups.
    */
   public void modifyName()
   {
      this.nameModified = true;

   }


   /**
    * Sets the modified name toggle to false. This will cause the program to
    * use default names rather than character defined names.
    */
   public void resetName()
   {
      this.nameModified = false;

   }
   
   
   public boolean isNameModified()
   {
      return this.nameModified;
   }
   
   
}
