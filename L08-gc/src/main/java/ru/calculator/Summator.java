package ru.calculator;

import java.util.ArrayList;
import java.util.List;

public class Summator {
    private Integer sum = 0;
    private Integer prevValue = 0;
    private Integer prevPrevValue = 0;
    private Integer sumLastThreeValues = 0;
    private Integer someValue = 0;

    private Integer iFor = 0;

    private final List<Data> listValues = new ArrayList<>();

    //!!! сигнатуру метода менять нельзя
    public void calc(Data data) {
        listValues.add(data);
        if (listValues.size() % 6_600_000 == 0) {
            listValues.clear();
        }
        sum += data.getValue();

        sumLastThreeValues = data.getValue() + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = data.getValue();
         iFor = (sumLastThreeValues * sumLastThreeValues / (data.getValue() + 1) - sum);
       /* for (var idx = 0; idx < 3; idx++) {
            someValue += (sumLastThreeValues * sumLastThreeValues / (data.getValue() + 1) - sum);
            someValue = Math.abs(someValue) + listValues.size();
            //someValue = someValue & 0x7fffffff+ listValues.size();
        }*/

               //512 spend msec:15021, sec:15
               //256 Execution failed for task ':L08-gc:CalcDemo.main()'.
                //someValue =  (Math.abs(iFor * 2)  + iFor) + listValues.size()*3;

        //256 spend msec:18117, sec:18
        //512 spend msec:14965, sec:14
         someValue = ((iFor * 2) & 0x7fffffff + iFor) + listValues.size()*3;




    }

    public Integer getSum() {
        return sum;
    }

    public Integer getPrevValue() {
        return prevValue;
    }

    public Integer getPrevPrevValue() {
        return prevPrevValue;
    }

    public Integer getSumLastThreeValues() {
        return sumLastThreeValues;
    }

    public Integer getSomeValue() {
        return someValue;
    }
}