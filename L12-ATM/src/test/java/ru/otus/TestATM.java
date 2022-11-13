package ru.otus;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.ATM;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestATM {
    private static ATM atm;
    private List<Integer> nominals;

    @BeforeEach
    void makeATM()
    {
        atm = new ATM(10,50,100,500,1000,2000);
    }

    @Test
    void get50() {
        System.out.println("------------- @Test1------------------");
        atm.insertBanknotes(50,5);
        atm.insertBanknotes(10,4);
        atm.insertBanknotes(100,7);
        atm.insertBanknotes(500,4);
        atm.insertBanknotes(1000,2);
        atm.insertBanknotes(2000,1);

        long atmSum = atm.getTotal();

        System.out.println("atmSum " + atmSum);

        assertThat(atmSum).isEqualTo(6990);

        nominals = atm.getMoney(50);

        final var remainder = atm.getTotal();

        assertThat(remainder).isEqualTo(6940);
    }

    @Test
    void get2050() {
        System.out.println("------------- @Test2------------------");
        atm.insertBanknotes(50,5);
        atm.insertBanknotes(10,5);
        atm.insertBanknotes(100,7);
        atm.insertBanknotes(500,4);
        atm.insertBanknotes(1000,2);
        atm.insertBanknotes(2000,1);

        long atmSum = atm.getTotal();

        System.out.println("atmSum " + atmSum);

        assertThat(atmSum).isEqualTo(7000);

        nominals = atm.getMoney(2050);

        final var remainder = atm.getTotal();

        assertThat(remainder).isEqualTo(4950);
    }

    @Test
    void get2530() {
        System.out.println("------------- @Test3------------------");
        atm.insertBanknotes(50,5);
        atm.insertBanknotes(10,5);
        atm.insertBanknotes(100,7);
        atm.insertBanknotes(500,4);
        atm.insertBanknotes(1000,2);
        atm.insertBanknotes(2000,1);

        long atmSum = atm.getTotal();

        System.out.println("atmSum " + atmSum);

        assertThat(atmSum).isEqualTo(7000);

        nominals = atm.getMoney(2530);

        final var remainder = atm.getTotal();

        assertThat(remainder).isEqualTo(4470);
    }

    @Test //Указана сумма, купюр для которой нет в АТМ
    void get2531() {
        System.out.println("------------- @Test4------------------");
        atm.insertBanknotes(50,5);
        atm.insertBanknotes(10,5);
        atm.insertBanknotes(100,7);
        atm.insertBanknotes(500,4);
        atm.insertBanknotes(1000,2);
        atm.insertBanknotes(2000,1);

        long atmSum = atm.getTotal();

        System.out.println("atmSum " + atmSum);

        assertThat(atmSum).isEqualTo(7000);

        nominals = atm.getMoney(2531);

        final var remainder = atm.getTotal();

        assertThat(remainder).isEqualTo(4469);
    }


       @Test //Попытка забрать денег больше чем есть в АТМ
    void get7000() {
        System.out.println("------------- @Test5------------------");
        atm.insertBanknotes(50,5);
        atm.insertBanknotes(10,4);
        atm.insertBanknotes(100,7);
        atm.insertBanknotes(500,4);
        atm.insertBanknotes(1000,2);
        atm.insertBanknotes(2000,1);

        long atmSum = atm.getTotal();

        System.out.println("atmSum " + atmSum);

        assertThat(atmSum).isEqualTo(6990);

        nominals = atm.getMoney(7000);

        final var remainder = atm.getTotal();

        assertThat(remainder).isEqualTo(6940);
    }

    @AfterEach
    void print() {
        System.out.println("------------- @AfterEach------------------");
        for (Integer nominal:nominals){
            System.out.println("Given out Banknote of nominal: " + nominal);
        }
    }
    }