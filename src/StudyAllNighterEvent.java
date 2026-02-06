/**
 * Represents the "Study All nighter event" activity.
 */
public class StudyAllNighterEvent extends Event
{
    public StudyAllNighterEvent()
    {
        super("All-Nigher Study Grind", EventType.STUDY_ALL_NIGHTER,
                -40, 0, +30, -20, 0.05);

    }

    // Override the parent method to define the specific effect when the bad luck happened
    @Override
    protected void onBadLuck(Student student)
    {
        student.setGrades(Math.max(0, student.getEnergy() - 15));
        setNote("Catching a cold from late-night study, lose 10 more energy");
    }
}