package ds.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public final class ListUtil {

	public static <A> Optional<A> head(List<A> l) {
		if (l.isEmpty()) return Optional.empty();
		else return Optional.of(l.get(0));
	}

	/**
	 * The find function takes a predicate and a list and returns the first element in the list matching the predicate, or empty if there is no such element.
	 * @param p - predicate to match the value against
	 * @param l - list to search for
	 * @return - if found, value wrapped in optional otherwise empty
	 */
	public static <A> Optional<A> find(Predicate<A> p, List<A> l) {
		Optional<A> res = Optional.empty();
		for (int i = 0; i < l.size() && !res.isPresent(); i++) {
			if(p.test(l.get(i))){
				res = Optional.of(l.get(i));
			}
		}
		
		return res;
	}
	
	/**
	 * applied to a predicate p and a list xs, returns the longest prefix (possibly empty) of xs of elements that satisfy p:
	 *  > takeWhile (x -> x < 3) [1,2,3,4,1,2,3,4] == [1,2]
	 *  > takeWhile (x -> x < 9) [1,2,3] == [1,2,3]
	 *  > takeWhile (x -> x < 0) [1,2,3] == []
	 * @param p
	 * @param l
	 * @return
	 */
	public static <A> List<A> takeWhile(Predicate<A> p, List<A> l) {
		List<A> result = new ArrayList<A>();
		for (int i = 0; i < l.size() && p.test(l.get(i)); i++) {
			result.add(l.get(i));
		}
		
		return result;
	}
	
	/**
	 * applied to a predicate p and a list xs, returns the suffix remaining after takeWhile p xs:
	 *  > dropWhile (x -> x < 3) [1,2,3,4,5,1,2,3] == [3,4,5,1,2,3]
	 *  > dropWhile (x -> x < 9) [1,2,3] == []
	 *  > dropWhile (x -> x < 0) [1,2,3] == [1,2,3]
	 * @param p
	 * @param l
	 * @return
	 */
	public static <A> List<A> dropWhile(Predicate<A> p, List<A> l) {
		List<A> result = new ArrayList<A>();
		
		boolean flag = true;
		for (int i = 0; i < l.size(); i++) {
			flag = flag && p.test(l.get(i));
			if (!flag) {
				result.add(l.get(i));
			}
		}
		
		return result;
	}
	
	/**
	 * returns a list obtained by applying f to each element of xs, i.e.,
	 *  > map f [x1, x2, ..., xn] == [f(x1), f(x2), ..., f(xn)]
	 *  > map f [x1, x2, ...] == [f(x1), f(x2), ...]
	 * @param m
	 * @param l
	 * @return
	 */
	public static <A, B> List<B> map(Function<A, B> m, List<A> l) {
		return fold(l, new ArrayList<B>(), (a,b) -> {b.add(m.apply(a)); return b;});
	}
	
	/**
	 * returns a list by applying f to each element of xs and flattening the resulting list
	 * @param m
	 * @param l
	 * @return
	 */
	public static <A, B> List<B> flatMap(Function<A, List<B>> m, List<A> l) {
		return fold(l, new ArrayList<B>(), (a,b) -> {b.addAll(m.apply(a)); return b;});
	}
	
	/**
	 * applied to a predicate and a list, returns the list of those elements that satisfy the predicate; i.e.,
	 *  > filter (x -> x % 2 == 0) [1,2,3,4,5,6,7,8,9,10] == [2,4,6,8,10]
	 * @param p
	 * @param l
	 * @return
	 */
	public static <A> List<A> filter(Predicate<A> p, List<A> l) {
		List<A> acc = new ArrayList<A>();
		return fold(l, acc, ((a,b) -> when (p.test(a), b, (x -> x.add(a)))));
	}
	
	/**
	 * Left-associative fold of a list. fold, when applied to a binary operator, a starting value (typically the left-identity of the operator), and a list, reduces the list using the binary operator, from left to right, i.e.
	 *  > fold [x1, x2, ..., xn] acc f == f (...f (f (f (acc, x1)), x2), ...), xn
	 * @param l
	 * @param b
	 * @param mapper
	 * @return
	 */
	public static <A, B> B fold(List<A> l, B b, BiFunction<A, B, B> mapper) {
		if (l != null) {
			for (A a : l) {
				b = mapper.apply(a, b);
			}
		}
		return b;
	}
	
	private static <A, B> B fold(List<A> l, B b, BiFunction<A, B, B> mapper, int idx) {
		return (l == null || l.isEmpty() || idx >= l.size()) ? b : fold(l, mapper.apply(l.get(idx), b), mapper, idx + 1);
	}
	
	/**
	 * returns a list with unique elements from the input list
	 * @param l
	 * @return
	 */
	public static <A> List<A> unique(List<A> l) {
		List<A> accumulator = new ArrayList<A>();
		return fold(l, accumulator, ((a,b) -> when (!b.contains(a), b, (x -> x.add(a)))));
	}
	
	private static <A> List<A> when(boolean b, List<A> l, Function<List<A>, ?> action) {
		if (b) {
			action.apply(l);
		}
		return l;
	}
	
	/**
	 * The findFirst function takes a predicate and a list and returns the first element in the list matching the predicate (short-circuit'ing the flow), or empty if there is no such element.
	 * @param p
	 * @param l
	 * @return
	 */
	public static <A> Optional<A> findFirst(Function<A, Boolean> p, Collection<A> l) {
		for (A a : l) {
			if(p.apply(a)){
				return Optional.ofNullable(a);
			}
		}
		return Optional.empty();
	}

	/**
	 * Determines whether all elements of the list satisfy the predicate. (short-circuits the flow when first non-match found)
	 * @param p
	 * @param l
	 * @return
	 */
	public static <A> boolean all(Function<A, Boolean> p, List<A> l) {
		for (A a : l) {
			if(!p.apply(a)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Determines whether any element of the structure satisfies the predicate. (short-circuits the flow when first match found)
	 * @param p
	 * @param l
	 * @return
	 */
	public static <A> boolean any(Function<A, Boolean> p, Collection<A> l) {
		for (A a : l) {
			if(p.apply(a)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines whether no elements of the structure satisfy the predicate. (short-circuits the flow when first non-match found)
	 * @param p
	 * @param l
	 * @return
	 */
	public static <A> Boolean none(Function<A, Boolean> p, Collection<A> l) {
		for (A a : l) {
			if(p.apply(a)){
				return false;
			}
		}
		return true;
	}
	
}
