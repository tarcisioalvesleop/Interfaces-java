package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	private Double pricePerDay;
	private Double pricePerHour;
	
	private BrazilTaxService taxService;

	public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
		super();
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		long t1 = carRental.getStart().getTime(); //pegando o valor em milisegundos
		long t2 = carRental.getFinish().getTime();//tempo final em milisegundos
		double hours = (double)(t2 - t1) / 1000 / 60 / 60;
		
		double basicPayment;
		if(hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour; //pegando o valor de horas arendondado para cima
		}
		else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay;
		}
		
		double tax = taxService.tax(basicPayment); //calcula o valor de imposto apartir do basicPayment
		
		carRental.setInvoice(new Invoice(basicPayment, tax));// cria um novo obj de Invoice e associa a car.Rental
		
	}
	
}
