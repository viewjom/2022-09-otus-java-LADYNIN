package ru.otus.atm;


public class Cell implements CellInterface {
    private long nominal = 0;
    private long amount = 0;
    private long amount_interim = 0;

    public Cell(long nominal) {
        for(Nominals n : Nominals.values())
        {
            if (n.getNNominal() == nominal) {
                this.nominal = nominal;
                break;
            }
        }
        if (this.nominal == 0) {
            System.out.println("Нет ячеек номиналом " + nominal + ".");
        }
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
        amount_interim--;
    }

    @Override
    public void commit() {
        amount = amount + amount_interim;
    }

    @Override
    public int hashCode() {

        return (int) (nominal ^ (nominal >>> 32));
    }
}
