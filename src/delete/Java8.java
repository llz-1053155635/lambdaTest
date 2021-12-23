package delete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: llz
 * @Date: 2021/12/15 15:18
 * @description:
 */
public class Java8 {
	private Integer id;

	public Java8(Integer integer) {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getL() {
		return l;
	}

	public void setL(List<Integer> l) {
		this.l = l;
	}

	public static void main(String[] args) {
		List<Integer> l = map(
				Arrays.asList("lambdas","in","action"),
				(String s) -> s.length()
		);

//		Supplier<delete.Java8> c1 = delete.Java8::new;
//		delete.Java8 java8 = c1.get();
//		delete.Function<Integer,delete.Java8> c2  = delete.Java8::new;

	}

	public static <T, R> List<R> map(List<T> list,
									 Function<T, R> f) {
		List<R> result = new ArrayList<>();
		for(T s: list){
			result.add(f.apply(s));
		}
		return result;
	}
	// [7, 2, 6]
	List<Integer> l = map(
			Arrays.asList("lambdas","in","action"),
			(String s) -> s.length()
	);
}
@FunctionalInterface
interface Function<T, R>{
	R apply(T t);
}