package com.jaiBalramTraders.Button;

import java.time.LocalDate;

public class FormData {
    private String farmerName;
    private int noOfBags;
    private double bagWeight;
    private double ratePerBag;
    private String status;
    private LocalDate date;

    public FormData(String farmerName, int noOfBags, double bagWeight, double ratePerBag, String status, LocalDate date) {
        this.farmerName = farmerName;
        this.noOfBags = noOfBags;
        this.bagWeight = bagWeight;
        this.ratePerBag = ratePerBag;
        this.status = status;
        this.date = date;
    }

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public int getNoOfBags() {
		return noOfBags;
	}

	public void setNoOfBags(int noOfBags) {
		this.noOfBags = noOfBags;
	}

	public double getBagWeight() {
		return bagWeight;
	}

	public void setBagWeight(double bagWeight) {
		this.bagWeight = bagWeight;
	}

	public double getRatePerBag() {
		return ratePerBag;
	}

	public void setRatePerBag(double ratePerBag) {
		this.ratePerBag = ratePerBag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

    // Getters and setters...
    
}
