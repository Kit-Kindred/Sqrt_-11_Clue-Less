package Common.Messages.StatusUpdates;

import java.io.Serializable;

public class CharacterUpdate extends StatusUpdate implements Serializable
{
    public boolean[] charEnabled;

    public CharacterUpdate(boolean[] newList)
    {
        super();
        this.charEnabled = newList;
    }
}
