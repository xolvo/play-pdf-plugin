package play.modules.fopdf.helpers;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import play.Logger;
import play.exceptions.UnexpectedException;
import play.modules.fopdf.PdfPlugin;
import play.vfs.VirtualFile;

public class CustomFontsUriResolver implements URIResolver {

	@Override
	public Source resolve(String href, String base) throws TransformerException {
		Source source = null;
		VirtualFile vfile = PdfPlugin.getModuleRelativeFile(href);
		
		try {
			source = new StreamSource(vfile.inputstream());
		} catch(UnexpectedException e) {
			Logger.error(e, "Can not resolve resuorce");
		}
		
		return source;
	}

}
