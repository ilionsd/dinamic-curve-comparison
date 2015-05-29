package com.insign.dinamic_curves.matching.matcher;

import com.insign.dinamic_curves.SortedCollection;
import com.insign.dinamic_curves.matching.Referenceable;

import java.util.Collection;
import java.util.List;

/**
 * Created by ilion on 10.05.2015.
 */
public interface Resolver<T>{

	Solution resolve(T toResolve);
}
