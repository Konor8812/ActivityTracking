package controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter checks, if any User was logged in
 * If positive, allows trying to get page content
 * If negative, redirects to registration page
 *
 * Logic, which forbids Users access to Admins page and vice versa stands on relevant jsp pages
 */
public class SecurityFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        if(filterConfig.getInitParameter("active").equals("true")) {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            String[] uriComponents = req.getRequestURI().split("/");

            String requestedPage = "";

            if (uriComponents[uriComponents.length - 1].contains(".jsp")) {
                requestedPage = uriComponents[uriComponents.length - 1];
            }

            boolean hasUserInSession = req.getSession().getAttribute("regedAs") != null;

            if (requestedPage.equals("Admin.jsp")
                    || requestedPage.equals("User.jsp")
                    || requestedPage.equals("Profile.jsp")
                    || requestedPage.equals("TagSearch.jsp")
                    || requestedPage.equals("UsersRequests,jsp")) {

                if (!hasUserInSession) {
                    HttpServletResponse resp = (HttpServletResponse) servletResponse;
                    resp.sendRedirect("GreetingPage.jsp");
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            } else{
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }
}
