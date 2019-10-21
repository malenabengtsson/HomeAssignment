package com.company;

import java.util.Collection;
import java.util.Comparator;

public class Sort <T extends Collection> implements Comparator<T> {
    @Override
    public int compare(T t1, T t2) {
       return 0;
    }
}
