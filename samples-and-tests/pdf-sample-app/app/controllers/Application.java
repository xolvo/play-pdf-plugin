package controllers;

import java.io.IOException;

import org.apache.commons.io.FileUtils;

import play.modules.fopdf.PDF;
import play.mvc.Controller;
import play.vfs.VirtualFile;

public class Application extends Controller {

    public static void index() {
        PDF.render("pdf/test.fo");
    }
    
    public static void index2() throws IOException {
    	VirtualFile template_vf = VirtualFile.fromRelativePath("app/views/pdf/test.fo");
    	if(template_vf == null)
    		throw new IOException("No such file");
    	
    	String text = "фавпук ывфы ваыва вып варп вап ываываыва ыва";
		String template_content = FileUtils.readFileToString(template_vf.getRealFile());
		PDF.renderContent(template_content, text);
    }
    
    public static void index3() {
    	play.modules.fopdf.Png.render("pdf/test.fo");
    }
    
    public static void index4() {
    	play.modules.fopdf.PostScript.render("pdf/test.fo");
    }

}
