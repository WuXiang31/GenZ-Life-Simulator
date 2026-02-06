import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;


/**
 * The Simulator class is the main controller for the Gen-Z university life simulation game.
 * The class handle to run 7 days loop
 * manager daily events
 * applies rule
 *
 */
public class Simulator
{
    //All the fields that mut be used

    // This is all the point(data) that are deducted or add on Crash day  include like Energy, Grades, Happiness, Money
    private static final int CRASH_ENERGY = 10; //Energy + 10
    private static final int CRASH_GRADES = -10; //Grades -1
    private static final int CRASH_HAPPINESS = -10; //Happiness -10
    private static final int CRASH_MONEY = -10; //Money -10


    //Abrupt end Condition
    private static final int BURNOUT_CONDITION = 30; // if happiness < = 30 for 3 days
    private static final int ACADEMIC_FAILURE_CONDITION = 40; // if grade <= 40 for 3 days
    private static final int EXHAUSTION_CONDITION = 0; //if energy < = 0for 2 day
    private static final int FINANCIAL_CRISIS_CONDITION = -100; //if money <= -100

    // HD part needed, Final exam field
    private static final int TEST_DAY = 5; //the final exam is held on day 5
    private static final int PASSED_GRADE = 50; //the grades need to pass the exam

    private ConsoleIO consoleIO;
    private Random random;

    private Student student;

    private int day;
    private boolean gameOver;

    //  All the Events counted to track on how many and time that the player is choose to do specify event
    private int totalActivities;
    private int studyCount;
    private int workCount;
    private int socialCount;
    private int selfCareCount;
    private int skipDays;
    private int crashDays;

    //this is the fields for tracking of the low energy
    private int trackLowHappinessDays;
    private int trackLowGradesDays;
    private int trackLowEnergyDays;

    //This is the array that use to store all the daily activities that the player had selected day by day
    private final ArrayList<Event> allDailyActivities;

    //All the available events for the day
    private final Event[] eventMenu;


    // Constructors
    public Simulator()
    {

        this.consoleIO = new ConsoleIO(new Scanner(System.in));
        this.random = new Random();


        this.day = 1;
        this.gameOver = false;

        this.trackLowHappinessDays = 0;
        this.trackLowGradesDays = 0;
        this.trackLowEnergyDays = 0;

        this.totalActivities = 0;
        this.studyCount = 0;
        this.workCount = 0;
        this.socialCount = 0;
        this.selfCareCount = 0;
        this.skipDays = 0;
        this.crashDays = 0;

        this.allDailyActivities = new ArrayList<>();

        this.eventMenu = new Event[]
                {
                        new AttendClassEvent(),
                        new StudyAllNighterEvent(),
                        new PartTimeShiftAEvent(),
                        new OvertimeShiftEvent(),
                        new HangoutEvent(),
                        new ThrowPartyEvent(),
                        new NetflixOrTikTokEvent(),
                        new GymOrMeditationEvent(),
                        new SkipDayEvent ()
                };
    }

    // Methods

    // This is the method that use to show the Menu
    public void displayMenu()
    {
        consoleIO.displayMessageInYellowText("======Actions Menu======");
        consoleIO.lineBreak();

        consoleIO.displayMessageInGreenText
                (
                "1. Attend a Uni Class ()\n" +
                "2. All-Nighter Study Grind\n" +
                "3. Part-time Shift\n" +
                "4. Overtime Shift\n" +
                "5. Hangout with friends\n" +
                "6. Throw a Party\n" +
                "7. Netflix Binge/ Doom Scroll TikTok\n" +
                "8. Gym/ Meditation"
                );

        consoleIO.displayMessageInRedText("9. Skip the day");
    }



