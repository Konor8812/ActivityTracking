package tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTag extends SimpleTagSupport {

    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");
        out.print(formatter.format(LocalDateTime.now()));
    }
}
