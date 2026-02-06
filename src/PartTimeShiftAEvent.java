/**
 * Represents the "part time shift" activity.
 */

public class PartTimeShiftAEvent extends Event
{
    public PartTimeShiftAEvent()
    {
        super("Part-time Shift", EventType.WORK_PART_TIME_SHIFT,
                -20, + 50, 0, 0, 0.1);

    }

    // Override the parent method to define the specific effect when the bad luck happened
    @Override
    protected void onBadLuck(Student student)
    {
        student.setMoney(student.getMoney() - 50);
        setNote("Your bicycle tire pops on the way, you miss work (no money earned).");
    }
}
