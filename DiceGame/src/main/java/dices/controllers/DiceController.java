package dices.controllers;


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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dices.dao.DiceThrowDao;
import dices.dao.PlayerDao;
import dices.entity.Dice;
import dices.entity.Player;
import dices.service.DiceService;
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

@RestController
@RequestMapping("/")
public class DiceController {
	
	@Autowired
	private DiceService diceService;
	
	//POST: /players : crea un jugador 
	@PostMapping("Players")
	public ResponseEntity<HttpStatus> createPlayer(@RequestBody Player player){
		return diceService.createPlayer(player);
		
	}
	
	//PUT /players : modifica el nom del jugador 
	@PutMapping("updateName/{playerId}/{playerName}")
	public ResponseEntity<String> updateName(@PathVariable("playerId") Integer playerId,@PathVariable("playerName") String playerName){
		
		return diceService.updateName(playerId, playerName);
	}

	//POST /players/{id}/games/ : un jugador específic realitza una tirada  dels daus.
	@PostMapping("DiceThrow/{playerId}")
	public ResponseEntity<String> createDiceThrow(@PathVariable("playerId") Integer playerId){
		
		return diceService.createDiceThrow(playerId);
	}
	//DELETE /players/{id}/games: elimina les tirades del jugador 
	@DeleteMapping("deleteThrows/{playerId}")
	public ResponseEntity<String> deleteThrows(@PathVariable(name="playerId") int playerId){
	
		return diceService.deleteThrows(playerId);
	}
	
	//GET /players/: retorna el llistat de tots  els jugadors del sistema  amb el seu  percentatge mig  d’èxits
	@GetMapping("playersList")
	public ResponseEntity<List<String>> playersList(){

		return diceService.playersList();
	}
	
	//GET /players/{id}/games: retorna el llistat de jugades per un jugador.
	@GetMapping("throwsList/{playerId}")
	public ResponseEntity<List<DiceThrow>> throwsList(@PathVariable("playerId") int playerId){

		return diceService.throwsList(playerId);
	}
	
	//GET /players/ranking: retorna el ranking  mig de tots els   jugadors del sistema .  És a dir, el  percentatge mig  d’èxits.
	@GetMapping("ranking")
	public ResponseEntity<String> ranking() {
		
		return diceService.ranking();
		
	}
	//GET /players/ranking/loser: retorna el jugador  amb pitjor percentatge d’èxit
	@GetMapping("loser")
	public ResponseEntity<String> loser(){
		
		return diceService.loser();
	}
	//GET /players/ranking/winner: retorna el  jugador amb  pitjor percentatge   d’èxit 
	@GetMapping("winner")
	public ResponseEntity<String> winner(){
		
		return diceService.winner();
	}
	@GetMapping ("playerDetail/{playerId}")
	public ResponseEntity<Player> playerDeatail(@PathVariable("playerId") int playerId){
		
		return diceService.playerDeatail(playerId);
	}
	

}
