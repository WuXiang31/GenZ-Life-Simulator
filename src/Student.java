//This Student Class is used to represents the player character in the simulation.
//Tracks teh student's core stats
//including energy, money, grades, and happiness

public class Student
{
    //student information and stats
    private int studentID;
    private String name;
    private int energy;
    private int money;
    private int grades;
    private int happiness;


    //initial value of the for status
    private static final int INITIAL_ENERGY = 50;
    private static final int INITIAL_MONEY = 100;
    private static final int INITIAL_GRADES = 50;
    private static final int INITIAL_HAPPINESS = 50;


    //Default constructor for the Student class
    public Student()
    {
        this.studentID = 123456;
        this.name = "WX";
        this.energy = INITIAL_ENERGY;
        this.money = INITIAL_MONEY;
        this.grades = INITIAL_GRADES;
        this.happiness = INITIAL_HAPPINESS;
    }


    //Parameter constructor for the Student class
    public Student(String name, int studentID)
    {
        this.name = name;
        this.studentID = studentID;
        this.energy = INITIAL_ENERGY;
        this.money = INITIAL_MONEY;
        this.grades = INITIAL_GRADES;
        this.happiness = INITIAL_HAPPINESS;
    }



    //Accessor for the field
    //getter
    public String getName()
    {
        return this.name;
    }

    public int getEnergy()
    {
        return this.energy;
    }

    public int getMoney()
    {
        return this.money;
    }

    public int getGrades()
    {
        return this.grades;
    }

    public int getHappiness()
    {
        return this.happiness;
    }


    //Mutator for the field
    //setter
    public void setEnergy(int newEnergy)
    {
        this.energy = newEnergy;
    }

    public void setMoney(int newMoney)
    {
        this.money = newMoney;
    }

    public void setGrades(int newGrades)
    {
        this.grades = newGrades;
    }

    public void setHappiness(int newHappiness)
    {
        this.happiness = newHappiness;
    }



    //Return a string of the student current stats
    @Override
    public String toString()
    {
        String Stats = String.format
                (
                        "====== Student Status ======\n"+
                                "Energy: %d\n" +
                                "Money: %d\n" +
                                "Grades: %d\n" +
                                "Happiness: %d\n",
                        this.energy,
                        this.money,
                        this.grades,
                        this.happiness
                );
        return Stats;
    }



}
