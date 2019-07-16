package ds.common;

import java.util.function.Function;

public abstract class Either<L, R> {

	L leftValue;
	R rightValue;
	EitherFields which;
	
	protected void Left(L left) {
		this.leftValue = left;
		this.rightValue = null;
		this.which = EitherFields.LEFT;
	}

	protected void Right(R right) {
		this.leftValue = null;
		this.rightValue = right;
		this.which = EitherFields.RIGHT;
	}
	
	public EitherFields which() {
		return this.which;
	}
	
	public L left() {
		return this.leftValue;
	}
	
	public R right() {
		return this.rightValue;
	}
	
	@Override
	public String toString() {
		return which() == EitherFields.LEFT ? "Left (" + left() + ")" : "Right (" + right() + ")";
	}
	
	public <B> Either<L, B> map(Function<R, B> f) {
		Either<L, B> mappedEither = null;
		switch (which) {
		case LEFT:
			mappedEither = new Left<L, B>(leftValue);
			break;
		case RIGHT:
			mappedEither = new Right<L, B>(f.apply(rightValue));
			break;
		}
		return mappedEither;
	}

	public <B> Either<L, B> flatMap(Function<R, Either<L, B>> f) {
		Either<L, B> mappedEither = null;
		switch (which) {
		case LEFT:
			mappedEither = new Left<L, B>(leftValue);
			break;
		case RIGHT:
			mappedEither = f.apply(rightValue);
			break;
		}
		return mappedEither;
	}
	
	public static enum EitherFields {
		LEFT, RIGHT;
	}
}