package org.sid.metier;

import java.util.Optional;

import org.sid.entites.Compte;
import org.sid.entites.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface IBanqueMetier {
	public Compte  consulter(String codecpt);
	public void  verser(String codecpt, double montant);
	public void  retrait(String codecpt, double montant);
	public void  virement(String codecpt1, String codecpt2,double montant);
	public Page<Operation> listOperation(String codecpt,int page, int size );


}
