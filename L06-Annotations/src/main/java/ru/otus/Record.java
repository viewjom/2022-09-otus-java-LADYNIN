package ru.otus;

public class Record {
    private int passed;
    private int failed;
    private int executed;
    public  Record() {

        this.passed = 0;
        this.failed = 0;
        this.executed = 0;

    }

    public void addPassed() {
         this.passed++;
    }

    public void addFailed() {
        this.failed++;
    }

    public void addExecuted() {
        this.executed++;
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
