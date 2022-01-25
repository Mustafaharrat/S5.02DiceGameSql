package dices.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import dices.entity.Player;

@Repository
@Transactional
public interface UserDao extends JpaRepository<Player,Integer> {

}
