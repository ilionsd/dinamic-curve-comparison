package com.insign.dinamic_curves.matching;

import com.insign.dinamic_curves.SortedCollection;

import java.util.Collection;
import java.util.List;

/**
 * Created by ilion on 10.05.2015.
 */
public interface ConformityResolwer<T> {

	Collection<Integer> resolve(SortedCollection<? extends T> obj1, SortedCollection<? extends T> obj2);
}
