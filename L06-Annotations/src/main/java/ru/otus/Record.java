package ru.otus;

public record  Record() {
    private static int passed;
    private static int failed;
    private static int executed;

    public void addPassed() {
        passed++;
    }

    public void addFailed() {
        failed++;
    }

    public void addExecuted() {
        executed++;
    }

    public int getPassed() {
        return passed;
    }

    public int getFailed() {
        return failed;
    }

    public int getExecuted() {
        return executed;
    }
}