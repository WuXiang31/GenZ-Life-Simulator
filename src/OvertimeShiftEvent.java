/**
 * Represents the "overtime shift " activity.
 */
public class OvertimeShiftEvent extends Event
{
    public OvertimeShiftEvent()
    {
        super("Overtime Shift", EventType.WORK_OVERTIME_SHIFT,
                -35, +100, 0, -15, 0.05);

    }

    // Override the parent method to define the specific effect when the bad luck happened
    @Override
    protected void onBadLuck(Student student)
    {
        student.setHappiness(Math.max(0, student.getHappiness() - 10));
        setNote("Spilling coffee on a customer, lose 10 more Happiness.");
    }

}
