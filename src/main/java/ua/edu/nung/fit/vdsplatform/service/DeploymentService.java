package ua.edu.nung.fit.vdsplatform.service;

import ua.edu.nung.fit.vdsplatform.dao.DeploymentDao;
import ua.edu.nung.fit.vdsplatform.model.Deployment;

import java.sql.Timestamp;
import java.util.List;

public class DeploymentService {

    private final DeploymentDao deploymentDao = new DeploymentDao();

    public Deployment createDeployment(Long serverId, String applicationName, String version, Integer port, String configuration) {
        Deployment deployment = new Deployment();
        deployment.setServerId(serverId);
        deployment.setApplicationName(applicationName);
        deployment.setVersion(version);
        deployment.setPort(port);
        deployment.setConfiguration(configuration);
        deployment.setStatus(Deployment.DeploymentStatus.PENDING);

        return deploymentDao.saveOrUpdate(deployment);
    }

    public Deployment getDeployment(Long deploymentId) {
        return deploymentDao.findById(deploymentId);
    }

    public List<Deployment> getServerDeployments(Long serverId) {
        return deploymentDao.findByServerId(serverId);
    }

    public List<Deployment> getAllDeployments() {
        return deploymentDao.findAll();
    }

    public void activateDeployment(Long deploymentId) {
        Deployment deployment = deploymentDao.findById(deploymentId);
        if (deployment != null) {
            deployment.setStatus(Deployment.DeploymentStatus.ACTIVE);
            deployment.setDeployedAt(new Timestamp(System.currentTimeMillis()));
            deploymentDao.saveOrUpdate(deployment);
        }
    }

    public void stopDeployment(Long deploymentId) {
        Deployment deployment = deploymentDao.findById(deploymentId);
        if (deployment != null) {
            deployment.setStatus(Deployment.DeploymentStatus.STOPPED);
            deploymentDao.saveOrUpdate(deployment);
        }
    }

    public void deleteDeployment(Long deploymentId) {
        deploymentDao.delete(deploymentId);
    }
}

