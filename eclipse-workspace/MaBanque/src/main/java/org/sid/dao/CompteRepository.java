package org.sid.dao;

import org.sid.entites.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompteRepository extends JpaRepository<Compte, String> {
	
	
	
	  @Query("select c from Compte c where c.codeCompte=:x") 
	  public Compte findOne(@Param("x")String codecpt) ;

		 
	
}
