package dices.service;


import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import dices.dao.DiceThrowDao;
import dices.dao.PlayerDao;
import dices.entity.Dice;
import dices.entity.Player;
import dices.entity.DiceThrow;

/*Nivell 1
El joc de daus s’hi juga  amb dos daus. 
En cas que el resultat   de la suma dels dos daus  sigui 7, 
la partida és  guanyada, sinó  és perduda. 
Un jugador pot  veure un llistat de totes les  tirades que ha fet 
i el  percentatge d’èxit.   

Per poder jugar al joc i realitzar  una tirada, un usuari  
s’ha de registrar amb  un nom no  repetit. Al crear-se, se l’hi  
assigna un identificador  numèric únic  i una data de registre. 
Si l’usuari  així  ho desitja , pots  no afegir cap nom i es  dirà “ANÒNIM”. 
Pot  haver-hi  més d’un jugador “ANÒNIM”.  

Cada jugador pot veure un llistat de totes les  tirades que ha fet, 
amb el valor de cada dau i si s’ha  guanyat  o no la partida. 
A més,  pot  saber el seu percentatge  d’èxit per totes les  tirades  que ha realitzat.    

No es pot eliminar una partida en  concret, 
però si que es pot  eliminar  tot el llistat de  tirades per un jugador.  

El software ha de permetre llistar tots  els jugadors que hi ha al sistema, 
el  percentatge d’èxit de cada jugador i el  percentatge d’èxit  mig de tots  
els jugadors en el sistema.   

El software ha de respectar els principals patrons de  disseny.  

NOTES 

Has de tindre en compte els  següents detalls de  construcció: 
 * POST: /players : crea un jugador 
PUT /players : modifica el nom del jugador 
POST /players/{id}/games/ : un jugador específic realitza una tirada  dels daus.  
DELETE /players/{id}/games: elimina les tirades del jugador 
GET /players/: retorna el llistat de tots  els jugadors del sistema  amb el seu  percentatge mig  d’èxits   
GET /players/{id}/games: retorna el llistat de jugades per un jugador.  
GET /players/ranking: retorna el ranking  mig de tots els   jugadors del sistema .  És a dir, el  percentatge mig  d’èxits. 
GET /players/ranking/loser: retorna el jugador  amb pitjor percentatge d’èxit  
GET /players/ranking/winner: retorna el  jugador amb  pitjor percentatge   d’èxit 
- Fase 1

    Persistència: utilitza com a base de  dades mysql 

- Fase 2

    Canvia la configuració i  utilitza MongoDB per persistir les dades 

- Fase 3

    Afegix seguretat : inclou autenticació per JWT en  tots els accessos a les URL de  l'microservei. 

Nivell 2



    Afegeix tests unitaris, de component i d'integració al projecte amb librerias jUnit, AssertJ o Hamcrest.
    Afegeix Mocks al testing del projecte (Mockito) i Contract Tests (https://docs.pact.io/)



Nivell 3

    Dissenya i modifica el projecte diversificant la persistència perquè utilitzi dos esquemes de base de dades alhora, MySQL i Mongo.
*/

@Service
public class DiceService {
	
	@Autowired
	private PlayerDao playerDAO;
	
	@Autowired
	private DiceThrowDao diceThrowDAO;
	
	
	
	//POST: /players : crea un jugador 
	
	public ResponseEntity<HttpStatus> createPlayer(Player player){
		
		if(playerDAO.findAllByName(player.getName()).isEmpty() || player.getName()==null) {
			
				if (player.getName()==null) {
				player.setName("Anonymous");
				}
		
			player.setDate_at(Calendar.getInstance());
			playerDAO.save(player);
			
			return new ResponseEntity<>(HttpStatus.CREATED);
			
		}else {

			return new ResponseEntity<>(HttpStatus.IM_USED);
		}
	}
	
	//PUT /players : modifica el nom del jugador 

	public ResponseEntity<String> updateName(Integer playerId,String playerName){
		
		Optional<Player> optionalPlayer=playerDAO.findById(playerId);
		
			if(optionalPlayer.isPresent() & playerDAO.findByName(playerName).isEmpty()) {
			
				Player newPlayer=optionalPlayer.get();
				newPlayer.setName(playerName);
			
				playerDAO.save(newPlayer); 
			
				return ResponseEntity.ok("New name: " + playerName+" Incorporated in player ID: "+playerId );
			}
		return ResponseEntity.ok("Player not Found or new name is used");
	}

	//POST /players/{id}/games/ : un jugador específic realitza una tirada  dels daus.
	
