/**
 * Represents the "Throw a party" activity.
 */
public class ThrowPartyEvent extends Event
{
    public ThrowPartyEvent()
    {
        super("Throw a Party", EventType.SOCIAL_THROW_A_PARTY,
                -50, -100, 0, +50, 0.10);

    }

    // Override the parent method to define the specific effect when the bad luck happened
    @Override
    protected void onBadLuck(Student student)
    {
        student.setEnergy(Math.max(0, student.getEnergy() - 10));
        setNote("Massive hangover, lose 10 more Energy.");
    }
}
