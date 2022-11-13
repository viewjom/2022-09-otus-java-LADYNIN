package ru.otus.atm;


public class Cell implements CellInterface {
    private final long nominal;
    private long amount = 0;
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
        amount --;
    }

    @Override
    public int hashCode() {
        int result = (int) (nominal ^ (nominal >>> 32));

        return result;
    }
}
