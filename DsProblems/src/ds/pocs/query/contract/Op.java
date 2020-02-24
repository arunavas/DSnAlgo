package ds.pocs.query.contract;

import ds.common.Either;
import ds.common.Pair;

public interface Op<R extends IExpr<R>, A extends IExpr<A>> {
    Either<String, R> exec(IExpr<A> lhs, IExpr<A> rhs);
    Pair<Type, Type> typeParameters();
}