package ru.otus.atm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class ATM {

    //Ячейки
    private final List<CellInterface> cells = new ArrayList<>();

    public ATM(int... nominal) {

        Arrays.stream(nominal).forEach(n -> cells.add(new Cell(n)));

    }

    public void insertBanknotes(int nominal, int amount) {
        cells.stream().filter(n -> n.getNominal() == nominal).forEach(b -> b.addBanknote(amount));
    }

    public long getTotal() {
        return cells.stream()
                .map(cell -> cell.getNominal() * cell.getAmountBanknotes())
                .reduce(0L, Long::sum);
    }

    public List<Integer> getMoney(long sum) {
        List<Integer> outNominals = new ArrayList<>();
        long outSum = 0;
        // byte bCount = 0;
        if (getTotal() < sum) {
            throw new RuntimeException("Недостаточно банкнот в банкомате!");
        }

       /* cells.stream()
                .sorted(Comparator.comparingInt(i -> i.hashCode()).reversed())
                .forEach(s->System.out.println("Nominal: " + s.getNominal()));
        */

        //Перебираем ячейки начиная с максимального номинала
        for (CellInterface cell : cells.stream()
                .sorted(Comparator.comparingInt(Object::hashCode).reversed()).toList()) {


            if (cell.getNominal() <= sum) {
                //System.out.println("Go to Nominal " + cell.getNominal());
                while ((sum - outSum >= cell.getNominal())
                        //        && (bCount <= 10)
                        && (outSum < sum)
                ) {
                    //  bCount++;
                    cell.extractNominal();
                    outNominals.add((int) cell.getNominal());
                    outSum += cell.getNominal();
                    // System.out.println("Total=" + cell.getAmountBanknotes() * cell.getNominal() + " OSTATOK=" + (sum - outSum));
                }
            }
        }

        if (outSum == sum) {
            cells.forEach(CellInterface::commit);
            return outNominals;
        } else {
            cells.forEach(CellInterface::rollback);
            throw new RuntimeException("Пожалуйста, введите другую сумму!");
        }
    }
}
