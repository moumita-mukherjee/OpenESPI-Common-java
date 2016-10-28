package org.energyos.espi.common.utils;

public class MemUtil {
	private static final long MB = 1024 * 1024L;

	public static void memprofile(String note) {
		System.err.println("Memory Profile :" + note + " Max= " + (Runtime.getRuntime().maxMemory() / MB) + " Total= "
				+ (Runtime.getRuntime().totalMemory() / MB) + " used= "
				+ ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / MB) + "  free= "
				+ (Runtime.getRuntime().freeMemory() / MB));
	}
}
