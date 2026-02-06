
/**
 * Represents the "Netflix Binge/ Doom Scroll TikTok" activity.
 */
public class NetflixOrTikTokEvent extends Event
{
    public NetflixOrTikTokEvent()
    {
        super("Netflix Binge/ Doom Scroll TikTok ", EventType.SELFCARE_NETFLIX_OR_Doom_Scroll_TikTok,
                0, 0, -25, +15, 0.10);

    }

    // Override the parent method to define the specific effect when the bad luck happened
    @Override
    protected void onBadLuck(Student student)
    {
        student.setGrades(Math.max(0, student.getGrades() - 5));
        setNote("DoomScroll too long, grade -5 extra.");
    }
}
