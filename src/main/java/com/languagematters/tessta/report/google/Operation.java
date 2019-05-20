package com.languagematters.tessta.report.google;

/**
 * The data structure representing _ diff is _ Linked list of Diff objects:
 * {Diff(Operation.DELETE, "Hello"), Diff(Operation.INSERT, "Goodbye"),
 * Diff(Operation.EQUAL, " world.")}
 * which means: delete "Hello", add "Goodbye" and keep " world."
 */
public enum Operation {
    DELETE, INSERT, EQUAL
}
