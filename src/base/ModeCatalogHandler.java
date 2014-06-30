/*
 * ModeCatalogHandler.java - XML handler for mode catalog files
 * Copyright (C) 2000, 2001 Slava Pestov
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package base;

//{{{ Imports
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
//}}}

import syntax.ModeProvider;
import util.XMLUtilities;

/**
 * @author Slava Pestov
 */
public class ModeCatalogHandler extends DefaultHandler
{
	//{{{ ModeCatalogHandler constructor
	public ModeCatalogHandler(String directory)
	{
		this.directory = directory;
	} //}}}

	//{{{ resolveEntity() method
	public InputSource resolveEntity(String publicId, String systemId)
	{
		return XMLUtilities.findEntity(systemId, "catalog.dtd", getClass());
	} //}}}

	//{{{ startElement() method
	public void startElement(String uri, String localName,
							 String qName, Attributes attrs)
	{
		if (qName.equals("MODE"))
		{
			String modeName = attrs.getValue("NAME");

			String file = attrs.getValue("FILE");
			if(file == null)
			{
				Log.log(Log.ERROR,this,directory + "catalog:"
					+ " mode " + modeName + " doesn't have"
					+ " a FILE attribute");
			}

			String filenameGlob = attrs.getValue("FILE_NAME_GLOB");
			String firstlineGlob = attrs.getValue("FIRST_LINE_GLOB");

			Mode mode = instantiateMode(modeName);

			ModeProvider.instance.addMode(mode);

			String path = directory + "/" + file;
			mode.setProperty("file",path);

			mode.unsetProperty("filenameGlob");
			if(filenameGlob != null)
				mode.setProperty("filenameGlob",filenameGlob);

			mode.unsetProperty("firstlineGlob");
			if(firstlineGlob != null)
				mode.setProperty("firstlineGlob",firstlineGlob);

			mode.init();
		}
	} //}}}

	public Mode instantiateMode(String modeName)
	{
		return new Mode(modeName);
	}

	private String directory;

}

