
/**
 * GenZLifeSimulator class
 * The main entry point for the text-based life simulation game.
 * Its primary responsibility is to create and run the Simulator.
 *
 * Author: [Sopheakta Ngar]
 * FIT 1051
 */

public class GenZLifeSimulator
{
    public static void main(String[] args)
    {
        // create the main game simulator
        Simulator simulator = new Simulator();
        //start teh simulation loop
        simulator.startGame();

    }
}
