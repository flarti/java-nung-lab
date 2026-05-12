package ua.edu.nung.fit.vdsplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.edu.nung.fit.vdsplatform.service.DeploymentService;
import ua.edu.nung.fit.vdsplatform.model.Deployment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {
        "/vds/deployments",
        "/vds/deployments/*"
})
public class DeploymentServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DeploymentService deploymentService = new DeploymentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            sendJson(resp, HttpServletResponse.SC_UNAUTHORIZED, Map.of("error", "Unauthorized"));
            return;
        }

        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Deployment> deployments = deploymentService.getAllDeployments();
                sendJson(resp, HttpServletResponse.SC_OK, Map.of("deployments", deployments));
            } else {
                String[] parts = pathInfo.split("/");
                if (parts.length == 2 && !parts[1].isEmpty()) {
                    Long deploymentId = Long.parseLong(parts[1]);
                    Deployment deployment = deploymentService.getDeployment(deploymentId);
                    
                    if (deployment == null) {
                        sendJson(resp, HttpServletResponse.SC_NOT_FOUND, Map.of("error", "Deployment not found"));
                        return;
                    }
                    
                    sendJson(resp, HttpServletResponse.SC_OK, deployment);
                } else {
                    sendJson(resp, HttpServletResponse.SC_BAD_REQUEST, Map.of("error", "Invalid request"));
                }
            }
        } catch (Exception e) {
            sendJson(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Map.of("error", e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            sendJson(resp, HttpServletResponse.SC_UNAUTHORIZED, Map.of("error", "Unauthorized"));
            return;
        }

        try {
            Map<String, Object> requestBody = objectMapper.readValue(req.getInputStream(), Map.class);

            Long serverId = ((Number) requestBody.get("serverId")).longValue();
            String applicationName = (String) requestBody.get("applicationName");
            String version = (String) requestBody.get("version");
            Integer port = ((Number) requestBody.get("port")).intValue();
            String configuration = (String) requestBody.get("configuration");

            Deployment deployment = deploymentService.createDeployment(serverId, applicationName, version, port, configuration);

            sendJson(resp, HttpServletResponse.SC_CREATED, Map.of(
                    "status", "created",
                    "deploymentId", deployment.getId()
            ));
        } catch (Exception e) {
            sendJson(resp, HttpServletResponse.SC_BAD_REQUEST, Map.of("error", e.getMessage()));
        }
    }

    private void sendJson(HttpServletResponse resp, int status, Object body) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(resp.getWriter(), body);
    }
}

