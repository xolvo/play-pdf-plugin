package play.modules.fopdf;

import java.io.IOException;

import org.apache.commons.logging.LogFactory;
import org.apache.fop.apps.FopFactory;
import org.xml.sax.SAXException;

import play.Logger;
import play.PlayPlugin;
import play.modules.fopdf.helpers.CustomFontsUriResolver;
import play.vfs.VirtualFile;

public class PdfPlugin extends PlayPlugin {
	
	private static FopFactory fop_factory;

	@Override
	public void onApplicationStart() {
		fop_factory = FopFactory.newInstance();
		VirtualFile vfile = getModuleRelativeFile("conf/fonts/conf.xml");
		
		try {
			fop_factory.setUserConfig(vfile.getRealFile());
		} catch (SAXException | IOException e) {
			Logger.error(e, "Can not set user's FOP config");
		}
		
		fop_factory.setURIResolver(new CustomFontsUriResolver());
		
		// Log only FATAL errors and not log messages like "Rendered page #1."
		org.apache.commons.logging.impl.Log4JLogger log = (org.apache.commons.logging.impl.Log4JLogger) LogFactory.getLog("org.apache.fop");
		log.getLogger().setLevel(org.apache.log4j.Level.FATAL);
    }
	
	public static FopFactory factory() {
		return fop_factory;
	}
	
	public static VirtualFile getModuleRelativeFile(String path) {
		return VirtualFile.fromRelativePath("{module:fopdf}" + path);
	}

}
