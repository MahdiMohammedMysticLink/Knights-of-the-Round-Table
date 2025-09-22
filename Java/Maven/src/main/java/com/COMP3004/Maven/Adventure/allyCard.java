 package com.COMP3004.Maven.Adventure;

import java.io.Serializable;

public class allyCard extends adventureCard implements Serializable{

	private static final long serialVersionUID = 1L;
	// Properties
	protected final int data1, data2;

	protected final String data1Type, data2Type, specialFeature;
	
	//Constructor
	public allyCard(String name, int data1, int data2, String data1Type, String data2Type, String specialFeature) {

		super("ALLY", name);
		this.data1 = data1;
		this.data2 = data2;
		this.data1Type = data1Type;
		this.data2Type = data2Type;
		this.specialFeature = specialFeature;
		
	}

	public final String getSpecialFeature() {return specialFeature;}
	
	public final String getData2Type() {return data2Type;}
	
	public final String getData1Type() {return data1Type;}
	
	public final int getData1() {return data1;};
	
	public final int getData2() {return data2;};
	
    /*
	public int compareTo(adventureCard c) {
	
		allyCard other = ((allyCard)c);
    	
    	
    	
		if (data1Type == "POINTS" && other.getData1Type() == "POINTS") {
			
			
			if (data1 == other.getData1()) {
				
				if (data2Type == other.getData2Type()) {
					
					
					return data2 - other.getData2();
					
				}
			
				
				if (data2Type != "NOTHING" && other.getData2Type() == "NOTHING") {
					
					
					return 1;
				}
				
				if (data2Type == "NOTHING" && other.getData2Type() != "NOTHING") {
					
					
					return -1;
				}
				
				
							
			}
			
			return data1 - other.getData1();
			
			
		}
		
		if (data1Type == "POINTS" && other.getData1Type() == "BIDS") {
			
			
			
			return 1;
			
			
		
		}
	
		
		
		if (data1Type == "BIDS" && other.getData1Type() == "POINTS") {
			
			
			
			return -1;
			
			
		
		}
		
		if (data1Type == "BIDS" && other.getData1Type() == "BIDS") {
			
			
			
			if (data1 == other.getData1()) {
				
				if (data2Type == other.getData2Type()) {
					
					
					return data2 - other.getData2();
					
				}
			
				
				if (data2Type != "NOTHING" && other.getData2Type() == "NOTHING") {
					
					
					return 1;
				}
				
				if (data2Type == "NOTHING" && other.getData2Type() != "NOTHING") {
					
					
					return -1;
				}
				
				
							
			}
			
			return data1 - other.getData1();
			
		}
		
	
		return 0;
*/
	protected allyCard() {


			super("AMOUR", "AMOUR");
			this.data1 = 10;
			this.data2= 1;
			this.data2Type = "BID";
			this.data1Type="POINTS";
			this.specialFeature = "NULL";


	}

}
