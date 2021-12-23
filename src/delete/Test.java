package delete;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * @Author: llz
 * @Date: 2021/12/17 17:22
 * @description:
 */
public class Test {
	public static void main(String[] args) {
//		List<Integer> weights = Arrays.asList(7, 3, 4, 10);
//		List<delete.Apple> apples = map(weights, delete.Apple::new);
//		for (delete.Apple s : apples) {
//			System.out.println(s.getWeight() + "" + s);
		int sum = Integer.sum(1, 2);
		List<Dish> menu = Arrays.asList(
				new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT),
				new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER),
				new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("season fruit", true, 120, Dish.Type.OTHER),
				new Dish("pizza", true, 550, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH),
				new Dish("salmon", false, 450, Dish.Type.FISH) );


		Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(
				groupingBy(dish -> {
					if (dish.getCalories() <= 400) return CaloricLevel.DIET;
					else if (dish.getCalories() <= 700) return
							CaloricLevel.NORMAL;
					else return CaloricLevel.FAT;
				} ));
		Iterator<Map.Entry<CaloricLevel, List<Dish>>> iterator = dishesByCaloricLevel.entrySet().iterator();
//		while (iterator.hasNext()){
//			Map.Entry<delete.CaloricLevel, List<delete.Dish>> entry = iterator.next();
//
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//
//		}
		Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream().collect(
				groupingBy(Dish::getType,
						groupingBy(dish -> {
							if (dish.getCalories() <= 400) return CaloricLevel.DIET;
							else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
							else return CaloricLevel.FAT;
						} )
				)
		);

		Iterator<Map.Entry<Dish.Type, Map<CaloricLevel, List<Dish>>>> iterator1 = dishesByTypeCaloricLevel.entrySet().iterator();

		while (iterator1.hasNext()){
			Map.Entry<Dish.Type, Map<CaloricLevel, List<Dish>>> entry = iterator1.next();

			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			Iterator<Map.Entry<CaloricLevel, List<Dish>>> iterator2 = entry.getValue().entrySet().iterator();
			while (iterator2.hasNext()) {
				Map.Entry<CaloricLevel, List<Dish>> next = iterator2.next();
//				System.out.println("Key = " + next.getKey() + ", Value = " + next.getValue());
			}
		}
		//		Optional<delete.Dish> any = menu.stream().filter(delete.Dish::isVegetarian).findAny();
//		System.out.println(any);
//			menu.forEach(System.out::println);
//			menu.forEach(System.out::println);
////		List<String> lowCaloricDishesName =
////				menu.parallelStream()
////						.filter(d -> d.getCalories() < 400)
////						.sorted(comparing(Dishes::getCalories))
////						.map(delete.Dish::getName)
////						.collect(toList());
////		}
//		List<String> threeHighCaloricDishNames =
//				menu.stream()
// .filter(d -> d.getCalories() > 300)
//				.map(delete.Dish::getName)
//				.limit(3)
//				.collect(toList());
//		System.out.println(threeHighCaloricDishNames);
//
//		delete.Function<Integer, Integer> f = x -> x + 1;
//		String s = "hello ,world;";
//		String[] array = {"hello","world"};
//		List<String> collect = Arrays.asList(array).stream()
//				.map(world -> world.split(""))
//				.flatMap(Arrays::stream)
//				.distinct()
//				.collect(toList());
//		collect.forEach(System.out::println);
	}



	public static List<Apple> map(List<Integer> list,
								  Function<Integer, Apple> f){
		List<Apple> result = new ArrayList<>();
		for(Integer e: list){
			System.out.println(f.apply(e)+"===========>");
			result.add(f.apply(e));
		}
		return result;
	}

}

class Apple{
	private Integer weight ;

	public Apple(Integer integer) {
		this.weight = integer;
	}

	public Apple() {
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}

enum CaloricLevel { DIET, NORMAL, FAT }