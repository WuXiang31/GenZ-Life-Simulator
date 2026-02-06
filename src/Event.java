import java.util.Random;

//This is the abstract class
// Base clas for all game activities event
//To hold the fix change of stats data
//the event type and name, bad luck , and the note


public abstract class Event
{
    //identity and event type
    private final String name;
    private final EventType type;

    // These are filed for the number of changes for the data
    private final int energyData;
    private final int moneyData;
    private final int gradesData;
    private final int happinessData;

    // the bad luck chance
    private final double badLuck;

    private EventStatus status;
    private String note;

    //Constructor
    public Event(String name, EventType type, int energyData, int moneyData,
                 int gradesData, int happinessData, double badLuckChance)
    {
        this.name = name;
        this.type = type;
        this.energyData = energyData;
        this.moneyData = moneyData;
        this.gradesData = gradesData;
        this.happinessData = happinessData;

        this.badLuck = badLuckChance;

        //default status is APPLIED
        this.status = EventStatus.APPLIED;
        this.note = "";
    }

    //Methods
    public void apply(Student student, Random random)
    {
        student.setEnergy(Math.max(0, student.getEnergy() + energyData));
        student.setMoney(student.getMoney() + moneyData);
        student.setGrades(Math.max(0, student.getGrades() + gradesData));
        student.setHappiness(Math.max (0, student.getHappiness() + happinessData));

        if (badLuck > 0.0 && random.nextDouble() < badLuck)
        {
            onBadLuck(student);
        }
    }

    public void cancel()
    {
        this.status = EventStatus.CANCELLED;
        this.note =  "(Cancelled)";
    }

    protected abstract void onBadLuck(Student student);



    //getters/ setter/ toString
    public String getName()
    {
        return name;

    }

    public EventType getType()
    {
        return type;
    }

    public EventStatus getStatus()
    {
        return status;
    }

    protected void setStatus(EventStatus status)
    {
        this.status = status;
    }

    public String getNote()
    {
        return note;
    }

    protected void setNote(String note)
    {
        this.note = note;
    }

    //print out the information of the activities apply
    @Override
    public String toString()
    {
        String suffix = (note != null && !note.isBlank()) ? " - " + note : "";
        return String.format("%s [%s] status=%s%s", name, type, status, suffix);

    }
}
