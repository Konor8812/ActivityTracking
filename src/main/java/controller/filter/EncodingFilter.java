package controller.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private String encoding;
    private static Logger logger = Logger.getLogger(EncodingFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        logger.info("Encoding ==> " + encoding);
        servletRequest.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if(!encodingParam.isEmpty()){
            encoding = encodingParam;
        }else{
            logger.info("Wasn't able to read encoding params from web.xml");
        }

    }
}
