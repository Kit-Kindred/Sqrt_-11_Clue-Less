package Client.App.views;

/**
 * Interface for all panels that are selectable. Rooms and cards mainly.
 * @author DavidC
 *
 */
public interface SelectablePanel
{

   public void toggleSelectable();
   
   public void select();
   
   public void deselect();
}
