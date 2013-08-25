package com.jasty.servlet;

import com.jasty.js.JsExpression;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet to handle all requests to FormEngine
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public abstract class FormEngineServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        FormEngineFactory.setInstance(createFormEngineFactory(config));
    }

    protected abstract FormEngineFactory createFormEngineFactory(ServletConfig config);

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        JsExpression expr = FormEngineFactory.getInstance().getFormEngine(req, resp).processEvent();
        writeScript(expr, req, resp);
    }

    public static void writeScript(JsExpression expr, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if(resp.isCommitted()) return;

        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Cache-Control", "no-store");
        resp.setHeader("Cache-Control", "must-revalidate"); // HTTP 1.1
        resp.addHeader("Pragma", "no-cache"); // HTTP 1.0
        resp.setDateHeader("Expires", 0); // Proxies.

        String script = expr.encode();
        PrintWriter writer = resp.getWriter();

        if(req.getContentType().startsWith("multipart/form-data")) {
            // to avoid displaying download dialog put script in html-tag.
            // see jquery.form.js docs

            String userAgent = req.getHeader("user-agent").toLowerCase();

            // STK: performance fix for firefox, when trying to render big javascript in textarea - use span containing comment instead
            if(userAgent.contains("firefox")) {
                writer.write("<span><!--");
                writer.write(script);
                writer.write("--></span>");
            }
            else {
                writer.write("<textarea>");
                writer.write(script);
                writer.write("</textarea>");
            }
        }
        else {
            resp.setContentType("text/javascript");
            writer.write(script);
        }
    }
}
