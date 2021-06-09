package org.sid.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CE")
public class CompteEpargne extends Compte {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double taux;

	public CompteEpargne() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompteEpargne(String codeCompte, Date datecreation, double solde, Client client) {
		super(codeCompte, datecreation, solde, client);
		// TODO Auto-generated constructor stub
	}

	public CompteEpargne(String codeCompte, Date datecreation, double solde, Client client, double taux) {
		super(codeCompte, datecreation, solde, client);
		this.taux = taux;
	}

	public double getTaux() {
		return taux;
	}

	public void setTaux(double taux) {
		this.taux = taux;
	}
	
	

}
