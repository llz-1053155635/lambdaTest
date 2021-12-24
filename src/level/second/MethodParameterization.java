package level.second;

import com.sun.org.apache.bcel.internal.classfile.InnerClass;
import level.first.Apple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @Author: llz
 * @Date: 2021/12/23 16:08
 * @description: 本章介绍方法参数化
 */
public class MethodParameterization {
	static List<Apple> apples = Arrays.asList(new Apple(20, "green"),
			new Apple(21, "red"),
			new Apple(32, "black"),
			new Apple(44,"red"),
			new Apple(10, "black"),
			new Apple(10, "green")
	);

	// 最初解决方法
	public static List<Apple> filterGreenApples(List<Apple> list){
		List<Apple> result = new ArrayList<>();
		for (Apple apple : list) {
			if (Objects.equals(apple.getColor(),"green")) {
				result.add(apple);
			}
		}
		return result;
	}

	// 优化：将颜色作为参数,更加灵地适应的变化
	public static List<Apple> filterApplesByColor(List<Apple> list,
												  String color){
		List<Apple> result = new ArrayList<>();
		for (Apple apple : list) {
			if (Objects.equals(apple.getColor(),color)) {
				result.add(apple);
			}
		}
		return result;
	}

	// 但如果此时,要换成大于150克的苹果，此时就违反DRY原则(don't repeat yourself)

	// 尝试 ： 将所有参数放入 ,但同时过于笨拙
	public static List<Apple> filterApples(List<Apple> list,
										   String color,
										   Integer weight){
		List<Apple> result = new ArrayList<>();
		for (Apple apple : list) {
			if (Objects.equals(apple.getColor(),color) && apple.getWeight() > weight) {
				result.add(apple);
			}
		}
		return result;
	}

	// 将筛选条件抽象化
	public static List<Apple> filterApples(List<Apple> list,
										   ApplePredicate p){
		List<Apple>  result = new ArrayList<>();
		for (Apple apple : list) {
			if (p.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}

	public static void prettyPrintApple(List<Apple> list,
										AppleFormatter appleFormatter){
		for (Apple apple : list) {
			String accept = appleFormatter.accept(apple);
			System.out.println(accept);
		}

	}
	@Test
	public void filterApple(){
	// 将筛选条件抽象化,将方法行为参数化
		List<Apple> list = filterApples(apples, new AppleGreenPredicate());
		list.forEach(System.out::println);
	}

	@Test
	public void printApple(){
		prettyPrintApple(apples,new AppleFancyFormatter());
	}

	// 通过上述案例 过程过于啰嗦
	/**
	 * 使用匿名内部类解决，使用过程的啰嗦,
	 * 但同时存在缺陷 ：
	 * 1. 过于笨重,往往占用很多空间
	 * 2. 令人费解
	 */
	@Test
	public void AnonymousInnerClassTest(){
		List<Apple> list = filterApples(apples, new ApplePredicate() {
			@Override
			public boolean test(Apple apple) {
				return Objects.equals("red", apple.getColor());
			}
		});
		list.forEach(System.out::println);
	}

	/**
	 * 使用lambda 表达式解决
	 */
	@Test
	public void lambdaTest(){
		List<Apple> list = filterApples(apples, apple -> "red".equals(apple.getColor()));
		list.forEach(System.out::println);
	}

	/**
	 * 将List类型进行抽象化,增强其通用性
	 */
	public static <T>List<T> filter(List<T> list , Predicate<T> p){
		List<T> result = new ArrayList<>();
		for (T t : list) {
			if (p.test(t)) {
				result.add(t);
			}
		}
		return result;
	}
}





// 因此需要一个标准化建造模板
interface  ApplePredicate{
	boolean test(Apple apple);
}

// 仅仅筛选 绿色苹果
class AppleGreenPredicate implements  ApplePredicate{

	@Override
	public boolean test(Apple apple) {
		return Objects.equals("green",apple.getColor());
	}
}
// 筛选大于150 的苹果
class AppleHeavyWeightPredicate implements ApplePredicate{

	@Override
	public boolean test(Apple apple) {
		return apple.getWeight() > 150;
	}
}

interface AppleFormatter{
	String accept(Apple a);
}

class AppleFancyFormatter implements AppleFormatter{
	public String accept(Apple apple){
		String characteristic = apple.getWeight() > 150 ? "heavy" :
				"light";
		return "A " + characteristic +
				" " + apple.getColor() +" apple";
	}
}