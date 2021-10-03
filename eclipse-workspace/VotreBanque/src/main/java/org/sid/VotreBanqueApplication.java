package org.sid;

import java.util.Date;

import org.sid.dao.ClientRepository;
import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.entities.Client;
import org.sid.entities.Compte;
import org.sid.entities.CompteCourant;
import org.sid.entities.CompteEpargne;
import org.sid.entities.Retrait;
import org.sid.entities.Versement;
import org.sid.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VotreBanqueApplication  implements CommandLineRunner{
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CompteRepository compteRepository;
	
	@Autowired
	private OperationRepository operationRepository; 
	
	@Autowired
	private IBanqueMetier banqueMetier;
	
	public static void main(String[] args) {
		SpringApplication.run(VotreBanqueApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Client c1=clientRepository.save(new Client("Jawher", "jawher@gmail.com"));
		Client c2=clientRepository.save(new Client("Saber", "saber@gmail.com"));
		Client c3=clientRepository.save(new Client("Oussama", "oussama@gmail.com"));
		Compte cp1=compteRepository.save(new CompteCourant("c1", new Date(), 9000.0, c1, 3000));
		Compte cp2=compteRepository.save(new CompteCourant("c2", new Date(), 9000.0, c2, 3000));
		Compte cp3=compteRepository.save(new CompteEpargne("c3", new Date(), 9000.0, c3, 5.5));
		
		operationRepository.save(new Versement(new Date(), 2000.0, cp1));
		operationRepository.save(new Retrait(new Date(), 2000.0, cp1));
		operationRepository.save(new Versement(new Date(), 1000.0, cp1));
		
		operationRepository.save(new Versement(new Date(), 2000.0, cp2));
		operationRepository.save(new Retrait(new Date(), 2000.0, cp2));
		operationRepository.save(new Versement(new Date(), 1000.0, cp2));
		
		operationRepository.save(new Versement(new Date(), 2000.0, cp3));
		operationRepository.save(new Retrait(new Date(), 2000.0, cp3));
		operationRepository.save(new Versement(new Date(), 1000.0, cp3));
				
		banqueMetier.verser("c1", 2222222);

				
	}

}
