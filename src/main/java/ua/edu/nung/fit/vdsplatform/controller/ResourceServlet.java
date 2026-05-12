package ua.edu.nung.fit.vdsplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.edu.nung.fit.vdsplatform.dao.ResourceUsageDao;
import ua.edu.nung.fit.vdsplatform.model.ResourceUsage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {
        "/vds/resources",
        "/vds/resources/*"
})
public class ResourceServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ResourceUsageDao resourceUsageDao = new ResourceUsageDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            sendJson(resp, HttpServletResponse.SC_UNAUTHORIZED, Map.of("error", "Unauthorized"));
            return;
        }

        String pathInfo = req.getPathInfo();
        String serverId = req.getParameter("serverId");
        String limit = req.getParameter("limit");

        try {
            if (serverId != null) {
                Long sId = Long.parseLong(serverId);
                List<ResourceUsage> resources;
                
                if (limit != null) {
                    resources = resourceUsageDao.findLatestByServerId(sId, Integer.parseInt(limit));
                } else {
                    resources = resourceUsageDao.findByServerId(sId);
                }
                
                sendJson(resp, HttpServletResponse.SC_OK, Map.of("resources", resources));
            } else {
                sendJson(resp, HttpServletResponse.SC_BAD_REQUEST, Map.of("error", "serverId parameter required"));
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
            Double cpuPercent = ((Number) requestBody.get("cpuPercent")).doubleValue();
            Double ramPercent = ((Number) requestBody.get("ramPercent")).doubleValue();
            Double storagePercent = ((Number) requestBody.get("storagePercent")).doubleValue();
            Double bandwidthGb = ((Number) requestBody.get("bandwidthGb")).doubleValue();

            ResourceUsage usage = new ResourceUsage();
            usage.setServerId(serverId);
            usage.setCpuPercent(new java.math.BigDecimal(cpuPercent));
            usage.setRamPercent(new java.math.BigDecimal(ramPercent));
            usage.setStoragePercent(new java.math.BigDecimal(storagePercent));
            usage.setBandwidthGb(new java.math.BigDecimal(bandwidthGb));
            usage.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

            ResourceUsage saved = resourceUsageDao.saveOrUpdate(usage);

            sendJson(resp, HttpServletResponse.SC_CREATED, Map.of(
                    "status", "recorded",
                    "usageId", saved.getId()
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

