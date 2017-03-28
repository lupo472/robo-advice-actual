package it.uiip.digitalgarage.roboadvice.service.util;

import java.security.MessageDigest;

/**
 * This class offers a method to hash a String.
 *
 * @author Luca Antilici
 */
public class HashFunction {

	/**
	 * This method is useful to hash a String.
	 *
	 * @param input		String to hash.
	 * @return			String that represents the hashed input String.
	 */
    public static String hashStringSHA256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception e){
            return e.getLocalizedMessage();
        }
    }

}
