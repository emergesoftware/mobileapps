package za.co.emergesoftware.android.util;

import java.util.ArrayList;

public final class SouthAfricanProvinces {
	
	private static ArrayList<String> provinces = new ArrayList<String>();
	
	public static ArrayList<String> getProvincesList() {
		
		if (provinces == null)
			provinces = new ArrayList<String>();
		
		if (provinces.isEmpty()){
		
			provinces.add("Western Cape");
			provinces.add("Northern Cape");
			provinces.add("Eastern Cape");
			provinces.add("KwaZulu Natal");
			provinces.add("Free State");
			provinces.add("Gauteng");
			provinces.add("North West");
			provinces.add("Mpumalanga");
			provinces.add("Limpopo");
		}
		
		return provinces;
		
	}
}
