package play.modules.fopdf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.fop.apps.FopFactory;
import org.apache.log4j.Level;
import org.xml.sax.SAXException;

import play.Logger;
import play.Play;
import play.PlayPlugin;
import play.vfs.VirtualFile;

public class PdfPlugin extends PlayPlugin {

	private static FopFactory fop_factory;

	@Override
	public void onApplicationStart() {
		fop_factory = FopFactory.newInstance();
		
		setupFopConfig();
		
		setDpiOptionsFromApplicationConfig();
		setFontsDefaultBasePath();
		
		setFopLoggerLevel(Level.FATAL);
    }
	
	private void setupFopConfig() {
		VirtualFile vfile = getModuleRelativeFile("conf/fonts/conf.xml");
		
		try {
			fop_factory.setUserConfig(vfile.getRealFile());
		} catch (SAXException | IOException e) {
			Logger.error(e, "Can not set user's FOP config");
		}
	}
	
	private void setDpiOptionsFromApplicationConfig() {
		Properties config = Play.configuration;
		
		int source_resolution = Integer.parseInt(config.getProperty("fop.source.resolution", "72"));
		int target_resolution = Integer.parseInt(config.getProperty("fop.target.resolution", "72"));
		
		fop_factory.setSourceResolution(source_resolution);
		fop_factory.setTargetResolution(target_resolution);
	}
	
	private void setFontsDefaultBasePath() {
		try {
			String path = getModuleRelativeFile("/").getRealFile().getAbsolutePath();
			fop_factory.getFontManager().setFontBaseURL(path);
		} catch (MalformedURLException e) {
			Logger.error(e, "Oops...");
		}
	}
	
	/**
	 * Log only FATAL errors and not log messages like "Rendered page #1."
	 * 
	 * @param level
	 */
	private void setFopLoggerLevel(Level level) {
		Log4JLogger log = (Log4JLogger) LogFactory.getLog("org.apache.fop");
		log.getLogger().setLevel(org.apache.log4j.Level.FATAL);
	}
	
	private VirtualFile getModuleRelativeFile(String path) {
		return VirtualFile.fromRelativePath("{module:fopdf}" + path);
	}
	
	static FopFactory factory() {
		return fop_factory;
	}

}
