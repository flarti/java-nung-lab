package ua.edu.nung.fit.vdsplatform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;

@Entity
@Table(name = "deployments")
public class Deployment {

    public enum DeploymentStatus {
        PENDING, DEPLOYING, ACTIVE, FAILED, STOPPED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "server_id", nullable = false)
    private Long serverId;

    @Column(name = "application_name", nullable = false, length = 255)
    private String applicationName;

    @Column(nullable = false, length = 100)
    private String version;

    @Column(nullable = false)
    private Integer port;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private DeploymentStatus status = DeploymentStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String configuration;

    @Column(name = "deployed_at")
    private Timestamp deployedAt;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

    // Getters and Setters
    public Long getId() { return id; }
    public Long getServerId() { return serverId; }
    public void setServerId(Long serverId) { this.serverId = serverId; }
    public String getApplicationName() { return applicationName; }
    public void setApplicationName(String applicationName) { this.applicationName = applicationName; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public Integer getPort() { return port; }
    public void setPort(Integer port) { this.port = port; }
    public DeploymentStatus getStatus() { return status; }
    public void setStatus(DeploymentStatus status) { this.status = status; }
    public String getConfiguration() { return configuration; }
    public void setConfiguration(String configuration) { this.configuration = configuration; }
    public Timestamp getDeployedAt() { return deployedAt; }
    public void setDeployedAt(Timestamp deployedAt) { this.deployedAt = deployedAt; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
}

