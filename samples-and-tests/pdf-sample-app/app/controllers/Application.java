package controllers;

import play.modules.fopdf.PDF;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        PDF.render("pdf/test.fo");
    }

}
