package com.travelers.hackathon.sampleData;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static com.travelers.hackathon.sampleData.GenerateDataForStartAndStop.metersPerSecond;

public class TestGenerateDataForStartAndStop {

	private GenerateDataForStartAndStop sut;

	@Before
	public void setup() {
		sut = new GenerateDataForStartAndStop();
	}

	@Test
	public void testGetSamples() {
		assertEquals(4, sut.getTotalSamples(60, 15));
		assertEquals(5, sut.getTotalSamples(61, 15));
		assertEquals(1, sut.getTotalSamples(1, 15));

	}

	@Test
	public void testSecondsToDeltaV() {
		assertEquals(5, sut.getSecondsToDeltaV(0.0f, 15.0f, 3.0f));
		assertEquals(5, sut.getSecondsToDeltaV(0.0f, 15.0f, 3.1f));
		assertEquals(6, sut.getSecondsToDeltaV(0.0f, 15.0f, 2.9f));

		assertEquals(5, sut.getSecondsToDeltaV(15.0f, 0.0f, -3.0f));
		assertEquals(5, sut.getSecondsToDeltaV(15.0f, 0.0f, -3.1f));
		assertEquals(6, sut.getSecondsToDeltaV(15.0f, 0.0f, -2.9f));
	}

	@Test
	public void testStartAndStopTimes() {
		float[] data = sut.velocityOverTime(15, // Samples per second
				5 * 60, // 5 minutes
				metersPerSecond(75), // 75 MPH MAX
				3.2f, // the average rate of acceleration of all the ordinary
						// cars I found was between 3 and 4 m/s2.
				-2.8f, // deceleration is slower than acceleration.
				0.0f, // Start at 0 MPH
				0.0f // Stop at 0 MPH
		);
		for (int i = 0; i < data.length; i++) {
			System.out.println((i + 1) * 15 + "s: " + data[i]);
		}
		// assertEquals("First entry should be 0", 0.0f, data[0], 0);
		assertEquals("last entry should be 0", 0.0f, data[data.length - 1], 0);
		assertEquals(data[data.length / 2], metersPerSecond(75), 0);
		assertEquals(20, data.length);

		data = sut.velocityOverTime(2, // Samples per second
				2 * 60, // 2 minutes
				metersPerSecond(75), // 75 MPH MAX
				3.2f, // the average rate of acceleration of all the ordinary
						// cars I found was between 3 and 4 m/s2.
				-2.8f, // deceleration is slower than acceleration.
				0.0f, // Start at 0 MPH
				0.0f // Stop at 0 MPH
		);
		for (int i = 0; i < data.length; i++) {
			System.out.println((i + 1) * 2 + "s: " + data[i]);
		}
	}

}