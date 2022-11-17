package ru.otus.atm;

public enum Nominals {
    n2000(2000),
    n1000(1000),
    n500(500),
    n100(100),
    n50(50),
    n10(10);

    private final long value;

    Nominals(long value) {
        this.value = value;
    }

    public long getNNominal() {
        return value;
    }
}