    //Executes the choice of the play choice after choosing the activity
    //and also do the crash day check if crashed
    //Returns true
    private boolean executeChoice (int choice)
    {
        Event proto = eventMenu[choice - 1];
        Event today = createEventForToday(proto);

        today.apply(student, random);
        allDailyActivities.add(today);
        totalActivities++;

        switch (today.getType())
        {
            case STUDY_ATTEND_CLASS:
            case STUDY_ALL_NIGHTER:
                studyCount++;
                break;

            case WORK_PART_TIME_SHIFT:
            case WORK_OVERTIME_SHIFT:
                workCount++;
                break;

            case SOCIAL_HANGOUT_WITH_FRIENDS:
            case SOCIAL_THROW_A_PARTY:
                socialCount++;
                break;

            case SELFCARE_NETFLIX_OR_Doom_Scroll_TikTok:
            case SELFCARE_GYM_OR_MEDITATION:
                selfCareCount++;
                break;

            case SKIP_DAY:
                //skipDays already count in runDay
                break;

            default:
                break;

        }

        consoleIO.displayMessageInYellowText(today.toString());

        //do the crash day check if crash return true
        if (student.getEnergy() <= 0 || student.getHappiness() <= 0)
        {
            crashDay();
            return true;
        }
        return false;


    }

    // this is the method use to run a one single day activities that the play choose
    public void dayActivities()
    {
        // show to menu to the use
        displayMenu();

        // take the user choice of the activities 1
        int activity1 = getIntInput("Choose action 1 (1 to 9): ", 1, 9);

        // if the user choose number which means the user wish to skip the day
        // therefore, no more activity 2 is chosen and only apply to the skip day rule
        // and increment the skip day count
        if (activity1 == 9)
        {
            executeChoice(activity1);
            skipDays++;
            return; // return back
        }

        // take the second input of the activities 2 from the player
        int activity2 = getIntInput("Choose action 2 (1 to 9): ",1,9);

        //if the player choose the number 2 on the second activities
        // then we execute the first activities as regular and execute the second activities as skip day
        // and increment the skip day count
        if (activity2 == 9)
        {
            executeChoice(activity1);
            executeChoice(9);
            skipDays++;
            return;
        }

        //if crash happens, stop for today activities
        boolean crashed = executeChoice(activity1);
        if (crashed)
        {
            return;
        }

        //apply the second activities
        executeChoice(activity2);
    }

    //This method is used to run the main 7 days loop
    // Each day it will display the current stas and let the play to decided their activities
    // also include the final exam check and apply all the early termination rules
    public void startSimulation()
    {
        consoleIO.displayMessage("\n--- Simulation Loop Starts ---");

        // 7 days of looping
        for (this.day = 1 ; this.day <= 7 && !this.gameOver; this.day++)
        {
            consoleIO.lineBreak();
            consoleIO.displayMessageInGreenText("Day " + this.day);

            consoleIO.displayMessage(this.student.toString());

            dayActivities();

            if (!this.gameOver)
            {
                // check if it is on the exam data
                finalExam();
            }

            if (!this.gameOver)
            {
                // check if it matches any early termination rule
                earlyTermination();
            }

        }

        // show the final report after the simulation finish
        if (!this.gameOver)
        {
            finalReport();
            consoleIO.displayMessageInYellowText("Simulation finished.");
        }
        else //if early termination print out the line below
        {
            consoleIO.displayMessageInYellowText("You are force to end the Game.");
        }
    }



    //this is the method that use to perform the Crash day
    private void crashDay()
    {
        crashDays++;

        consoleIO.lineBreak();
        consoleIO.displayMessageInRedText("Energy or Happiness are Completely drained.\n" + "Crashed! Forced into bed.");

        // do the calculation of stats change on the crash day
        int energy = Math.max(0, student.getEnergy() + CRASH_ENERGY); //current energy +10
        int grades = Math.max(0, student.getGrades() + CRASH_GRADES); //current grades -10
        int happiness = Math.max(0, student.getHappiness() + CRASH_HAPPINESS ); //current happiness -10
        int money = student.getMoney() + CRASH_MONEY; //current money -10


        //set the stats after the calculation
        student.setEnergy(energy);
        student.setGrades(grades);
        student.setHappiness(happiness);
        student.setMoney(money);
    }

