package org.wocommunity.openoffice;

import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.uno.UnoRuntime;

public class OOPresentationDocument extends OODocument {

	/**
	 * @see http://api.openoffice.org/docs/DevelopersGuide/Drawing/Drawing.xhtml
	 * @see http://lxr.go-oo.org/source/api/odk/examples/DevelopersGuide/Drawing/
	 * @param componentLoader
	 */
		
	public OOPresentationDocument(XComponentLoader componentLoader) {
		super(componentLoader);
	}

	public void create() throws IOException, IllegalArgumentException {
		setDocumentUrl("private:factory/simpress");
		load();
	}
	
	public void load() throws IOException, IllegalArgumentException {
		PropertyValue[] loadProps = new PropertyValue[2];
		loadProps[0] = new PropertyValue(); 
		loadProps[0].Name = "Hidden"; 
		loadProps[0].Value = new Boolean(true);
	    loadProps[1] = new PropertyValue();
	    loadProps[1].Name = "Silent";
	    loadProps[1].Value = new Boolean(true); 
		template = mxComponentLoader.loadComponentFromURL(documentUrl(), "_blank", 0, loadProps);  
		templateStore = (XStorable) UnoRuntime.queryInterface(XStorable.class, template);
	}
	
}
