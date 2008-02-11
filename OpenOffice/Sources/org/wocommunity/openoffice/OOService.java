package org.wocommunity.openoffice;

import com.sun.star.beans.XPropertySet;
import com.sun.star.bridge.XUnoUrlResolver;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

public class OOService {

	private XMultiComponentFactory 	mxMCF;
    private XMultiServiceFactory 	mxMSF;
    private XComponentContext 		mxComponentContext;
    private XComponentLoader 		mxComponentLoader;
	
	public void openConnection(String host,int tcpPort)
	throws OOService.Exception {
		try {
			String connectString = "uno:socket,host=" + host + ",port=" + tcpPort + ";urp;StarOffice.ServiceManager";
            mxComponentContext = Bootstrap.createInitialComponentContext(null);
            mxMCF = mxComponentContext.getServiceManager();
            Object objectUrlResolver = mxMCF.createInstanceWithContext("com.sun.star.bridge.UnoUrlResolver", mxComponentContext);
            XUnoUrlResolver xurlresolver = (XUnoUrlResolver)UnoRuntime.queryInterface(XUnoUrlResolver.class, objectUrlResolver);
            Object objectInitial = xurlresolver.resolve(connectString);
            mxMCF = (XMultiComponentFactory) UnoRuntime.queryInterface(XMultiComponentFactory.class, objectInitial);
            XPropertySet xpropertysetMultiComponentFactory = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, mxMCF);
            Object objectDefaultContext = xpropertysetMultiComponentFactory.getPropertyValue("DefaultContext");
            mxComponentContext = (XComponentContext) UnoRuntime.queryInterface(XComponentContext.class, objectDefaultContext);
            mxComponentLoader = (XComponentLoader)UnoRuntime.queryInterface(XComponentLoader.class, mxMCF.createInstanceWithContext("com.sun.star.frame.Desktop", mxComponentContext));
            if(mxMSF == null) {
                mxMSF = (XMultiServiceFactory)UnoRuntime.queryInterface(XMultiServiceFactory.class, mxComponentContext.getServiceManager());
            } 
		} catch(java.lang.Exception exception) {
			throw new OOService.Exception("Can't connect to the OpenOffice.org service", exception);
		} 
	}
	
	public XComponentLoader mxComponentLoader() {
		return mxComponentLoader;
	}
	
	public static class Exception extends java.lang.Exception {

		public Exception(String message) {
			super(message);
		} // Exception

		public Exception(String message, Throwable cause) {
			super(message, cause);
		} // Exception
	} // Exception
	
	
}
