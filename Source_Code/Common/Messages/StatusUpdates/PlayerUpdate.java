package Common.Messages.StatusUpdates;

import Common.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerUpdate extends StatusUpdate implements Serializable
{
    public ArrayList<Player> p;

    public PlayerUpdate(ArrayList<Player> p)
    {
        super();
        this.p = new ArrayList<Player>();
        for (Player pl : p)
        {
            this.p.add(new Player(pl));
        }
    }
}