    // Check with some condition if it is fit with the Early Termination
    private void earlyTermination()
    {
        consoleIO.displayMessage("\nEnd of Day "+ day );
        consoleIO.displayMessage(student.toString());

        //Burnout because low energy
        if (student.getHappiness() <= BURNOUT_CONDITION)
        {
            trackLowHappinessDays++; // increment teh burnout day count
        }
        else
        {
            trackLowHappinessDays = 0; // if not continue burnout set back to 0
        }


        //Academic Failure Condition
        if (student.getGrades() <= ACADEMIC_FAILURE_CONDITION)
        {
            trackLowGradesDays++; //increment the academic failure day count
        }
        else
        {
            trackLowGradesDays = 0; // if not continue academic failure set back to 0
        }

        //Exhaustion Condition
        if (student.getEnergy() <= EXHAUSTION_CONDITION)
        {
            trackLowEnergyDays++; //increment the low energy day count
        }
        else
        {
            trackLowEnergyDays =0; // if not continue exhaustion condition failure set back to 0
        }


        //Financial Crisis Condition
        if (student.getMoney() <= FINANCIAL_CRISIS_CONDITION)
        {

            consoleIO.displayMessageInRedText("You went bankrupt and had to drop out.\n"
                    + "You can't even afford a ramen");
            gameOver = true;//if no more money than -100, game over
        }

        //check if satisfy the continues 3 days burned out
        if (trackLowHappinessDays >= 3)
        {
            consoleIO.displayMessageInRedText("You burned out and couldn't continue.");
            gameOver = true; //if yes, game over
        }

        //check if satisfy teh continues 3 day academic failure
        if (trackLowGradesDays >= 3)
        {
            consoleIO.displayMessageInRedText("You failed your studies and were removed from uni.");
            gameOver = true; //if yes, game over
        }

        //check if satisfy the continues 2 days exhaustion
        if (trackLowEnergyDays >= 2)
        {
            consoleIO.displayMessageInRedText("You collapsed from exhaustion and couldn't continue.");
            gameOver = true; //if yes, game over
        }

    }

    // The HD part, when reach the day five it is time to take the final exam
    // This is the method where it handel to the final exam
    private void finalExam()
    {

        if (this.day == TEST_DAY )
        {
            consoleIO.lineBreak();
            consoleIO.displayMessageInYellowText("Final Exam DAY!!");
            consoleIO.lineBreak();

            //if failed the test then it will be ends final immediately
            if (student.getGrades() < PASSED_GRADE)
            {
                consoleIO.displayMessageInRedText(" Final exam (Day " + TEST_DAY + "): Your Grades < "
                        + PASSED_GRADE + ". You failed the final exam and had to withdraw from Uni." );
                gameOver = true;
            }
            else //if passed the test
            {
                consoleIO.displayMessageInGreenText("Congratulation, You passed the final exam.\n"
                        + "You may continues your game");
            }

        }

    }


    //Final report and calculated the GZSS score and the final status
    private void finalReport()
    {
        consoleIO.displayMessageInGreenText("====== Final Report =======");
        consoleIO.lineBreak();

        //Total evens attended
        consoleIO.displayMessage("Total event attended: " + totalActivities);

        //Tyes of event attended
        consoleIO.displayMessage("- Study: " + studyCount );
        consoleIO.displayMessage("- Work: " + workCount);
        consoleIO.displayMessage("- Social: "+ socialCount);
        consoleIO.displayMessage("- Self-care: " + selfCareCount);
        consoleIO.lineBreak();

        //Final stats
        consoleIO.displayMessageInGreenText("--- Final Stats ---");

        int finalEnergy = student.getEnergy();
        int finalMoney = student.getMoney();
        int finalGrades = student.getGrades();
        int finalHappiness = student.getHappiness();

        double gazz = (finalEnergy  + finalMoney + finalGrades + finalHappiness )/ 4.0;

        consoleIO.displayMessage(String.format("Energy:%d Money:%d Grades:%d Happiness:%d",
                finalEnergy , finalMoney, finalGrades, finalHappiness ));

        consoleIO.displayMessage(String.format("GZSS = %.2f", gazz));

        //Days skipped
        consoleIO.displayMessage("- Skip days: "+ skipDays);
        //Days crashed
        consoleIO.displayMessage("- Crash days: " + crashDays);

        // Outcome
        String ending;
        if (gazz >= 80) ending = "Thriving Student";
        else if (gazz >= 60) ending = "Balanced but Tired";
        else if (gazz >= 40) ending = "Hanging by Thread";
        else if (gazz >= 20) ending = "Burned Out";
        else ending = "Gen - Z Disaster";

        consoleIO.displayMessageInGreenText("Outcome: " + ending);
    }




