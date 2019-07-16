package ds.common;

public class Right<L, R> extends Either<L, R> {
	public Right(R value) {
		super.Right(value);
	}
}
