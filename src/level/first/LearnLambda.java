package level.first;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: llz
 * @Date: 2021/12/23 13:58
 * @description:
 */
public class LearnLambda {
	public static void main(String[] args) {
		List<Apple> apples = Arrays.asList(new Apple(20, "green"),
				new Apple(21, "red"),
				new Apple(32, "black"));
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
	}
}


class Apple{
	public Apple(Integer weight, String color) {
		this.weight = weight;
		this.color = color;
	}

	private Integer weight;
	private String color;

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Apple{" +
				"weight=" + weight +
				", color='" + color + '\'' +
				'}';
	}
}