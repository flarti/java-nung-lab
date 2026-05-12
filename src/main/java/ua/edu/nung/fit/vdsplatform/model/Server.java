package ua.edu.nung.fit.vdsplatform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "servers")
public class Server {

    public enum ServerStatus {
        PROVISIONING, RUNNING, SUSPENDED, TERMINATED
    }

    public enum OSType {
        UBUNTU_20, UBUNTU_22, DEBIAN_11, CENTOS_7, CENTOS_8, WINDOWS_SERVER_2019, WINDOWS_SERVER_2022
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(nullable = false, unique = true, length = 255)
    private String name;

    @Column(name = "ip_address", unique = true, length = 45)
    private String ipAddress;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ServerStatus status = ServerStatus.PROVISIONING;

    @Column(name = "os_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private OSType osType;

    @Column(name = "cpu_cores", nullable = false)
    private Integer cpuCores;

    @Column(name = "ram_gb", nullable = false)
    private Integer ramGb;

    @Column(name = "storage_gb", nullable = false)
    private Integer storageGb;

    @Column(name = "monthly_price", nullable = false)
    private BigDecimal monthlyPrice;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

    public Long getId() { return id; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public ServerStatus getStatus() { return status; }
    public void setStatus(ServerStatus status) { this.status = status; }
    public OSType getOsType() { return osType; }
    public void setOsType(OSType osType) { this.osType = osType; }
    public Integer getCpuCores() { return cpuCores; }
    public void setCpuCores(Integer cpuCores) { this.cpuCores = cpuCores; }
    public Integer getRamGb() { return ramGb; }
    public void setRamGb(Integer ramGb) { this.ramGb = ramGb; }
    public Integer getStorageGb() { return storageGb; }
    public void setStorageGb(Integer storageGb) { this.storageGb = storageGb; }
    public BigDecimal getMonthlyPrice() { return monthlyPrice; }
    public void setMonthlyPrice(BigDecimal monthlyPrice) { this.monthlyPrice = monthlyPrice; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
}

