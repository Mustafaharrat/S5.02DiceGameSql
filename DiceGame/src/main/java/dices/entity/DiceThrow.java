package dices.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DiceThrow")
public class DiceThrow {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="throwNumber")
	private int throwNumber;
	
	@Column(name="dice1")
	private int dice1;
	
	@Column(name="dice2")
	private int dice2;
	
	@Column(name="throwValue")
	private int throwValue;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Date_throw")
	private Calendar date_throw;
	
	
	
	@Column(name="playerid")
	private int playerid;
	
	public DiceThrow() {}

	public DiceThrow(int thorwNumber, Calendar date_throw, int playerid) {
		super();
		this.throwNumber = thorwNumber;
		
		this.date_throw = date_throw;
		this.playerid = playerid;
	}

	public DiceThrow(int dice1, int dice2, int playerid, Calendar date_throw , int throwValue) {
		// TODO Auto-generated constructor stub
		
		this.dice1=dice1;
		this.dice2=dice2;
		this.playerid=playerid;
		this.date_throw=date_throw;
		this.throwValue=throwValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getThorwNumber() {
		return throwNumber;
	}

	public void setThorwNumber(int thorwNumber) {
		this.throwNumber = thorwNumber;
	}
	
	

	

	

	public int getThrowValue() {
		return throwValue;
	}

	public void setThrowValue(int throwValue) {
		this.throwValue = throwValue;
	}

	public int getDice1() {
		return dice1;
	}

	public void setDice1(int dice1) {
		this.dice1 = dice1;
	}

	public int getDice2() {
		return dice2;
	}

	public void setDice2(int dice2) {
		this.dice2 = dice2;
	}

	public Calendar getDate_throw() {
		return date_throw;
	}

	public void setDate_throw(Calendar date_throw) {
		this.date_throw = date_throw;
	}

	public int getPlayerid() {
		return playerid;
	}

	public void setPlayerid(int playerid) {
		this.playerid = playerid;
	}
	
	


	@Override
	public String toString() {
		return "DiceThrow [id=" + id + ", thorwNumber=" + throwNumber + ", dice1=" + dice1 + ", dice2=" + dice2
				+ ", throwValue=" + throwValue + ", date_throw=" + ", playerid=" + playerid + "]";
	}

	
	
	

}
