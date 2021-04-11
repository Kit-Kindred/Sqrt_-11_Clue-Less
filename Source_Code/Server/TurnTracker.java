package Server;

public class TurnTracker
{
    boolean CanAccuse;
    boolean CanMove;
    boolean CanSuggest;

    public TurnTracker()
    {
        reset();
    }

    public boolean accuse()
    {
        if(CanAccuse)
        {
            CanAccuse = false;
            return true;
        }
        else
        {
            return false;
        }
    }

    // Should board keep track of guessing in the same room constraint?
    public boolean suggest()
    {
        if(CanSuggest)
        {
            CanSuggest = false;
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean move()
    {
        if(CanMove)
        {
            CanMove = false;
            return true;
        }
        else
        {
            return false;
        }
    }

    // If there's anything that can still be done, this returns false
    public boolean isTurnOver()
    {
        return !(CanAccuse || CanMove || CanSuggest);
    }

    public void reset()
    {
        CanAccuse = true;
        CanMove = true;
        CanSuggest = true;
    }
}
