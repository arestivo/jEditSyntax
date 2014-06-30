import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.text.Segment;

import syntax.DefaultTokenHandler;
import syntax.ModeProvider;
import syntax.Token;
import syntax.TokenMarker;
import util.XMLUtilities;
import base.Log;
import base.Mode;
import base.ModeCatalogHandler;

public class Test {
	public static void main(String[] args) {
		loadModeCatalog("modes");
		Mode mode = ModeProvider.instance.getModeForFile("teste.cpp", null);
		
		TokenMarker tokenMaker = mode.getTokenMarker();
				
		String code = "<?php $a = 5; function a() {} // this is a comment";
		DefaultTokenHandler dth = new DefaultTokenHandler();
		tokenMaker.markTokens(null, dth, new Segment(code.toCharArray(), 0, code.length()));
		Token tokens = dth.getTokens();
		while (tokens != null) {
			System.out.println(Token.tokenToString(tokens.id));
			tokens = tokens.next;
		}
	}
	
	private static void loadModeCatalog(String path)
	{
		ModeCatalogHandler handler = new ModeCatalogHandler(path);
		try
		{
			InputStream _in = new FileInputStream(path + "/catalog");
			XMLUtilities.parseXML(_in, handler);
		}
		catch(IOException e)
		{
			Log.log(Log.ERROR,Test.class,e);
		}
	}	
}