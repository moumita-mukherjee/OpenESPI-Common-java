package org.energyos.espi.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

public class UUIDUtil {

	public static String maskcustomer(String text) {
		return mask(text, 10);
	}

	public static String mask(String text, int length) {
		if (text.length() >= length) {
			return text;
		} else {
			StringBuffer sb = new StringBuffer(12);
			for (int i = text.length(); i < length; i++) {
				sb.append("0");
			}
			return sb.toString() + text;
		}
	}

	public static void main(String[] args) {
		System.out.println(" UUID " + uuid("greenbutton.londonhydro.com", "/espi/1_1/resource/RetailCustomer/1000016554/UsagePoint/BL001EC01088F5/MeterReading/31"));		
	}

	// UUID 2dd40c93-bbf1-4c4d-8e0e-9c68a0591639
	public static UUID uuid(String objectid) {
		return uuid("greenbutton.londonhydro.com", objectid);
	}

	public static UUID uuid(String namespace, String name) {

		// Calculate hash value
		MessageDigest md;
		String hash = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.reset();
			md.update("6ba7b8119dad11d180b400c04fd430c8".getBytes());
			md.update(namespace.getBytes("UTF-8"));
			md.update(name.getBytes("UTF-8"));
			hash = byteToHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String result = "";
		result = String.format("%s-%s-%04x-%04x-%s",

		// 32 bits for "time_low"
				hash.substring(0, 8),

				// 16 bits for "time_mid"
				hash.substring(8, 12),

				// 16 bits for "time_hi_and_version",
				// four most significant bits holds version number 3
				(Integer.parseInt(hash.substring(12, 16), 16) & 0x0fff) | 0x5000,

				// 16 bits, 8 bits for "clk_seq_hi_res",
				// 8 bits for "clk_seq_low",
				// two most significant bits holds zero and one for variant
				// DCE1.1
				(Integer.parseInt(hash.substring(16, 20), 16) & 0x3fff) | 0x8000,

				// 48 bits for "node"
				hash.substring(20, 32));

		return UUID.fromString(result);

	}

	public static UUID uuid2(String namespace, String name) {

		// Calculate hash value
		MessageDigest md;
		String hash = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.reset();
			md.update("6ba7b8119dad11d180b400c04fd430c8".getBytes());
			md.update(namespace.getBytes("UTF-8"));
			md.update(name.getBytes("UTF-8"));
			hash = byteToHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuffer result = new StringBuffer(16);
		// result = String.format("%s-%s-%04x-%04x-%s",

		// 32 bits for "time_low"
		result.append(hash.substring(0, 8));
		result.append("-");

		// 16 bits for "time_mid"
		result.append(hash.substring(8, 12));
		result.append("-");

		// 16 bits for "time_hi_and_version",
		// four most significant bits holds version number 3
		result.append(Integer.toHexString((Integer.parseInt(hash.substring(12, 16), 16) & 0x0fff) | 0x5000));
		result.append("-");

		// 16 bits, 8 bits for "clk_seq_hi_res",
		// 8 bits for "clk_seq_low",
		// two most significant bits holds zero and one for variant DCE1.1
		result.append(Integer.toHexString((Integer.parseInt(hash.substring(16, 20), 16) & 0x3fff) | 0x8000));
		result.append("-");

		// 48 bits for "node"
		result.append(hash.substring(20, 32));

		return UUID.fromString(result.toString());

	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

}
