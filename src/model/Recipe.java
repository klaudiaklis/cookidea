package model;

public class Recipe {
	private int id;
	private String name;
	private String description;
	private int mealTypeId;
	private int cousineTypeId;
	private int portions;
	private int difficultyLevelId;
	private int duration;
	
	public Recipe(int id, String name, String description, int duration, int mealTypeId, int cousineTypeId, int portions,
			int difficultyLevelId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.mealTypeId = mealTypeId;
		this.cousineTypeId = cousineTypeId;
		this.portions = portions;
		this.difficultyLevelId = difficultyLevelId;
	}
	public int getDuration() {
		return duration;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getMealTypeId() {
		return mealTypeId;
	}
	public int getCousineTypeId() {
		return cousineTypeId;
	}
	public int getPortions() {
		return portions;
	}
	public int getDifficultyLevelId() {
		return difficultyLevelId;
	}
	
	
}
