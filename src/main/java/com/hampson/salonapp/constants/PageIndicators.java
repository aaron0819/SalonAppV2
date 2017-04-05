package com.hampson.salonapp.constants;

public enum PageIndicators {
	CUSTOMER(1), STYLIST(2), SALON_MANAGER(3);
	
	  private int value;    

	  private PageIndicators(int value) {
	    this.value = value;
	  }

	  public int getValue() {
	    return value;
	  }
}

