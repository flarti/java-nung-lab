package ua.edu.nung.fit.vdsplatform.service;

import ua.edu.nung.fit.vdsplatform.dao.ServerDao;
import ua.edu.nung.fit.vdsplatform.model.Server;

import java.math.BigDecimal;
import java.util.List;

public class ServerManagementService {

    private final ServerDao serverDao = new ServerDao();

    public Server createServer(Long ownerId, String name, String osType, Integer cpuCores, Integer ramGb, Integer storageGb) {
        Server server = new Server();
        server.setOwnerId(ownerId);
        server.setName(name);
        server.setOsType(Server.OSType.valueOf(osType));
        server.setCpuCores(cpuCores);
        server.setRamGb(ramGb);
        server.setStorageGb(storageGb);
        server.setStatus(Server.ServerStatus.PROVISIONING);

        double calculatedPrice = (cpuCores * 5.0) + (ramGb * 2.0) + (storageGb * 0.1);
        server.setMonthlyPrice(BigDecimal.valueOf(calculatedPrice));

        return serverDao.saveOrUpdate(server);
    }

    public Server getServer(Long serverId) {
        return serverDao.findById(serverId);
    }

    public List<Server> getUserServers(Long ownerId) {
        return serverDao.findByOwnerId(ownerId);
    }

    public List<Server> getAllServers() {
        return serverDao.findAll();
    }

    public Server updateServer(Server server) {
        return serverDao.saveOrUpdate(server);
    }

    public void terminateServer(Long serverId) {
        Server server = serverDao.findById(serverId);
        if (server != null) {
            server.setStatus(Server.ServerStatus.TERMINATED);
            serverDao.saveOrUpdate(server);
        }
    }

    public void deleteServer(Long serverId) {
        serverDao.delete(serverId);
    }
}

