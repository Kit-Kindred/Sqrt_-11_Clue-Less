package Common.Messages.StatusUpdates;

import javax.print.attribute.standard.MediaSize;
import java.io.Serializable;

public class CharacterUpdate extends StatusUpdate implements Serializable
{
    public boolean[] charEnabled;

    public CharacterUpdate(boolean[] newList)
    {
        super();
        charEnabled = new boolean[newList.length];
        System.arraycopy(newList, 0, this.charEnabled, 0, newList.length);
    }

    public CharacterUpdate(CharacterUpdate other)
    {
        this(other.charEnabled);
    }
}
