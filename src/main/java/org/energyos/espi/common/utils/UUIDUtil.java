package org.energyos.espi.common.utils;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.springframework.security.crypto.codec.Base64;

public class UUIDUtil {
	public static long mask(String text) {
		byte[] encodedvalue = Base64.encode(text.getBytes());
		long value = 0;
		for (int i = 0; i < encodedvalue.length; i++) {
			value += ((long) encodedvalue[i] & 0xffL) << (8 * i);
		}
		return value;
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

	public static void main2(String[] args) throws Exception {

		PrintWriter writer = new PrintWriter("data.txt", "UTF-8");
		long mask = 787812651212312l;
		long data = 1000l;
		System.out.println(data);
		long x = data ^ mask;
		System.out.println(x);
		System.out.println(x ^ mask);
		// for (int i = 0; i < Integer.MAX_VALUE; i++) {
		// System.out.println(i + " --->" + mask(String.valueOf(i)));
		// writer.println(mask(String.valueOf(i)));
		// }
		writer.close();

		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(String.valueOf(data).getBytes());

		System.out.println(maskCCNumber(String.valueOf(data)));
	

	}

	public static String maskCCNumber(String ccnum) {
		long starttime = System.currentTimeMillis();
		int total = ccnum.length();
		int startlen = 4, endlen = 4;
		int masklen = total - (startlen + endlen);
		StringBuffer maskedbuf = new StringBuffer(ccnum.substring(0, startlen));
		for (int i = 0; i < masklen; i++) {
			maskedbuf.append('X');
		}
		maskedbuf.append(ccnum.substring(startlen + masklen, total));
		String masked = maskedbuf.toString();
		long endtime = System.currentTimeMillis();
		System.out.println("maskCCNumber:=" + masked + " of :" + masked.length() + " size");
		System.out.println("using StringBuffer=" + (endtime - starttime) + " millis");
		return masked;
	}

	private static String md5(String s) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(), 0, s.length());
			BigInteger i = new BigInteger(1, m.digest());
			System.out.println(i);
			System.out.println(i.longValue());
			return String.format("%1$032x", i);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		HexBinaryAdapter ha= new HexBinaryAdapter();
		byte [] data=ha.unmarshal("B40E2000");
		System.out.println( " "+data[0]+ " "+data[1] + " "+data[2]+ " "+data[3] + " "+data.length);
		String[] masterlist = { "/espi/1_1/resource/LocalTimeParameters/EST", "/espi/1_1/resource/ReadingType/1",
				"/espi/1_1/resource/ReadingType/11", "/espi/1_1/resource/ReadingType/12",
				"/espi/1_1/resource/ReadingType/13", "/espi/1_1/resource/ReadingType/14",
				"/espi/1_1/resource/ReadingType/15", "/espi/1_1/resource/ReadingType/16",
				"/espi/1_1/resource/ReadingType/21", "/espi/1_1/resource/ReadingType/22",
				"/espi/1_1/resource/ReadingType/31", "/espi/1_1/resource/DataCustodian/ApplicationInformation/1",
				"/espi/1_1/resource/DataCustodian/ApplicationInformation/101",
				"/espi/1_1/resource/DataCustodian/ApplicationInformation/102",
				"/espi/1_1/resource/DataCustodian/ApplicationInformation/103",
				"/espi/1_1/resource/DataCustodian/ApplicationInformation/104",
				"/espi/1_1/resource/DataCustodian/ApplicationInformation/105","/espi/1_1/resource/DataCustodian/ApplicationInformation/106" };

		for (int i = 0; i < 0; i++) {

			System.out.println(masterlist[i] + " UUID " + uuid("greenbutton.londonhydro.com", masterlist[i]));

		}
		System.out.println(" UUID "
				+ uuid("greenbutton.londonhydro.com",
						"/espi/1_1/resource/RetailCustomer/0000366183/UsagePoint/000007383764:D299829-01/MeterReading/11"));
		
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
