package org.sid;


import java.util.Date;

import org.sid.dao.ClientRepository;
import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.entites.Client;
import org.sid.entites.Compte;
import org.sid.entites.CompteCourant;
import org.sid.entites.CompteEpargne;
import org.sid.entites.Retrait;
import org.sid.entites.Versement;
import org.sid.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MaBanqueApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CompteRepository compteRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private IBanqueMetier banqueMetier;
	
	
	public static void main(String[] args) {
	 SpringApplication.run(MaBanqueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		  Client c1=clientRepository.save(new Client("Jawher", "jawher@gmail.com"));
		  Client c2=clientRepository.save(new Client("Hamza", "Hamza@gmail.com"));
		  Client c3=clientRepository.save(new Client("Oussama", "oussama@gmail.com"));
		  
		  Compte cp1=compteRepository.save(new CompteCourant("c1", new Date(), 9000.0,
		  c1, 3000)); Compte cp2=compteRepository.save(new CompteCourant("c2", new
		  Date(), 9000.0, c2, 3000)); Compte cp3=compteRepository.save(new
		  CompteEpargne("c3", new Date(), 9000.0, c3, 5.5));
		  
		  operationRepository.save(new Versement(new Date(), 2000.0, cp1));
		  operationRepository.save(new Retrait(new Date(), 2000.0, cp1));
		  operationRepository.save(new Versement(new Date(), 1000.0, cp1));
		  
		  operationRepository.save(new Versement(new Date(), 2000.0, cp2));
		  operationRepository.save(new Retrait(new Date(), 2000.0, cp2));
		  operationRepository.save(new Versement(new Date(), 1000.0, cp2));
		  
		  operationRepository.save(new Versement(new Date(), 2000.0, cp3));
		  operationRepository.save(new Retrait(new Date(), 2000.0, cp3));
		  operationRepository.save(new Versement(new Date(), 1000.0, cp3));
		  
		  banqueMetier.verser("c1", 222);
		 
	}

}
