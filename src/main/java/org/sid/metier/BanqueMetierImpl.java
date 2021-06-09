package org.sid.metier;

import java.util.Date;

import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.entities.Compte;
import org.sid.entities.CompteCourant;
import org.sid.entities.Operation;
import org.sid.entities.Retrait;
import org.sid.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BanqueMetierImpl implements IBanqueMetier {
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	OperationRepository operationrepository;
	@Override
	public Compte consulterCompte(String codeCompte) {

		Compte cp = compteRepository.getById(codeCompte);
		if(cp==null) throw new RuntimeException("Compte introuvable");	
		return cp;
	}

	@Override
	public void verser(String codeCompte, double mantant) {
		Compte cp = consulterCompte(codeCompte);
		Versement v = new Versement(new Date(), mantant, cp);
		operationrepository.save(v);
		cp.setSolde(cp.getSolde()+mantant);
		compteRepository.save(cp);
		
	}

	@Override
	public void retirer(String codeCompte, double mantant) {
		Compte cp = consulterCompte(codeCompte);
		double facilitesCaisse=0;
		if(cp instanceof CompteCourant)
			facilitesCaisse= ((CompteCourant) cp).getDecouvert();
		if(cp.getSolde()+facilitesCaisse<mantant)
			throw new RuntimeException("Solde insuffisant");
		Retrait r = new Retrait(new Date(), mantant, cp);
		operationrepository.save(r);
		cp.setSolde(cp.getSolde()-mantant);
		compteRepository.save(cp);
		
	}

	@Override
	public void virement(String codeCompte1, String codeCompte2, double mantant) {
		retirer(codeCompte1, mantant);
		verser(codeCompte2, mantant);
		
	}

	@Override
	public Page<Operation> listeOperation(String codeCompte, int page, int size) {
		// TODO Auto-generated method stub
		return operationrepository.listOperation(codeCompte, PageRequest.of(page, size));
	}

}