	public ResponseEntity<String> createDiceThrow(Integer playerId){
		
		Optional<Player> optionalPlayer=playerDAO.findById(playerId);
		
		if(optionalPlayer.isPresent()) {
			
				Player player1=optionalPlayer.get();
			
				DiceThrow diceThrow=new DiceThrow();
		
				Dice dice1=new Dice(1,"dice1",(int)(Math.random()*(7-1))+1);
				Dice dice2=new Dice(2,"dice2",(int)(Math.random()*(7-1))+1);
		
			
				diceThrow.setDice1(dice1.getValueDice());
				diceThrow.setDice2(dice2.getValueDice());
				diceThrow.setPlayerid(playerId);
				diceThrow.setDate_throw(Calendar.getInstance());
				diceThrow.setThrowValue(dice1.getValueDice()+dice2.getValueDice());
				player1.getThrowList().add(diceThrow);
				diceThrow.setThorwNumber(player1.getThrowList().size());
		
				diceThrowDAO.save(diceThrow);
			
			
				double win=0;
				double attempts=player1.getThrowList().size();

					for (DiceThrow e :player1.getThrowList()) {
							if (e.getThrowValue()==7) {
								win++;
							}
						}

				double porcentage= (win/attempts)*100;
				player1.setPercent(porcentage);
				playerDAO.save(player1);
			
			
			if (diceThrow.getThrowValue()==7) {
					return ResponseEntity.ok("YOU WINN!!!! \nResult of the throw number "+diceThrow.getThorwNumber()+": "+
										 diceThrow.getThrowValue()+"\nDice 1 result: "+dice1.getValueDice()+
										 " \nDice 2 result: "+dice2.getValueDice()+"\nPercent of victories: "+ player1.getPercent());
			}else {
					return ResponseEntity.ok("YOU LOUS!!!! \nResult of the throw number "+diceThrow.getThorwNumber()+": "+
										diceThrow.getThrowValue()+"\nDice 1 result: "+dice1.getValueDice()+
										" \nDice 2 result: "+dice2.getValueDice()+"\nPercent of victories: "+ player1.getPercent());
			}
		}else {
			
			return ResponseEntity.ok(" Player ID: "+ playerId+" not Found");
		}
	}
	//DELETE /players/{id}/games: elimina les tirades del jugador 

	public ResponseEntity<String> deleteThrows(int playerId){
		
		Optional<Player> optionalPlayer=playerDAO.findById(playerId);
		
		if(optionalPlayer.isPresent()) {
			
			Player player=optionalPlayer.get();
			
			diceThrowDAO.deleteAllByPlayerid(playerId);
			player.setPercent(0);
			playerDAO.save(player);
			
			return ResponseEntity.ok("Delete All Throws of Player: "+ player.getName() );
		
		}else {
			return ResponseEntity.ok(" Player ID: "+ playerId+" not Found");
		}
	}
	
	//GET /players/: retorna el llistat de tots  els jugadors del sistema  amb el seu  percentatge mig  d’èxits
	public ResponseEntity<List<String>> playersList(){

		List<String>lista=playerDAO.findAll().stream()
				.flatMap(s-> Stream.of("Player: "+s.getName()+" Victories: "+s.getPercent()+"%"))
				.collect(Collectors.toList());
		return ResponseEntity.ok(lista);
	}
	
	//GET /players/{id}/games: retorna el llistat de jugades per un jugador.
	
	public ResponseEntity<List<DiceThrow>> throwsList(int playerId){

			try {
				Player player= playerDAO.findById(playerId).get();
				
			return ResponseEntity.ok(player.getThrowList());
			
			}catch (NoSuchElementException e) {
				
				return ResponseEntity.noContent().build();
			}
	}
	
	//GET /players/ranking: retorna el ranking  mig de tots els   jugadors del sistema .  És a dir, el  percentatge mig  d’èxits.
	
	public ResponseEntity<String> ranking() {
		
		double win=0;
		double playerWin=0;
		for (Player e : playerDAO.findAll()) {
			
			playerWin=	(win+e.getPercent());
		}
	
		double average=playerWin/playerDAO.findAll().size();
		
			List<String>lista=playerDAO.findAll().stream()
				.flatMap(s-> Stream.of("Player: "+s.getName()+" Winning percentage:"+s.getPercent()+"\n")) 
				.collect(Collectors.toList());
			return ResponseEntity.ok(lista.toString()+"\nAverage success:"+average);
	}
	//GET /players/ranking/loser: retorna el jugador  amb pitjor percentatge d’èxit
	
	public ResponseEntity<String> loser(){
			
		Player sortedList = playerDAO.findAll().stream()
							.min(Comparator.comparingDouble(s-> s.getPercent())).get();
		return ResponseEntity.ok("Worst percentage: "+sortedList.getPercent()+"\nPlayer: "+sortedList.getName());
	}
	//GET /players/ranking/winner: retorna el  jugador amb  pitjor percentatge   d’èxit 
	public ResponseEntity<String> winner(){
		
		Player winner = playerDAO.findAll().stream()
				.max(Comparator.comparingDouble(s-> s.getPercent())).get();
		return ResponseEntity.ok("Best percentage: "+winner.getPercent()+"\nPlayer: "+winner.getName());
		
	}
	public ResponseEntity<Player> playerDeatail(int playerId){
		
		Optional<Player> optionalPlayer=playerDAO.findById(playerId);
		Player player=optionalPlayer.get();
		return ResponseEntity.ok(player);
	}
}
