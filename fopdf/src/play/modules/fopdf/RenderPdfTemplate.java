package play.modules.fopdf;

import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.MimeConstants;

import play.exceptions.UnexpectedException;
import play.libs.Codec;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.results.Result;
import play.templates.Template;

public class RenderPdfTemplate extends Result {
	
	/**
	 * Template's name
	 */
	private String name;
	
	/**
	 * Имя сгенерированного файла
	 */
	private String pdf_filename;
	
	/**
	 * Rendered data
	 */
	private String content;
	
	public RenderPdfTemplate(Template template, Map<String, Object> args) {
		name = template.name;
		content = template.render(args);
		
		pdf_filename = (name != null ? name : Codec.UUID()) + ".pdf";
	}

	@Override
	public void apply(Request request, Response response) {
		try {
			response.setHeader("Content-Disposition", "inline; filename=\"" + pdf_filename + "\"");
			setContentTypeIfNotSet(response, "application/pdf");
			
			Fop fop = PdfPlugin.factory().newFop(MimeConstants.MIME_PDF, response.out);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			
			StreamSource fop_template = new StreamSource(IOUtils.toInputStream(content));
			SAXResult result = new SAXResult(fop.getDefaultHandler());
			
			transformer.transform(fop_template, result);
		} catch (FOPException | TransformerFactoryConfigurationError | TransformerException e) {
			throw new UnexpectedException(e);
		}
	}

}