    //This is the UI interface when the starts the program it will print out this menu

    public void startGame()
    {
        consoleIO.lineBreak();
        consoleIO.displayMessageInYellowText("=== Welcome to the GenZ university simulation Game ===");
        consoleIO.lineBreak();

        //ask user to input there character name that they want to use in the game
        String name = getStringInput("What is your name: ");

        //will random generate a random a 4 digits number
        int studentID = random.nextInt(9999);

        this.student = new Student(name, studentID);

        consoleIO.displayMessage("\nWelcome "  + name + " !");
        consoleIO.displayMessage("Your automatically generate Student ID  is: " + studentID);
        consoleIO.lineBreak();

        consoleIO.displayMessageInGreenText("Gen-Z University life begins. \nGOOD LUCK!!!");

        this.startSimulation();

    }


    //This method is used to make a fresh event for today from whatever the player had chosen in the menu
    private Event createEventForToday(Event proto)
    {
        switch (proto.getType())
        {
            case STUDY_ATTEND_CLASS:
                return new  AttendClassEvent();
            case STUDY_ALL_NIGHTER:
                return new StudyAllNighterEvent();
            case WORK_PART_TIME_SHIFT:
                return new PartTimeShiftAEvent();
            case WORK_OVERTIME_SHIFT:
                return new OvertimeShiftEvent();
            case SOCIAL_HANGOUT_WITH_FRIENDS:
                return new HangoutEvent();
            case SOCIAL_THROW_A_PARTY:
                return new ThrowPartyEvent();
            case SELFCARE_NETFLIX_OR_Doom_Scroll_TikTok:
                return new NetflixOrTikTokEvent();
            case SELFCARE_GYM_OR_MEDITATION:
                return new GymOrMeditationEvent();
            case SKIP_DAY:
                return new SkipDayEvent();

            default:
                return new SkipDayEvent();
        }
    }




    //do validation check for the input String form the user
    private String getStringInput(String messageToPrompt)
    {
        String userInput = "";

        do
        {
            try
            {
                userInput = consoleIO.getStringInput(messageToPrompt);
            } catch (IllegalArgumentException e)
            {
                consoleIO.displayMessageInRedText("‼️Invalid input! Input must " +
                        "not be empty or blank.");
            }
        } while (userInput.isBlank());

        return userInput.trim();
    }

    // do some validation check for the input Integer from the user
    private int getIntInput(String messageToPrompt, int min, int max)
    {
        int userInput = 0;

        do
        {
            try
            {
                userInput = consoleIO.getIntInput(messageToPrompt, min, max);
            } catch (NumberFormatException nfe)
            {
                consoleIO.displayMessageInRedText("‼️Invalid input! Please enter " +
                        "a numeric input (" + min + " to " + max + ").");
            } catch (IllegalArgumentException iae)
            {
                consoleIO.displayMessageInRedText("‼‼️Invalid input! Input must be a number " +
                        "between " + min + " and " + max + ")");
            }
        } while (userInput < min || userInput > max);


        return userInput;
    }


}
