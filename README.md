Add new repository to `dependencies.yml`

```yaml
repositories:
    - purecode:
        type: http
        artifact: "http://play.purecode.ru/1.x/[module]-[revision].zip"
        contains:
            - ru.purecode -> *
```

Then add this module to `require` section:

```yaml
require:
    - play
    - ru.purecode -> fopdf 0.4.0
```

In your controller use:

```java
package controllers;
 
import play.modules.fopdf.PDF;
import play.mvc.Controller;
 
public class Application extends Controller {
 
    public static void index() {
        PDF.render("pdf/test.fo");
    }
 
}
```

`pdf/test.fo` is your FOP template where you can use Play! template features
like variables, links and tags

### DPI for images

```
fop.source.resolution=300
fop.target.resolution=300
```

http://xmlgraphics.apache.org/fop/1.1/configuration.html

### Custom config and fonts

You can setup logger level in application.conf
`fop.logger.level=INFO` will set logger to INFO level.
Values for this key could be found in `org.apache.log4j.Level`.

Also you can setup custom configuration file for FOP.
Write `fop.config.path=YOUR_PATH` in application.conf. YOUR_PATH is relative path from your application root folder. It's better to store it in conf dir. FOP conf file must be valid XML file.
Read this http://xmlgraphics.apache.org/fop/1.1/configuration.html for reference.

Custom fonts could be defined in your FOP conf XML file. But do not forget to define ALL fonts in this file. Pre-installed fonts WOULD NOT work if you use custom FOP config!
See samples-and-tests/pdf-sample-app/conf/fop_sample_config.xml for reference.
