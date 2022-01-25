package dices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dices.controllers.DiceController;
import dices.dao.DiceThrowDao;
import dices.dao.PlayerDao;
import dices.entity.DiceThrow;
import dices.entity.Player;
import dices.service.DiceService;
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes= {MockitoTest.class})
public class MockitoTest {
	
	@Mock
	PlayerDao playerDAO;
	
	@Mock
	DiceThrowDao diceThrowDAO;
	
	@InjectMocks
	DiceService diceService;
	
	List<Player>lista;
	
	List<DiceThrow>throwList;
	
	Player player;
	
	DiceThrow diceThrow;
	
	
	@Test
	@Order(1)
	public void testCreated() {
		player=new Player("Jhon",Calendar.getInstance());
		when(playerDAO.save(player)).thenReturn(player);
		ResponseEntity<HttpStatus>res=diceService.createPlayer(player);
		assertEquals(HttpStatus.CREATED,res.getStatusCode());
	}
	
	@Test
	@Order(2)
	public void testUpdateName() {
		player=new Player(1,"Jhon",Calendar.getInstance());
		when(playerDAO.save(player)).thenReturn(player);
		ResponseEntity<String> res=diceService.updateName(1, "Luis");
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	
	@Test
	@Order(3)
	public void testDiceThrow() {
		player=new Player(1,"Jhon",Calendar.getInstance());
		when(playerDAO.save(player)).thenReturn(player);
		when(diceThrowDAO.save(diceThrow)).thenReturn(diceThrow);
		ResponseEntity<String> reso=diceService.createDiceThrow(1);
		assertEquals(HttpStatus.OK,reso.getStatusCode());
	}
	
	@Test
	@Order(4)
	public void testDeleteThrows() {
		player=new Player(1,"Jhon",Calendar.getInstance());
		when(playerDAO.save(player)).thenReturn(player);
		//when(diceThrowDAO.deleteAllByPlayerid(1)).thenReturn(throwList);
		ResponseEntity<String> res=diceService.deleteThrows(1);
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	
	@Test
	@Order(5)
	public void testPlayerList(){
		lista=new ArrayList<Player>();
		lista.add(new Player("Jhon",Calendar.getInstance()));
		lista.add(new Player("Anna",Calendar.getInstance()));
		lista.add(new Player("Xin",Calendar.getInstance()));
		
		when(playerDAO.findAll()).thenReturn(lista);
		ResponseEntity<List<String>> res=diceService.playersList();
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(3,res.getBody().size());
	}
	@Test
	@Order(6)
	public void TestThrowList() {
		player=new Player(1,"Jhon",Calendar.getInstance());
	    when(playerDAO.findById(Mockito.eq(12))).thenReturn(Optional.of(player));
		ResponseEntity<List<DiceThrow>> res=diceService.throwsList(12);
		assertEquals(HttpStatus.OK,res.getStatusCode());

	}
	@Test
	@Order(7)
	public void testRanking() {
		lista=new ArrayList<Player>();
		lista.add(new Player("Jhon",Calendar.getInstance()));
		lista.add(new Player("Anna",Calendar.getInstance()));
		lista.add(new Player("Xin",Calendar.getInstance()));
		
		when(playerDAO.findAll()).thenReturn(lista);
		ResponseEntity<String> res=diceService.ranking();
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
	@Order(8)
	public void testLoser() {
		lista=new ArrayList<Player>();
		lista.add(new Player("Jhon",Calendar.getInstance()));
		lista.add(new Player("Anna",Calendar.getInstance()));
		lista.add(new Player("Xin",Calendar.getInstance()));
		
		when(playerDAO.findAll()).thenReturn(lista);
		ResponseEntity<String> res=diceService.loser();
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
	@Order(9)
	public void testWinner() {
		lista=new ArrayList<Player>();
		lista.add(new Player("Jhon",Calendar.getInstance()));
		lista.add(new Player("Anna",Calendar.getInstance()));
		lista.add(new Player("Xin",Calendar.getInstance()));
		
		when(playerDAO.findAll()).thenReturn(lista);
		ResponseEntity<String> res=diceService.winner();
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
}
