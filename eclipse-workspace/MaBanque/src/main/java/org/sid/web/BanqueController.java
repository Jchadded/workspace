package org.sid.web;

import org.sid.entites.Compte;
import org.sid.entites.Operation;
import org.sid.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BanqueController {
	@Autowired
	private IBanqueMetier iBanqueMetier;
	
	@RequestMapping(("/operations"))
	public String index() {
		return "comptes";
	}
	/**
	 * @param model
	 * @param codeCompte
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/consultercompte")  //càd , quand je tape : localhost/consulterCompte j'aurai une vue qui s'appelle comptes.html
	public String consulterCompte(Model model, String codeCompte,@RequestParam(name="page",defaultValue="0")int page,@RequestParam(name="size",defaultValue="5")int size) {   //cette methode retourne une vue tous simplement
		
		
		  model.addAttribute("codeCompte",codeCompte);
		  
		  try {
		  
		  Compte compte=iBanqueMetier.consulter(codeCompte); //le bloc de try catch est		  à cause de la generation d'exception de la methode getCompte 
		 // Page<Operation> pageOperations = iBanqueMetier.listOperation(codeCompte ,0, 4);
		  //model.addAttribute("listOperations",pageOperations.getContent());
		 // int[]		  pages=new int[pageOperations.getTotalPages()]; model.addAttribute("pages",		  pages);
		  model.addAttribute("compte",compte);
		  
		  }catch (Exception e) { model.addAttribute("exception",e); }
		 
		

		return "comptes"; //càd : le nom de la vue est : comptes.html
	}
	
	

	
	
	  @RequestMapping(value="/saveOperation",method=RequestMethod.POST) public
	  String saveOperation(Model model,String typeOperation, String
	  codeCompte,double montant, String codeCompte2) { //cette methode retourne une	  vue tous simplement
	  
	  try { if(typeOperation.equals("VERS")) {
	  
	  iBanqueMetier.verser(codeCompte,montant); } else
	  if(typeOperation.equals("RETR")) {
	  
	  iBanqueMetier.retrait(codeCompte,montant); } else {
	  
	  iBanqueMetier.virement(codeCompte,codeCompte2,montant); } }catch (Exception
	  e) { model.addAttribute("error",e); return
	  "redirect:/consultercompte?codeCompte="+codeCompte+"&error="+e.getMessage();
	  }
	  
	  
	  return "redirect:/consultercompte?codeCompte="+codeCompte; }
	 
}
