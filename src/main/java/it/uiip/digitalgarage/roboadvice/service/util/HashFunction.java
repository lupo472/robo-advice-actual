package it.uiip.digitalgarage.roboadvice.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

/**
 * Created by Luca on 01/03/2017.
 */
public class HashFunction {

    public static String hashStringSHA256(String input){

        try {
            String output;

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
            return "Error";
        }
    }
}
