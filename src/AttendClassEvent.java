/**
* Represents the "Attend Uni Class" activity.
*/

public class AttendClassEvent extends Event
{
    //Constructor
    public AttendClassEvent()
    {
        super("Attend Uni Class", EventType.STUDY_ATTEND_CLASS,
                -10, -10, 15, 0, 0.1);

    }

    // Override the parent method to define the specific effect when the bad luck happened
    @Override
    protected void onBadLuck(Student student)
    {
        student.setGrades(Math.max(0, student.getGrades() - 15));
        setNote("Forget your assignment at home, so no grade boost that day, So no grade boost that day.");
    }
}

