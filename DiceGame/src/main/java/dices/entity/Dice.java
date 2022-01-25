package dices.entity;



public class Dice {
	
	private int id;
	private String nameDice;
	private int valueDice;
	
	public Dice() {}

	public Dice(int id, String nameDice, int valueDice) {
		super();
		this.id = id;
		this.nameDice = nameDice;
		this.valueDice = valueDice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameDice() {
		return nameDice;
	}

	public void setNameDice(String nameDice) {
		this.nameDice = nameDice;
	}

	public int getValueDice() {
		return valueDice;
	}

	public void setValueDice(int valueDice) {
		this.valueDice = valueDice;
	}

	@Override
	public String toString() {
		return "Dice [id=" + id + ", nameDice=" + nameDice + ", valueDice=" + valueDice + "]";
	}
	
	


}
