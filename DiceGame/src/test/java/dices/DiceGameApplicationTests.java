package dices;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
//import org.hibernate.mapping.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dices.controllers.DiceController;
import dices.dao.DiceThrowDao;
import dices.dao.PlayerDao;
import dices.entity.Dice;
import dices.entity.DiceThrow;
import dices.entity.Player;



@SpringBootTest
class DiceGameApplicationTests {
	

	

}
