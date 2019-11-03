package com.relcache.core.pocs.query.contract;

import com.relcache.core.ds.Either;
import com.relcache.core.ds.Pair;

public interface Op<R extends IExpr<R>, A extends IExpr<A>> {
    Either<String, R> exec(IExpr<A> lhs, IExpr<A> rhs);
    Pair<Type, Type> typeParameters();
}