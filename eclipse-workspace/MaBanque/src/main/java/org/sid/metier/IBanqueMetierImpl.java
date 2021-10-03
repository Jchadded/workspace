package org.sid.metier;

import java.util.Date;
import java.util.List;

import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.entites.Compte;
import org.sid.entites.CompteCourant;
import org.sid.entites.Operation;
import org.sid.entites.Retrait;
import org.sid.entites.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.domain.PageRequest;

//import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;   // Spring g�re les transactions 
 

@Service  // annotation utilise pour les objets de la couche metier
@Transactional  // a importer : import org.springframework.transaction.annotation.Transactional;
public class IBanqueMetierImpl implements IBanqueMetier { //puis , on va faire le couplage faible avec la couche dao --> la couche metier va faire appel � la couche dao
  // pour faire l'injection de dependance  --> on va demander a spring d'injecter une implementation de cette interface
	@Autowired // a importer : import org.springframework.beans.factory.annotation.Autowired;
	private CompteRepository compteRepository;

	@Autowired  
	private OperationRepository operationRepository;

	 
	@Override
	public Compte consulter(String codeCompte) {
		Compte compte=compteRepository.findOne(codeCompte);
		 if (compte==null) throw new RuntimeException("Compte introuvable"); // c'est une exception non surveiller
		return compte;
	}

	@Override
	public void verser(String codeCompte, double montant) {
		Compte compte = consulter(codeCompte);
		Versement versement = new Versement(new Date(), montant,compte); // le versement est une operation
	    operationRepository.save(versement); // ici, la methode save() permet l'enregistrement
	    //mettre a jour le solde du compte
	    compte.setSolde(compte.getSolde() + montant);
	    compteRepository.save(compte); // ici, la methode save permet de mettre a jours le compte (update)  ---->Meme dans la console, on aura comme requette : Hibernate: update compte set code_cli=?, date_creation=?, solde=?, decouvert=? where code_compte=?
	 }

	@Override
	public void retrait(String codeCompte, double montant) {
		
		Compte compte = consulter(codeCompte);
		double facilitesCaisse = 0;
		
		if (compte instanceof CompteCourant) {
			
			 facilitesCaisse = ((CompteCourant) compte).getDecouvert();
			
			 if ( compte.getSolde()+facilitesCaisse < montant )  throw new RuntimeException("Slode insuffisant");
			
		}
		
		Retrait retrait = new Retrait(new Date(), montant,compte); // le retrait est une operation
	    operationRepository.save(retrait); // ici, la methode save() permet l'enregistrement
	    //mettre a jour le solde du compte
	    compte.setSolde(compte.getSolde() - montant);
	    compteRepository.save(compte); // ici, la methode save permet de mettre a jours le compte (update)
	
		
	}

	@Override
	public void virement(String codeCompteRetrait, String codeCompteVersement, double montant) {
		if(codeCompteRetrait == codeCompteVersement)throw new RuntimeException("Impossible : On ne peut pas effectuer un virement dans le meme compte");
		retrait(codeCompteRetrait,montant);
		verser(codeCompteVersement,montant);
		
	}

 	@Override                    
	public Page<Operation> listOperation(String codeCompte, int page, int sizePage) {  // page: est le numero de la page
		 
		return operationRepository.listOperation(codeCompte,  new  QPageRequest(page, sizePage) );
 	}


 	
   /*  @Override
	public List<Operation> listOperationsCompte(String codeCompte) {  // page: est le numero de la page
		 
		return operationRepository.listOperation(codeCompte);
	} */	
} 