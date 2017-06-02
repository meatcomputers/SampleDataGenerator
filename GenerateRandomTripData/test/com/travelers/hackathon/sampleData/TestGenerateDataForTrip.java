package com.travelers.hackathon.sampleData;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static com.travelers.hackathon.sampleData.GenerateDataForStartAndStop.metersPerSecond;

public class TestGenerateDataForTrip {

	private GenerateDataForStartAndStop startAndStop;

	private GenerateDataForTrip sut;

	@Before
	public void setup() {
		startAndStop = new GenerateDataForStartAndStop();
		sut = new GenerateDataForTrip(startAndStop);
	}

	@Test
	public void testTripGenerationWithNoStops() {
		float[] data = sut.velocityOverTrip(2, 60 * 2, metersPerSecond(75), // 75
																			// MPH
																			// MAX
				3.2f, // the average rate of acceleration of all the ordinary
						// cars I found was between 3 and 4 m/s2.
				-2.8f, // deceleration is slower than acceleration. ,
				0);
		assertEquals(60, data.length);
		System.out.println("Data for a trip with no stops: ");
		for (int i = 0; i < data.length; i++) {
			System.out.println((i + 1) * 2 + "s: " + data[i]);
		}
	}

	@Test
	public void testTripGeneration() {
		float[] data = sut.velocityOverTrip(2, 60 * 6, metersPerSecond(75), // 75
																			// MPH
																			// MAX
				3.2f, // the average rate of acceleration of all the ordinary
						// cars I found was between 3 and 4 m/s2.
				-2.8f, // deceleration is slower than acceleration. ,
				3);
		assertEquals(180, data.length);
		System.out.println("Data for a trip with 3 stops: ");
		for (int i = 0; i < data.length; i++) {
			System.out.println((i + 1) * 2 + "s: " + data[i]);
		}
	}

}