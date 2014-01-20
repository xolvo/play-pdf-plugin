Add new repository to `dependencies.yml`

```
repositories:
    - purecode:
        type: http
        artifact: "http://play.purecode.ru/1.x/[module]-[revision].zip"
        contains:
            - ru.purecode -> *
```

Then add this module to `require` section:

```
require:
    - play
    - ru.purecode -> fopdf 0.2.0
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
