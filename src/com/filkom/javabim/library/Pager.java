package com.filkom.javalibman.library;

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

    private void printSpace() {
        beginLine();
        for (int i = 0; i < this.pageWidth; i++) {
            System.out.print(" ");
        }
        endLine();
    }

    private void printEmptySpace() {
        System.out.println();
    }

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

    private String getInput(int spaceBefore) {
        beginLine();
        for (int i = 0; i < spaceBefore; i++) {
            System.out.print(" ");
        }
        System.out.print(this.inputStyle + " ");
        return scanner.nextLine();
    }

    private String customGetInput(String inputStyle, int spaceBefore) {
        beginLine();
        for (int i = 0; i < spaceBefore; i++) {
            System.out.print(" ");
        }

        System.out.print(inputStyle + " ");
        return scanner.nextLine();
    }

    private void beginLine() {
        System.out.print(this.verticalBar);
    }

    private void endLine() {
        System.out.println(this.verticalBar);
    }

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

    public void horizontalSeparator() {
        System.out.print(this.edge);
        for (int i = 0; i < this.pageWidth; i++) {
            System.out.print(horizontalBar);
        }
        System.out.println(this.edge);
    }

    public void header(String message) {
        emptySpace();
        horizontalSeparator();
        spacer();
        messageCenter(message);
        spacer();
        horizontalSeparator();
        spacer();
    }

    public void footer() {
        spacer();
        horizontalSeparator();
        emptySpace();
    }

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

    public void info(String... message) {
        header("Info");
        for (String i : message) {
            message(i);
        }
        spacer();
        message("Press enter to continue");
        spacer();
        input();
        footer();
    }

    public void spacer() {
        printSpace();
    }

    public void spacer(int space) {
        for (int i = 0; i < space; i++) {
            printSpace();
        }
    }

    public void emptySpace() {
        printEmptySpace();
    }

    public void emptySpace(int space) {
        for (int i = 0; i < space; i++) {
            printEmptySpace();
        }
    }

    public void message(String message) {
        printMessage(message, this.defaultSpaceBefore);
    }

    public void message(String message, int spaceBefore) {
        printMessage(message, spaceBefore);
    }

    public String input() {
        return getInput(this.defaultSpaceBefore);
    }

    public String input(int spaceBefore) {
        return getInput(spaceBefore);
    }

    public String customInput(String inputStyle, int spaceBefore) {
        return customGetInput(inputStyle, spaceBefore);
    }

    public String customInput(String inputStyle, boolean inputSeparator) {
        if (inputSeparator) {
            return customGetInput(inputStyle.concat(this.inputSeparator), this.defaultSpaceBefore);
        }
        return customGetInput(inputStyle, this.defaultSpaceBefore);
    }

    public String customInput(String inputStyle) {
        return customGetInput(inputStyle, this.defaultSpaceBefore);
    }
}
