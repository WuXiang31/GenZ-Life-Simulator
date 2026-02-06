import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Provides utility methods for reading input from the console and displaying
 * messages with optional colored text.
 * <p>
 * This class wraps around a {@link Scanner} instance for input and provides
 * convenience methods for printing messages in standard, red, green, or
 * yellow text using ANSI escape codes. It also includes methods to read
 * integers and strings with basic validation.
 * </p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * Scanner scanner = new Scanner(System.in);
 * ConsoleIO consoleIO = new ConsoleIO(scanner);
 *
 * consoleIO.displayMessage("Hello, world!");
 * consoleIO.displayMessageInGreenText("Success!");
 * int choice = consoleIO.getIntInput("Enter a number between 1 and 5:", 1, 5);
 * String name = consoleIO.getStringInput("Enter your name:");
 * consoleIO.close();
 * }</pre>
 *
 * <p>
 * It is typically passed to other classes or methods (like
 * {@code MenuController}) to centralize and standardize input handling.
 * </p>
 */
public class ConsoleIO
{
    private static final String RED_TEXT = "\u001B[31m";
    private static final String GREEN_TEXT = "\u001B[32m";
    private static final String YELLOW_TEXT = "\u001B[33m";
    private static final String RESET_TEXT = "\u001B[0m";
    private final Scanner CONSOLE;

    /**
     * Constructs a new {@code ConsoleIO} instance using the specified
     * {@link Scanner}.
     * <p>
     * This constructor allows the {@code ConsoleIO} to wrap around an existing
     * {@code Scanner} object, giving more flexibility in how input is provided.
     * For example, it can be used with {@code System.in} for console input, or
     * with a {@code StringReader} or file-based {@code Scanner} during
     * automated testing.
     * </p>
     *
     * <p><strong>Example usage (console input):</strong></p>
     * <pre>{@code
     * Scanner consoleScanner = new Scanner(System.in);
     * ConsoleIO consoleIO = new ConsoleIO(consoleScanner);
     * }</pre>
     *
     * <p><strong>Example usage (testing with predefined input):</strong></p>
     * <pre>{@code
     * String fakeInput = "Test User\n42\n";
     * Scanner testScanner = new Scanner(fakeInput);
     * ConsoleIO consoleIO = new ConsoleIO(testScanner);
     * }</pre>
     *
     * @param scanner the {@code Scanner} to be used for reading user input;
     *                must not be {@code null}.
     */
    public ConsoleIO(Scanner scanner)
    {
        this.CONSOLE = scanner;
    }


    /**
     * Closes the underlying {@link Scanner} instance used by this
     * {@code ConsoleIO}.
     * <p>
     * This method should be called when input is no longer needed, such as when
     * the application is shutting down, to release system resources associated
     * with the input stream.
     * </p>
     *
     * <p><strong>Note:</strong> If this {@code ConsoleIO} was constructed
     * with a {@code Scanner} that wraps {@code System.in}, closing it will also
     * close the standard input stream, which may prevent further input from
     * being read in the application.</p>
     *
     * <p><strong>Example usage:</strong></p>
     * <pre>{@code
     * ConsoleIO consoleIO = new ConsoleIO(new Scanner(System.in));
     * ...
     * consoleIO.close();
     * }</pre>
     */
    public void close()
    {
        if (this.CONSOLE != null)
            CONSOLE.close();
    }

    /**
     * Displays the specified message to the console.
     *
     * @param message the string message to be printed
     */
    public void displayMessage(String message)
    {
        System.out.println(message);
    }


    /**
     * Displays the specified message to the console in green text.
     * <p>
     * This method adds ANSI escape codes to change the text color to green
     * and then resets it back to the default color after printing.
     * </p>
     *
     * @param message the string message to be printed in green
     */
    public void displayMessageInGreenText(String message)
    {
        System.out.println(GREEN_TEXT + message + RESET_TEXT);
    }

    /**
     * Displays the specified message to the console in red text.
     * <p>
     * This method adds ANSI escape codes to change the text color to red
     * and then resets it back to the default color after printing.
     * </p>
     *
     * @param message the string message to be printed in red
     */
    public void displayMessageInRedText(String message)
    {
        System.out.println(RED_TEXT + message + RESET_TEXT);
    }

    /**
     * Displays the specified message to the console in yellow text.
     * <p>
     * This method adds ANSI escape codes to change the text color to yellow
     * and then resets it back to the default color after printing.
     * </p>
     *
     * @param message the string message to be printed in yellow
     */
    public void displayMessageInYellowText(String message)
    {
        System.out.println(YELLOW_TEXT + message + RESET_TEXT);
    }

    /**
     * Prompts the user with the specified message, reads input from the
     * console, and parses it into an integer. The value is then validated to
     * ensure it falls within the specified range.
     * <p>
     * This method internally calls {@link #getStringInput(String)} to read user
     * input.
     * </p>
     *
     * @param messageToPrompt the message displayed to the user before input is
     *                        read
     * @param min             the minimum acceptable value (inclusive)
     * @param max             the maximum acceptable value (inclusive)
     * @return the integer entered by the user, guaranteed to be within the
     * specified range
     * @throws NumberFormatException    if the user input cannot be parsed into
     *                                  an integer
     * @throws IllegalArgumentException if the parsed integer is less than
     *                                  {@code min} or greater than {@code max}
     */
    public int getIntInput(String messageToPrompt, final int min, final int max)
            throws NumberFormatException, InputMismatchException
    {
        int userInput;
        System.out.println(messageToPrompt);
        userInput = Integer.parseInt(CONSOLE.nextLine());

        if (userInput < min || userInput > max)
            throw new IllegalArgumentException();
        return userInput;
    }

    /**
     * Prompts the user with the specified message and reads a line of input
     * from the console.
     * <p>
     * The method trims leading and trailing whitespace from the input before
     * returning it. If the user enters a blank string (empty or only
     * whitespace), an {@link IllegalArgumentException} is thrown.
     * </p>
     *
     * @param messageToPrompt the message displayed to the user before input is
     *                        read
     * @return the trimmed user input as a {@code String}
     * @throws IllegalArgumentException if the input is blank (empty or
     *                                  whitespace only)
     */
    public String getStringInput(String messageToPrompt) throws IllegalArgumentException
    {
        String userInput;

        System.out.println(messageToPrompt);
        userInput = CONSOLE.nextLine();

        if (userInput.isBlank())
            throw new IllegalArgumentException();

        return userInput.trim();
    }

    /**
     * Prints a blank line to the console.
     * <p>
     * This can be used to separate output visually for better readability.
     * </p>
     */
    public void lineBreak()
    {
        System.out.println();
    }

}
