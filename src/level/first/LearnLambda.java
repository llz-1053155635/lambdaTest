package level.first;

import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @Author: llz
 * @Date: 2021/12/23 13:58
 * @description:
 */
public class LearnLambda {
	static List<Apple> apples = Arrays.asList(new Apple(20, "green"),
			new Apple(21, "red"),
			new Apple(32, "black"),
			new Apple(44,"red"),
			new Apple(10, "black")
	);

	public static void main(String[] args) {
		// 方式一
		Collections.sort(apples, new Comparator<Apple>() {
			@Override
			public int compare(Apple o1, Apple o2) {
				return o1.getWeight().compareTo(o2.getWeight());
			}
		});
		apples.forEach(System.out::println);
		// 方式二:
		System.out.println("==============");
		apples.sort(Comparator.comparing(Apple::getWeight));
		apples.forEach(System.out::println);


		// listFiles 查询所有文件目录
		File[] files1 = new File(".").listFiles();
		List<File> collect2 = Arrays.stream(files1).collect(Collectors.toList());
		collect2.forEach(System.out::println);

		//寻找隐藏文件
		File[] hiddenFiles = new File(".").listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isHidden();
			}
		});
		List<File> collect = Arrays.stream(hiddenFiles).collect(Collectors.toList());
		collect.forEach(System.out::println);

		// 方法引用
		File[] files = new File(".").listFiles(File::isHidden);
		List<File> collect1 = Arrays.stream(files).collect(Collectors.toList());
		collect1.forEach(System.out::println);
	}

	@Test
	public void filterApple(){
		// 传统java 写法筛选绿色的苹果,如果有要筛选大于150克的苹果可谓相当多于代码
		List<Apple> list = new ArrayList<>();
		for (Apple apple : apples) {
			if (Objects.equals("green",apple.getColor())) {
				list.add(apple);
			}
		}
		list.forEach(System.out::println);
	}
	// 有考虑将代码作为参数传进去，就了以避免重复代码
	public static boolean isGreen(Apple apple){
		return Objects.equals("green",apple.getColor());
	}
	public static boolean isHeavyApple(Apple apple) {
		return apple.getWeight() > 150 ;
	}

	public List<Apple> filterApples(List<Apple> list ,
									Predicate<Apple> predicate){
		List<Apple> result = new ArrayList<>();
		for (Apple apple : list) {
			if (predicate.test(apple)) {
				result.add(apple);
			}
		}
		return result;

	}

	@Test
	public void filterApple2(){
		// 因此可以简写为
		filterApples(apples,LearnLambda::isGreen);
	}


	@Test
	public void filterApple3(){
		// 使用匿名函数(lambda表达式) 来简写上述方法
		filterApples(apples,(Apple a) -> "green".equals(a.getColor()));
		// 或者
		filterApples(apples,a -> a.getWeight() > 150
				&& Objects.equals("green",a.getColor()));
		// java 加入filter 和几个相关的方法使得通用库更加让人满足,使用自己封装的工具类
		Collection<Apple> apples = filter(LearnLambda.apples, apple -> Objects.equals(apple.getColor(), "green"));
		apples.forEach(System.out::println);


	}

	// 因此可以构建自己工具类
	public static <T> Collection<T> filter(Collection<T> c, Predicate<T> p){
		Collection<T> result = new ArrayList<>();
		for (T t : c) {
			if (p.test(t)) {
				result.add(t);
			}
		}
		return result;
	}

	// 流

	/**
	 * Map<Currency, List<Transaction>> transactionsByCurrencies = new HashMap<>(); ←─建立累积交易分组的Map
	 * for (Transaction transaction : transactions) { ←─遍历交易的List
	 *  if(transaction.getPrice() > 1000){ ←─筛选金额较高的交易
	 *  Currency currency = transaction.getCurrency(); ←─提取交易货币
	 *  List<Transaction> transactionsForCurrency =
	 *  transactionsByCurrencies.get(currency);
	 *  if (transactionsForCurrency == null) { ←─如果这个货币的分组Map是空的，那就建立一个
	 *  transactionsForCurrency = new ArrayList<>();
	 *  transactionsByCurrencies.put(currency,
	 *  transactionsForCurrency);
	 *  }
	 *  transactionsForCurrency.add(transaction); ←─将当前遍历的交易添加到具有同一货币的交易List中
	 *  }
	 * }
	 */
	@Test
	public void streamTest(){
		// 简单的流使用 , 数据全都在库内部迭代
		Map<String, List<Apple>> map = apples.stream().filter(apple -> apple.getWeight() > 12)
				.collect(groupingBy(Apple::getColor));
		System.out.println(map);
		/**
		 * console 结果
		 * {
		 * 	red=[Apple{weight=21, color='red'},
		 * 		Apple{weight=44, color='red'}],
		 * 	green=[Apple{weight=20, color='green'}],
		 * 	black=[Apple{weight=32, color='black'}]}
		 */
		/**
		 * 如果处理数据过于庞杂,现有java版 的Thread api 并非易事,并且线程可
		 * 能同时访问并更新共享变量,因此可能造成数据出现异常情况
		 * 利用synchronized 关键字方法不同,他关注的是数据的分块而不是协调访问
 		 */
		// 使用ｓｔｒｅａｍ流来筛选
		List<Apple> list = apples.stream()
				.filter(apple -> apple.getWeight() > 150)
				.collect(Collectors.toList());

		// 使其并行操作,交由两个CPU上并聚合操作结果
		apples.parallelStream()
				.filter(apple -> apple.getWeight() > 150)
				.collect(Collectors.toList()) ;

		// java 中的并行与无共享可变状态
		/**
		 * 大家都说Java里面并行很难，而且和synchronized相关的玩意儿都容易出问题。那
		 * Java 8里面有什么“灵丹妙药”呢？事实上有两个。首先，库会负责分块，即把大的流分
		 * 成几个小的流，以便并行处理。其次，流提供的这个几乎免费的并行，只有在传递给
		 * filter之类的库方法的方法不会互动（比方说有可变的共享对象）时才能工作。但是
		 * 其实这个限制对于程序员来说挺自然的，举个例子，我们的Apple::isGreenApple
		 * 就是这样。确实，虽然函数式编程中的函数的主要意思是“把函数作为一等值”，不过
		 * 它也常常隐含着第二层意思，即“执行时在元素之间无互动”。
		 */
	}




}

////其实现成 Predicate 函数式接口
//interface Predicate<T>{
//	boolean test(T t);
//}

