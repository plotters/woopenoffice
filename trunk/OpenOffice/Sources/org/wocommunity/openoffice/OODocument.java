package org.wocommunity.openoffice;

import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.CloseVetoException;
import com.webobjects.foundation.NSLog;

public class OODocument {

	protected XComponentLoader 		mxComponentLoader;
	protected XComponent 			template;
	protected XStorable 			templateStore;
	protected String				documentUrl;
	
	public final static String		PDF_FILTER_NAME 			= "writer_pdf_Export";

	/*
	 * ./Contents/share/registry/modules/org/openoffice/TypeDetection/Types/fcfg_xslt_types.xcu
	 * ./Contents/share/registry/modules/org/openoffice/TypeDetection/Filter/fcfg_writer_filters.xcu
	 */
	
	public OODocument(XComponentLoader _mxComponentLoader) {
		mxComponentLoader = _mxComponentLoader;
	}
	
	public void setDocumentUrl(String _documentUrl) {
		documentUrl = _documentUrl;
	}
	
	public String documentUrl() {
		return documentUrl;
	}
	
	public void load() throws IOException, IllegalArgumentException {
		PropertyValue[] loadProps = new PropertyValue[1];
		loadProps[0] = new PropertyValue(); 
		loadProps[0].Name = "Hidden"; 
		loadProps[0].Value = new Boolean(true);
		template = mxComponentLoader.loadComponentFromURL(documentUrl(), "_blank", 0, loadProps);  
		templateStore = (XStorable) UnoRuntime.queryInterface(XStorable.class, template);
	}
	
	public XComponent template() {
		return template;
	}
	
	public void close() throws CloseVetoException {
		com.sun.star.util.XCloseable xClose = (com.sun.star.util.XCloseable) UnoRuntime.queryInterface(com.sun.star.util.XCloseable.class, template);

		if (template != null) {
			if (xClose != null) {
				try {
					xClose.close(true);
				} catch(CloseVetoException exCloseVeto) {
					NSLog.err.appendln(exCloseVeto);
				} // catch
				xClose.close(true);
			} // if
		} else {
			XComponent xDisposeable = (XComponent)UnoRuntime.queryInterface(XComponent.class,template);       
			xDisposeable.dispose();
		}
	}
	
	public void exportToPDF(String urlForPDF, boolean overwrite) throws IOException, IllegalArgumentException, CloseVetoException {
		export(urlForPDF,overwrite,PDF_FILTER_NAME);
	}
	
	public void exportToOtherFormat(String filterName, String urlForExportedDocument, boolean overwrite) throws IOException {
		export(urlForExportedDocument,overwrite,filterName);        
	}
	
		
	/**
	 * Supported URLs for documents
	 * 
	 * file:///blabla/bla.doc
	 * ftp://user:password@server.name/blabla/bla.doc
	 * 
	 * Use the file:// protocol if your WebObjects app and OpenOffice.org are running on the same server.
	 * If they are not running on the same server, you have to use the smb:// or ftp:// protocols.
	 * You can also mix & match, for example the original document might be local, but you may want
	 * to store the PDF file on a FTP server.
	 * 
	 * The URL can be a file: URL, a http: URL, an ftp: URL or a private: URL
	*
	*/
	protected void export(String urlForExportedDocument, boolean overwrite, String filterName) throws IOException {
		if (urlForExportedDocument == null) {
			urlForExportedDocument = documentUrl + ".pdf";
		}
		PropertyValue[] templateProps = new PropertyValue[2];
		templateProps[0] = new PropertyValue();
        templateProps[0].Name = "Overwrite";
        templateProps[0].Value = "False";	
        if (overwrite) {
        	templateProps[0].Value = "True";	
        } 
        templateProps[1] = new PropertyValue();
        templateProps[1].Name = "FilterName";
        templateProps[1].Value = filterName;        //com.sun.star.comp.PDF.PDFFilter
		templateStore.storeToURL(urlForExportedDocument, templateProps);		
	}
	
}
