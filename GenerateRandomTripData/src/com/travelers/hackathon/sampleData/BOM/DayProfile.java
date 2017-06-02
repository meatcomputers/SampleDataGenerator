package com.travelers.hackathon.sampleData.BOM;

import java.util.ArrayList;
import java.util.List;

public class DayProfile {

	private List<TripProfile> trips;

	public List<TripProfile> getTrips() {
		if (trips == null) {
			trips = new ArrayList<TripProfile>(2);
		}
		return trips;
	}

}