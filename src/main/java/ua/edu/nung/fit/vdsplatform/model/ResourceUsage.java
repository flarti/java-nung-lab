package ua.edu.nung.fit.vdsplatform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "resource_usage")
public class ResourceUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "server_id", nullable = false)
    private Long serverId;

    @Column(name = "cpu_percent", nullable = false)
    private BigDecimal cpuPercent;

    @Column(name = "ram_percent", nullable = false)
    private BigDecimal ramPercent;

    @Column(name = "storage_percent", nullable = false)
    private BigDecimal storagePercent;

    @Column(name = "bandwidth_gb", nullable = false)
    private BigDecimal bandwidthGb;

    @Column(nullable = false)
    private Timestamp timestamp;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public Long getServerId() { return serverId; }
    public void setServerId(Long serverId) { this.serverId = serverId; }
    public BigDecimal getCpuPercent() { return cpuPercent; }
    public void setCpuPercent(BigDecimal cpuPercent) { this.cpuPercent = cpuPercent; }
    public BigDecimal getRamPercent() { return ramPercent; }
    public void setRamPercent(BigDecimal ramPercent) { this.ramPercent = ramPercent; }
    public BigDecimal getStoragePercent() { return storagePercent; }
    public void setStoragePercent(BigDecimal storagePercent) { this.storagePercent = storagePercent; }
    public BigDecimal getBandwidthGb() { return bandwidthGb; }
    public void setBandwidthGb(BigDecimal bandwidthGb) { this.bandwidthGb = bandwidthGb; }
    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
    public Timestamp getCreatedAt() { return createdAt; }
}

