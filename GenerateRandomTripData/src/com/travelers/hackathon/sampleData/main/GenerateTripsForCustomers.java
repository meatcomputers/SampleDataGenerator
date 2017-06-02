package com.travelers.hackathon.sampleData.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.travelers.hackathon.sampleData.GenerateDataForStartAndStop;
import com.travelers.hackathon.sampleData.GenerateDataForTrip;
import com.travelers.hackathon.sampleData.BOM.DriverProfile;
import com.travelers.hackathon.sampleData.BOM.TripProfile;

public class GenerateTripsForCustomers {

	private static final int SAMPLE_TIME_SECONDS = 15;

	public static void main(String[] args) throws Exception {
		new GenerateTripsForCustomers().generateDataForDriverOne();
	}

	public void generateDataForDriverOne() throws Exception {
		DriverProfile driverProfile = new DriverProfile();
		driverProfile.setDriverId(1);
		driverProfile.setAcceleration(3.1f);
		driverProfile.setDeceleration(-2.4f);
		driverProfile.setPassengersPerDay(2);

		Calendar tripDate = Calendar.getInstance();
		tripDate.set(2020, Calendar.JUNE, 02, 8, 30, 0);
		TripProfile weekdayCommute = new TripProfile(driverProfile);
		weekdayCommute.setStartTime(tripDate.getTime());
		weekdayCommute.setNumberOfStops(5);
		weekdayCommute.setNumberOfUberPassengers(0);
		weekdayCommute.setMaxSpeedMilesPerHour(75);
		weekdayCommute.setAccelerationMetersPerSecondSecond(3.1f);
		weekdayCommute.setDecelerationMetersPerSecondSecond(-2.8f);
		weekdayCommute.setSampleTimeSeconds(SAMPLE_TIME_SECONDS);
		weekdayCommute.setTotalSeconds(25 * 60);

		TripProfile saturdayDrive = new TripProfile(driverProfile);
		saturdayDrive.setStartTime(tripDate.getTime());
		saturdayDrive.setNumberOfStops(3);
		saturdayDrive.setNumberOfUberPassengers(0);
		saturdayDrive.setMaxSpeedMilesPerHour(25);
		saturdayDrive.setAccelerationMetersPerSecondSecond(2.7f);
		saturdayDrive.setDecelerationMetersPerSecondSecond(-2.8f);
		saturdayDrive.setSampleTimeSeconds(SAMPLE_TIME_SECONDS);
		saturdayDrive.setTotalSeconds(5 * 60);

//		FileWriter fw = new FileWriter("Data/DriverData_01.csv");
		FileWriter fw = new FileWriter("Data/DriverData_01.json");
		BufferedWriter bw = new BufferedWriter(fw);
//		bw.write("driverID, timestamp, velocity\n");
		bw.write("data: {[\n");
		
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 5; i++) {
				tripDate.set(Calendar.HOUR, 8);
				tripDate.set(Calendar.MINUTE, 30);
				weekdayCommute.setStartTime(tripDate.getTime());
				generateTripData(driverProfile, weekdayCommute, bw);

				tripDate.add(Calendar.HOUR, 9);
				weekdayCommute.setStartTime(tripDate.getTime());
				generateTripData(driverProfile, weekdayCommute, bw);

				tripDate.add(Calendar.DATE, 1);
			}

			tripDate.set(Calendar.HOUR, 11);
			tripDate.set(Calendar.MINUTE, 30);
			saturdayDrive.setStartTime(tripDate.getTime());
			generateTripData(driverProfile, saturdayDrive, bw);
			tripDate.add(Calendar.DATE, 1);

		}

		bw.write("]}\n");

	}

	public static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

	public void generateTripData(DriverProfile profile, TripProfile trip, BufferedWriter bw) throws Exception {
		float[] tripPoints = new GenerateDataForTrip(new GenerateDataForStartAndStop()).velocityOverTrip(
				trip.getSampleTimeSeconds(), trip.getTotalSeconds(), trip.getMaxSpeedMetersPerSecond(),
				trip.getAccelerationMetersPerSecondSecond(), trip.getDecelerationMetersPerSecondSecond(),
				trip.getNumberOfStops());

		Date currentTime = trip.getStartTime();
		for (float tripPoint : tripPoints) {
			currentTime = new Date(currentTime.getTime() + 1000 * trip.getSampleTimeSeconds());
//			bw.write(profile.getDriverId() + ", " + dateFormat.format(currentTime) + ", " + tripPoint + "\n");
			bw.write("{ id: " + profile.getDriverId() 
				+ ", timestamp: " + dateFormat.format(currentTime) 
				+ ", velocity: " + tripPoint + " }, \n");
		}

	}
}