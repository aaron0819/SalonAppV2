package com.hampson.salonapp.util;

import static com.hampson.salonapp.util.AppointmentUtils.buildAppointmentObject;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hampson.salonapp.model.AppointmentRequest;

public class AppointmentUtilsTest {

	@Test
	public void testConstructor() {
		assertEquals(new AppointmentUtils().getClass(), AppointmentUtils.class);
	}
	
	@Test
	public void testBuildAppointmentObject() {
		AppointmentRequest expected = buildTestAppointmentRequest();
		AppointmentRequest actual = buildAppointmentObject(expected.getAppointmentType(), expected.getCustomerId(),
				expected.getAppointmentDate(), expected.getAlternateAppointmentDate(),
				expected.getAppointmentStartTime(), expected.getAlternateAppointmentTime(),
				expected.getPreferredStylist());

		assertTrue(reflectionEquals(expected, actual));
	}

	private AppointmentRequest buildTestAppointmentRequest() {
		AppointmentRequest request = new AppointmentRequest();

		request.setAppointmentType("Haircut");
		request.setCustomerId(404);
		request.setAppointmentDate("2017-04-25");
		request.setAlternateAppointmentDate("");
		request.setAppointmentStartTime("03:30 PM");
		request.setAlternateAppointmentTime("");
		request.setPreferredStylist("No Preference");

		return request;
	}

}
