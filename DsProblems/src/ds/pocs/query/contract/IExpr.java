package ds.pocs.query.contract;

import ds.common.Either;

public interface IExpr<T> {
    Either<String, T> eval();
    Type type();
}