package ua.edu.nung.fit.vdsplatform.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {
        "/dashboard",
        "/dashboard/servers",
        "/dashboard/deployments",
        "/dashboard/resources"
})
public class DashboardServlet extends HttpServlet {

    private Configuration freemarkerConfig;

    @Override
    public void init() throws ServletException {
        Object config = getServletContext().getAttribute("freemarkerConfig");

        if (config instanceof Configuration) {
            this.freemarkerConfig = (Configuration) config;
            return;
        }

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_34);
        configuration.setClassLoaderForTemplateLoading(
                Thread.currentThread().getContextClassLoader(),
                "templates"
        );
        configuration.setDefaultEncoding("UTF-8");

        this.freemarkerConfig = configuration;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        Map<String, Object> model = new HashMap<>();

        if ("/dashboard/servers".equals(path)) {
            model.put("title", "VDS Platform - My Servers");
            renderTemplate(request, response, "vds/servers.ftl", model);

        } else if ("/dashboard/deployments".equals(path)) {
            model.put("title", "VDS Platform - Deployments");
            renderTemplate(request, response, "vds/deployments-list.ftl", model);

        } else if ("/dashboard/resources".equals(path)) {
            model.put("title", "VDS Platform - Resources");
            renderTemplate(request, response, "vds/resources-monitor.ftl", model);

        } else {
            model.put("title", "VDS Platform - Dashboard");
            model.put("message", "Welcome to VDS Platform 🚀");
            model.put("description", "Manage your virtual dedicated servers efficiently and securely.");
            renderTemplate(request, response, "dashboard.ftl", model);
        }
    }

    private void renderTemplate(HttpServletRequest request,
                                HttpServletResponse response,
                                String templateName,
                                Map<String, Object> model)
            throws IOException, ServletException {

        response.setContentType("text/html; charset=UTF-8");

        try {
            Map<String, Object> mergedModel = new HashMap<>();

            Enumeration<String> attributeNames = request.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String name = attributeNames.nextElement();
                mergedModel.put(name, request.getAttribute(name));
            }

            mergedModel.putAll(model);

            Template template = freemarkerConfig.getTemplate(templateName);
            template.process(mergedModel, response.getWriter());

        } catch (TemplateException e) {
            throw new ServletException("Failed to render template: " + templateName, e);
        }
    }
}