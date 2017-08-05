package model;

public class DifficultyLevel {
	public DifficultyLevel(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return name;
	}
	private int id;
	private String name;
}
