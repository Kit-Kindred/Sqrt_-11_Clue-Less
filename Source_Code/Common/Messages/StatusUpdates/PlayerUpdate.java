package Common.Messages.StatusUpdates;

import java.util.ArrayList;

import Common.Player;

public class PlayerUpdate extends StatusUpdate
{
   
   private static final long serialVersionUID = -3375789620849010144L;
   public ArrayList<Player> players;
   
   public PlayerUpdate( ArrayList<Player> players )
   {
      this.players = players;
   }
   
   
}
