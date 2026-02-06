/**
 * Represents the "Skip the day" activity.
 */
public class SkipDayEvent extends Event
{
    public SkipDayEvent()
    {
        super("Skip the day", EventType.SKIP_DAY,
                +15, 0, -15, -15, 0.0);

    }

    // Override the parent method to define the specific effect when the bad luck happened
    @Override
    protected void onBadLuck(Student student)
    {
        //no bad luck for the Skip the day
    }
}
