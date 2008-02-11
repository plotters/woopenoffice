package org.wocommunity.openoffice;

import org.wocommunity.openoffice.OOService.Exception;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.container.XEnumeration;
import com.sun.star.container.XEnumerationAccess;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.io.IOException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XComponent;
import com.sun.star.text.*;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.CloseVetoException;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;

public class OOTextDocument extends OODocument {

	/*
	 * http://api.openoffice.org/docs/DevelopersGuide/Text/Text.xhtml
	 */
		
	public final static String		WORD97_FILTER_NAME 			= "MS Word 97"; // .doc
	public final static String		RTF_FILTER_NAME				= "Rich Text Format"; // .rtf
	public final static String		WORDPERFECT_FILTER_NAME 	= "WordPerfect"; // .wpd
	public final static String		OOWRITER_FILTER_NAME 		= "writer8"; // .odt
	public final static String		PLAINTEXT_FILTER_NAME 		= "Text"; // .txt
	public final static String		WORD2003XML_FILTER_NAME 	= "MS Word 2003 XML"; // .xml
	public final static String		LATEX_FILTER_NAME 			= "LaTeX"; // .tex
	public final static String		BIBTEX_FILTER_NAME 			= "BibTeX"; // .bib
	public final static String		HTML_FILTER_NAME 			= "HTML"; // .html
	public final static String		MEDIAWIKI_FILTER_NAME 		= "MediaWiki";  // .txt
	private XTextDocument			document;	
	
	public OOTextDocument(XComponentLoader _mxComponentLoader) {
		super(_mxComponentLoader);
	}
	
	public void create() throws IOException, IllegalArgumentException {
		setDocumentUrl("private:factory/swriter");
		load();
	}
	
	public void load() throws IOException, IllegalArgumentException {
		super.load();
		document = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, template());
	}
	
	/**
	 * @see http://api.openoffice.org/docs/common/ref/com/sun/star/text/XTextDocument.html
	 */
	public XTextDocument document() {
		return document;
	}
	
	/**
	 * @see http://api.openoffice.org/docs/common/ref/com/sun/star/text/XText.html
	 * @return
	 */
	private XText textInterface() {
		return (XText) UnoRuntime.queryInterface(XText.class,document().getText());
	}
	
	/**
	 * 
	 * @return The text, as a string, of the whole document
	 * @see http://api.openoffice.org/docs/DevelopersGuide/Text/Text.xhtml#1_3_1_1_3_Control_Characters
	 */
	public String wholeText() {
		return textInterface().getString();		
	}
	
	/**
	 * This method will replace the whole text in the document with the new content.
	 */
	public void setWholeText(String content) {
		textInterface().setString(content);
	}
	
	public void insertPlainText(String content) throws IllegalArgumentException {
		textInterface().insertString(textInterface().getEnd(), content, false);
	}
	
	public void insertParagraphBreak() throws IllegalArgumentException {
		textInterface().insertControlCharacter(textInterface().getEnd(),ControlCharacter.PARAGRAPH_BREAK, false);
	}
	
	public void appendParagraph() throws IllegalArgumentException {
		textInterface().insertControlCharacter(textInterface().getEnd(),ControlCharacter.APPEND_PARAGRAPH, false);
	}
	
	public void insertSoftHyphen() throws IllegalArgumentException {
		textInterface().insertControlCharacter(textInterface().getEnd(),ControlCharacter.SOFT_HYPHEN, false);
	}
	
	public void insertLineBreak() throws IllegalArgumentException {
		textInterface().insertControlCharacter(textInterface().getEnd(),ControlCharacter.LINE_BREAK, false);
	}
	
	public void insertHardSpace() throws IllegalArgumentException {
		textInterface().insertControlCharacter(textInterface().getEnd(),ControlCharacter.HARD_SPACE, false);
	}
	
	public void insertHardHyphen() throws IllegalArgumentException {
		textInterface().insertControlCharacter(textInterface().getEnd(),ControlCharacter.HARD_HYPHEN, false);
	}
	
	public NSArray paragraphs() throws Exception {
		NSMutableArray paragraphs = new NSMutableArray();
		try {
			XEnumerationAccess xParaAccess = (XEnumerationAccess) UnoRuntime.queryInterface(XEnumerationAccess.class, textInterface());
			XEnumeration xParaEnum = xParaAccess.createEnumeration();

			XTextCursor mxDocCursor = textInterface().createTextCursor();
			XParagraphCursor xParaCursor = (XParagraphCursor) UnoRuntime.queryInterface(XParagraphCursor.class, mxDocCursor);
			xParaCursor.gotoStartOfParagraph(true);
			while (xParaEnum.hasMoreElements()) {
				xParaCursor.gotoNextParagraph(true);
				paragraphs.addObject(xParaCursor.getString());
				if (!xParaCursor.gotoNextParagraph(false)) {
					xParaCursor.gotoEndOfParagraph(true);
				} else {
					xParaCursor.gotoPreviousParagraph(false);
				}
				xParaEnum.nextElement();
			}

		} catch (java.lang.Exception exception) {
			throw new OOService.Exception("Error when fetching paragraphs: ", exception);
		}
		return paragraphs.immutableClone();
	}	
	
	public void exportToMSWord(String urlForExportedDocument, boolean overwrite) throws IOException, IllegalArgumentException, CloseVetoException {
		export(urlForExportedDocument,overwrite,WORD97_FILTER_NAME);
	}
	
	public void exportToHTML(String urlForPDF, boolean overwrite) throws IOException, IllegalArgumentException, CloseVetoException {
		export(urlForPDF,overwrite,HTML_FILTER_NAME);
	}
	
	/*
	 * ./Contents/share/registry/modules/org/openoffice/TypeDetection//Types/fcfg_writer_types.xcu
	 * 
	 */
	
	
}
