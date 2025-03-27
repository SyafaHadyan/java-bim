package util;

import java.util.Scanner;

public class Pager {
    private int pageWidth;
    private int defaultSpaceBefore;
    private char horizontalBar;
    private char verticalBar;
    private char edge;
    private String inputStyle;
    private String inputSeparator;
    private Scanner scanner;

    /* Page constructor to initialize default variables */
    public Pager() {
        this.pageWidth = 75;
        this.defaultSpaceBefore = 2;
        this.horizontalBar = '-';
        this.verticalBar = '|';
        this.edge = '+';
        this.inputStyle = "$";
        this.inputSeparator = ":";
        this.scanner = new Scanner(System.in);
    }

    /* Page constructor with parameters */
    public Pager(int pageWidth, int defaultSpaceBefore, char horizontalBar, char verticalBar, char edge,
            String inputStyle, String inputSeparator) {
        this.pageWidth = pageWidth;
        this.defaultSpaceBefore = defaultSpaceBefore;
        this.horizontalBar = horizontalBar;
        this.verticalBar = verticalBar;
        this.edge = edge;
        this.inputStyle = inputStyle;
        this.inputSeparator = inputSeparator;
        this.scanner = new Scanner(System.in);
    }

    /* Will print empty space (with border) */
    private void printSpace() {
        beginLine();
        for (int i = 0; i < this.pageWidth; i++) {
            System.out.print(" ");
        }
        endLine();
    }

    /*
     * Just a wrapper to print empty space, better than to put random \n or
     * System.out.println();
     */
    private void printEmptySpace() {
        System.out.println();
    }

    /* Print message (and calculate remaining space (to print border)). */
    private void printMessage(String message, int spaceBefore) {
        int spaceAfter = pageWidth - spaceBefore - message.length();

        beginLine();
        for (int i = 0; i < spaceBefore; i++) {
            System.out.print(" ");
        }
        System.out.print(message);
        for (int i = 0; i < spaceAfter; i++) {
            System.out.print(" ");
        }
        endLine();
    }

    /* Wrapper of input, but to put after left border and return String. */
    private String getInput(int spaceBefore) {
        beginLine();
        for (int i = 0; i < spaceBefore; i++) {
            System.out.print(" ");
        }
        System.out.print(this.inputStyle + " ");
        return scanner.nextLine();
    }

    /*
     * Instead of using the default variable of input (read the variable above) it
     * takes a String then place it before taking user input.
     *
     * Example:
     * Pager::customInput("Name", 1);
     *
     * Result:
     * | Name <user input here>
     */
    private String customGetInput(String inputStyle, int spaceBefore) {
        beginLine();
        for (int i = 0; i < spaceBefore; i++) {
            System.out.print(" ");
        }

        System.out.print(inputStyle + " ");
        return scanner.nextLine();
    }

    /* Wrapper to print border */
    private void beginLine() {
        System.out.print(this.verticalBar);
    }

    /* Wrapper to print border */
    private void endLine() {
        System.out.println(this.verticalBar);
    }

    /* Setters */
    public void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
    }

    public void setDefaultSpaceBefore(int defaultSpaceBefore) {
        this.defaultSpaceBefore = defaultSpaceBefore;
    }

    public void setHorizontalBar(char bar) {
        this.horizontalBar = bar;
    }

    public void setVerticalBar(char bar) {
        this.verticalBar = bar;
    }

    public void setEdge(char edge) {
        this.edge = edge;
    }

    public void setInputStyle(String inputStyle) {
        this.inputStyle = inputStyle;
    }

    /* Will print n time (where n is page width, (read the variable above)) */
    public void horizontalSeparator() {
        System.out.print(this.edge);
        for (int i = 0; i < this.pageWidth; i++) {
            System.out.print(horizontalBar);
        }
        System.out.println(this.edge);
    }

    /*
     * Wrapper to print horizontal separator, spacer, message (center), spacer,
     * horizontal separator (again), then end with spacer, all with just one
     * function call. Than manually calling each function one by one.
     *
     * Example:
     *
     * Header("Java Book Inventory Manager v1.0.0")
     *
     * Result:
     *
     * +---------------------------------------------------------------------------+
     * |***************************************************************************|
     * |********************Java Book Inventory Manager v1.0.0*********************|
     * |***************************************************************************|
     * +---------------------------------------------------------------------------+
     * |***************************************************************************|
     *
     *
     * Last line is also included in the function call to separate with content.
     */
    public void header(String message) {
        emptySpace();
        horizontalSeparator();
        spacer();
        messageCenter(message);
        spacer();
        horizontalSeparator();
        spacer();
    }

    /* Wrapper to print footer. */
    public void footer() {
        spacer();
        horizontalSeparator();
        emptySpace();
    }

    /*
     * Will print messsage in the center of the box by calculating input and box
     * width.
     */
    public void messageCenter(String message) {
        int size = this.pageWidth - message.length();

        beginLine();
        for (int i = 0; i < size / 2; i++) {
            System.out.print(" ");
        }
        System.out.print(message);
        for (int i = 0; i < size / 2; i++) {
            System.out.print(" ");
        }
        if (this.pageWidth % 2 == 0 && message.length() % 2 != 0) {
            System.out.print(" ");
        } else if (this.pageWidth % 2 != 0 && message.length() % 2 == 0) {
            System.out.print(" ");
        }
        endLine();
    }

    /*
     * Will print info page (instead of continuing / exiting, this function will
     * call input (and will not store it anywhere), when user presses enter, it will
     * exit the function and continue). Useful if you want to display important info
     * to user.
     */
    public void info(String... message) {
        header("Info");
        for (String i : message) {
            message(i);
        }
        spacer();
        message("Press enter to continue");
        spacer();
        customInput();
        footer();
    }

    /* Will print space */
    public void spacer() {
        printSpace();
    }

    /*
     * Will print space as well, but it will print space n times (where n is user
     * input from parameter).
     */
    public void spacer(int space) {
        for (int i = 0; i < space; i++) {
            printSpace();
        }
    }

    /* Will print empty space. */
    public void emptySpace() {
        printEmptySpace();
    }

    /* Will print empty space n times. */
    public void emptySpace(int space) {
        for (int i = 0; i < space; i++) {
            printEmptySpace();
        }
    }

    /*
     * Print message (but without specfying space before message (using default
     * value)).
     */
    public void message(String message) {
        printMessage(message, this.defaultSpaceBefore);
    }

    /* Print message (with space before). */
    public void message(String message, int spaceBefore) {
        printMessage(message, spaceBefore);
    }

    /* Will take user input and return String. */
    public String input() {
        return getInput(this.defaultSpaceBefore);
    }

    /* Will take user input (with specified space before user input) */
    public String input(int spaceBefore) {
        return getInput(spaceBefore);
    }

    /*
     * Will print custom input, then take user input (see private function above).
     */
    public String customInput(String inputStyle, int spaceBefore) {
        return customGetInput(inputStyle, spaceBefore);
    }

    /* If true, will add ":" after custom input. */
    public String customInput(String inputStyle, boolean inputSeparator) {
        if (inputSeparator) {
            return customGetInput(inputStyle.concat(this.inputSeparator), this.defaultSpaceBefore);
        }
        return customGetInput(inputStyle, this.defaultSpaceBefore);
    }

    /* Custom input, but only taking input style. */
    public String customInput(String inputStyle) {
        return customGetInput(inputStyle, this.defaultSpaceBefore);
    }

    /* Custom input (currently only used by Pager::info). */
    public String customInput() {
        return customGetInput("", this.defaultSpaceBefore - 1);
    }
}
