/**
 * Represents the "Hangout with friends" activity.
 */
public class HangoutEvent extends Event
{
    public HangoutEvent()
    {
        super("Hangout with friends", EventType.SOCIAL_HANGOUT_WITH_FRIENDS,
                -20 , -30, 0, +30, 0.05);
    }

    // Override the parent method to define the specific effect when the bad luck happened
    @Override
    protected void onBadLuck(Student student)
    {
        student.setMoney(student.getMoney() - 30);
        setNote("You lost your wallet, lose 30 more Money.");
    }
}
