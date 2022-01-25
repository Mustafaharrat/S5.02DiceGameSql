package dices.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dices.entity.DiceThrow;

@Repository
@Transactional
public interface DiceThrowDao extends JpaRepository<DiceThrow,Integer> {

	List<DiceThrow> findAllByPlayerid(int playerId);
	
	public void deleteAllByPlayerid(int playerId);
}
