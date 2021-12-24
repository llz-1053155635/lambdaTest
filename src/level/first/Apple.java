package level.first;

/**
 * @Author: llz
 * @Date: 2021/12/23 16:11
 * @description:
 */
public class Apple {
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
