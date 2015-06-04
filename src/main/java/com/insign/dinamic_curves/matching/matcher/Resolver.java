package com.insign.dinamic_curves.matching.matcher;

/**
 * Created by ilion on 10.05.2015.
 */
public interface Resolver<T>{

	Solution resolve(T toResolve);
}
