package play.modules.fopdf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.classloading.enhancers.LocalvariablesNamesEnhancer.LocalVariablesNamesTracer;
import play.classloading.enhancers.LocalvariablesNamesEnhancer.LocalVariablesSupport;
import play.templates.Template;
import play.templates.TemplateLoader;

public class PDF implements LocalVariablesSupport {

	/**
	 * Render template with data from args
	 * 
	 * @param fop_template_path custom path of template file with *.fo extension 
	 * @param args
	 */
	public static void render(String fop_template_path, Object... args) {
		Template template = TemplateLoader.load(fop_template_path);
		
		Map<String, Object> templateBinding = new HashMap<String, Object>(16);
        for (Object o : args) {
            List<String> names = LocalVariablesNamesTracer.getAllLocalVariableNames(o);
            for (String name : names) {
                templateBinding.put(name, o);
            }
        }
        
        throw new RenderPdfTemplate(template, templateBinding);
	}

}
