package org.wocommunity.openoffice;

import com.sun.star.frame.XComponentLoader;
import com.sun.star.io.IOException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.util.CloseVetoException;

public class OOSpreadsheetDocument extends OODocument {

	/*
	 * ./Contents/share/registry/modules/org/openoffice/TypeDetection/Types/fcfg_calc_types.xcu
	 */
	public final static String		HTML_FILTER_NAME 			= "HTML";
	public final static String		EXCEL97_FILTER_NAME 		= "MS Excel 97"; // .xls
	public final static String		SYLK_FILTER_NAME 			= "SYLK"; // .slk 
	public final static String		TEXTCSV_FILTER_NAME 		= "Text - txt - csv (StarCalc)"; // .txt
	public final static String		EXCEL2003XML_FILTER_NAME 	= "Microsoft Excel 2003 XML"; // .xml
	public final static String		OOWCALC_FILTER_NAME 		= "calc8"; // .ods
	
	public OOSpreadsheetDocument(XComponentLoader componentLoader) {
		super(componentLoader);
	}
	
	public void exportToMSExcel(String urlForExportedDocument, boolean overwrite) throws IOException, IllegalArgumentException, CloseVetoException {
		export(urlForExportedDocument,overwrite,EXCEL97_FILTER_NAME);
	}

}
