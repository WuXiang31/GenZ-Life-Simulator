/**
 * Represents the "Gym/ Meditation" activity.
 */

public class GymOrMeditationEvent extends Event
{
    public GymOrMeditationEvent()
    {
        super("Gym/ Meditation ", EventType.SELFCARE_GYM_OR_MEDITATION,
                +15, 0, 0, +10, 0.05);

    }

    // Override the parent method to define the specific effect when the bad luck happened
    @Override
    protected void onBadLuck(Student student)
    {
        student.setEnergy(Math.max(0, student.getEnergy() -15));
        student.setHappiness(Math.max(0, student.getHappiness() - 10));

        setNote("Gym/ Meditation is closed or class cancelled, waste the activity, no stat boost.");
    }
}
