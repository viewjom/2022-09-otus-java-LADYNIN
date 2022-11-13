package ru.otus.atm;

public interface CellInterface {

    void addBanknote(int amount);

    long getAmountBanknotes();

    long getNominal();

    void extractNominal();
}
