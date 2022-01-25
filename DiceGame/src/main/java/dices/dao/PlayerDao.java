package dices.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import dices.entity.Player;

@Repository
@Transactional
public interface PlayerDao extends JpaRepository<Player,Integer> {
	
	List<Player> findAllByName(String name);
	
	Optional<Player> findByName(String name);

}
