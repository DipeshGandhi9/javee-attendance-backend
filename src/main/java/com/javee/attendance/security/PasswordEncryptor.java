package com.javee.attendance.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Base64;

public class PasswordEncryptor
{

	private static final Logger LOGGER = LoggerFactory.getLogger( PasswordEncryptor.class );

	public static String encrypt( String text )
	{
		int splitter = 2;
		String encryptedText = "";

		StringBuilder stringBuilder = new StringBuilder( "_" );
		String updatedText = text.substring( splitter ).concat( text.substring( 0, splitter ) );
		for ( int i = 0; i < updatedText.length(); i++ )
		{
			char c = (char) ( ( (int) updatedText.charAt( i ) ) + ( (int) splitter ) );
			stringBuilder.append( c );
		}
		stringBuilder.append( "#" );
		encryptedText = stringBuilder.toString();

		encryptedText = Base64.getEncoder().encodeToString( encryptedText.getBytes() );

		return encryptedText;
	}

}

