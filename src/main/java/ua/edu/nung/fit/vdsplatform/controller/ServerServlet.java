package ua.edu.nung.fit.vdsplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.edu.nung.fit.vdsplatform.service.ServerManagementService;
import ua.edu.nung.fit.vdsplatform.model.Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {
        "/vds/servers",
        "/vds/servers/*"
})
public class ServerServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ServerManagementService serverService = new ServerManagementService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            sendJson(resp, HttpServletResponse.SC_UNAUTHORIZED, Map.of("error", "Unauthorized"));
            return;
        }

        Long userId = (Long) session.getAttribute("userId");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /vds/servers - List user's servers
                List<Server> servers = serverService.getUserServers(userId);
                sendJson(resp, HttpServletResponse.SC_OK, Map.of("servers", servers));
            } else {
                // GET /vds/servers/{id} - Get specific server
                String[] parts = pathInfo.split("/");
                if (parts.length == 2 && !parts[1].isEmpty()) {
                    Long serverId = Long.parseLong(parts[1]);
                    Server server = serverService.getServer(serverId);
                    
                    if (server == null) {
                        sendJson(resp, HttpServletResponse.SC_NOT_FOUND, Map.of("error", "Server not found"));
                        return;
                    }
                    
                    // Verify ownership
                    if (!server.getOwnerId().equals(userId)) {
                        sendJson(resp, HttpServletResponse.SC_FORBIDDEN, Map.of("error", "Access denied"));
                        return;
                    }
                    
                    sendJson(resp, HttpServletResponse.SC_OK, server);
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

        Long userId = (Long) session.getAttribute("userId");

        try {
            Map<String, Object> requestBody = objectMapper.readValue(req.getInputStream(), Map.class);

            String name = (String) requestBody.get("name");
            String osType = (String) requestBody.get("osType");
            Integer cpuCores = ((Number) requestBody.get("cpuCores")).intValue();
            Integer ramGb = ((Number) requestBody.get("ramGb")).intValue();
            Integer storageGb = ((Number) requestBody.get("storageGb")).intValue();

            Server server = serverService.createServer(userId, name, osType, cpuCores, ramGb, storageGb);

            sendJson(resp, HttpServletResponse.SC_CREATED, Map.of(
                    "status", "created",
                    "serverId", server.getId()
            ));
        } catch (Exception e) {
            sendJson(resp, HttpServletResponse.SC_BAD_REQUEST, Map.of("error", e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            sendJson(resp, HttpServletResponse.SC_UNAUTHORIZED, Map.of("error", "Unauthorized"));
            return;
        }

        Long userId = (Long) session.getAttribute("userId");
        String pathInfo = req.getPathInfo();

        try {
            String[] parts = pathInfo.split("/");
            if (parts.length == 2 && !parts[1].isEmpty()) {
                Long serverId = Long.parseLong(parts[1]);
                Server server = serverService.getServer(serverId);

                if (server == null) {
                    sendJson(resp, HttpServletResponse.SC_NOT_FOUND, Map.of("error", "Server not found"));
                    return;
                }

                if (!server.getOwnerId().equals(userId)) {
                    sendJson(resp, HttpServletResponse.SC_FORBIDDEN, Map.of("error", "Access denied"));
                    return;
                }

                Map<String, Object> requestBody = objectMapper.readValue(req.getInputStream(), Map.class);
                
                if (requestBody.containsKey("status")) {
                    Server.ServerStatus newStatus = Server.ServerStatus.valueOf((String) requestBody.get("status"));
                    server.setStatus(newStatus);
                }

                Server updated = serverService.updateServer(server);
                sendJson(resp, HttpServletResponse.SC_OK, Map.of("status", "updated", "server", updated));
            } else {
                sendJson(resp, HttpServletResponse.SC_BAD_REQUEST, Map.of("error", "Invalid request"));
            }
        } catch (Exception e) {
            sendJson(resp, HttpServletResponse.SC_BAD_REQUEST, Map.of("error", e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            sendJson(resp, HttpServletResponse.SC_UNAUTHORIZED, Map.of("error", "Unauthorized"));
            return;
        }

        Long userId = (Long) session.getAttribute("userId");
        String pathInfo = req.getPathInfo();

        try {
            String[] parts = pathInfo.split("/");
            if (parts.length == 2 && !parts[1].isEmpty()) {
                Long serverId = Long.parseLong(parts[1]);
                Server server = serverService.getServer(serverId);

                if (server == null) {
                    sendJson(resp, HttpServletResponse.SC_NOT_FOUND, Map.of("error", "Server not found"));
                    return;
                }

                if (!server.getOwnerId().equals(userId)) {
                    sendJson(resp, HttpServletResponse.SC_FORBIDDEN, Map.of("error", "Access denied"));
                    return;
                }

                serverService.deleteServer(serverId);
                sendJson(resp, HttpServletResponse.SC_OK, Map.of("status", "deleted"));
            } else {
                sendJson(resp, HttpServletResponse.SC_BAD_REQUEST, Map.of("error", "Invalid request"));
            }
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

