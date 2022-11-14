package ru.otus.atm;


public class Cell implements CellInterface {
    private final long nominal;
    private long amount = 0;
    private long amount_interim = -1000;

    public Cell(long nominal) {
        this.nominal = nominal;
    }

    @Override
    public long getNominal() {
        return nominal;
    }

    @Override
    public void addBanknote(int amount) {
        this.amount = this.amount + amount;
    }

    public long getAmountBanknotes() {
        return amount;
    }

    @Override
    public void extractNominal() {
        if (amount_interim < 0) {
            amount_interim = amount;
        }
        amount_interim--;
    }

    @Override
    public void commit() {
        if (amount_interim != -1000) {
            amount = amount_interim;
        }
        amount_interim = -1000;
    }

    @Override
    public void rollback() {
        amount_interim = -1000;
    }

    @Override
    public int hashCode() {

        return (int) (nominal ^ (nominal >>> 32));
    }
}
