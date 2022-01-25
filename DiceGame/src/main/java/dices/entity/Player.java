package dices.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Players")
public class Player {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Percent")
	private double percent;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Date_at")
	private Calendar date_at;
	
	@OneToMany(mappedBy="playerid",cascade= CascadeType.ALL) 
	@Column(name="ThrowList")
	private List<DiceThrow> throwList;
	
	public Player() {}

	public Player(String name, Calendar date_at) {
		super();
	
		this.name = name;
		this.date_at = date_at;
	}

	public Player(int id, String name, Calendar date_at, List<DiceThrow> throwList) {
		super();
		this.id = id;
		this.name = name;
		this.date_at = date_at;
		this.throwList = throwList;
	}
	
	public Player(int id, String string, Calendar instance) {
		
		
		this.id = id;
		this.name = name;
		this.date_at = date_at;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public Calendar getDate_at() {
		return date_at;
	}

	public void setDate_at(Calendar date_at) {
		this.date_at = date_at;
	}

	public List<DiceThrow> getThrowList() {
		return throwList;
	}

	public void setThrowList(List<DiceThrow> throwList) {
		this.throwList = throwList;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", date_at=" +  ", throwList=" + throwList + "]";
	}
	
	
}
