package org.bridgelabz.fundoonotes.Utility;

import java.time.LocalDateTime;

public class DateTime {
	private DateTime() {
	}

	public static String today() {
		return LocalDateTime.now().toString();
	}
}