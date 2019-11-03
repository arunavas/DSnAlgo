package com.relcache.core.pocs.query.contract;

import com.relcache.core.ds.Either;

public interface IExpr<T> {
    Either<String, T> eval();
    Type type();
}