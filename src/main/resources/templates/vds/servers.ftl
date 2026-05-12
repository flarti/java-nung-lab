<#import "../layout.ftl" as layout>

<@layout.layout>
    <div class="row">
        <div class="col-12">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h1 class="h3 mb-0"><i class="bi bi-server"></i> My Servers</h1>
                <button class="btn btn-primary" id="createServerBtn">
                    <i class="bi bi-plus-circle"></i> Create Server
                </button>
            </div>
        </div>
    </div>

    <div id="alertBox" class="alert alert-danger d-none mb-4" role="alert"></div>
    <div id="successBox" class="alert alert-success d-none mb-4" role="alert"></div>

    <div class="row">
        <div class="col-12">
            <div class="card shadow-sm border-0">
                <div class="table-responsive">
                    <table class="table table-sm mb-0" id="serversTable">
                        <thead class="table-light">
                            <tr>
                                <th>Name</th>
                                <th>IP Address</th>
                                <th>OS Type</th>
                                <th>CPU</th>
                                <th>RAM</th>
                                <th>Storage</th>
                                <th>Status</th>
                                <th>Price/Month</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody id="serversList">
                            <tr>
                                <td colspan="9" class="text-center text-muted py-4">
                                    <p>No servers found. <a href="#" id="createServerLink">Create one now</a></p>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Create Server Modal -->
    <div class="modal fade" id="createServerModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Create New Server</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form id="createServerForm">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="serverName" class="form-label">Server Name</label>
                            <input type="text" class="form-control" id="serverName" required>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="osType" class="form-label">Operating System</label>
                                <select class="form-select" id="osType" required>
                                    <option value="" disabled selected>Select OS...</option>
                                    <option value="UBUNTU_22">Ubuntu 22.04 LTS</option>
                                    <option value="UBUNTU_20">Ubuntu 20.04 LTS</option>
                                    <option value="DEBIAN_11">Debian 11</option>
                                    <option value="CENTOS_8">CentOS 8</option>
                                    <option value="WINDOWS_SERVER_2022">Windows Server 2022</option>
                                </select>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="cpuCores" class="form-label">CPU Cores</label>
                                <select class="form-select" id="cpuCores" required>
                                    <option value="">Select...</option>
                                    <option value="1">1 Core</option>
                                    <option value="2">2 Cores</option>
                                    <option value="4">4 Cores</option>
                                    <option value="8">8 Cores</option>
                                    <option value="16">16 Cores</option>
                                </select>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="ramGb" class="form-label">RAM (GB)</label>
                                <select class="form-select" id="ramGb" required>
                                    <option value="">Select...</option>
                                    <option value="2">2 GB</option>
                                    <option value="4">4 GB</option>
                                    <option value="8">8 GB</option>
                                    <option value="16">16 GB</option>
                                    <option value="32">32 GB</option>
                                    <option value="64">64 GB</option>
                                </select>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="storageGb" class="form-label">Storage (GB)</label>
                                <select class="form-select" id="storageGb" required>
                                    <option value="">Select...</option>
                                    <option value="50">50 GB</option>
                                    <option value="100">100 GB</option>
                                    <option value="250">250 GB</option>
                                    <option value="500">500 GB</option>
                                    <option value="1000">1 TB</option>
                                    <option value="2000">2 TB</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Create Server</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script type="module">
        <#noparse>
        const createServerBtn = document.getElementById("createServerBtn");
        const createServerLink = document.getElementById("createServerLink");
        const createServerModal = new bootstrap.Modal(document.getElementById("createServerModal"));
        const createServerForm = document.getElementById("createServerForm");
        const alertBox = document.getElementById("alertBox");
        const successBox = document.getElementById("successBox");
        const serversList = document.getElementById("serversList");

        let currentModal = null;

        function showError(message) {
            alertBox.textContent = message;
            alertBox.classList.remove("d-none");
            successBox.classList.add("d-none");
        }

        function showSuccess(message) {
            successBox.textContent = message;
            successBox.classList.remove("d-none");
            alertBox.classList.add("d-none");
        }

        function hideMessages() {
            alertBox.classList.add("d-none");
            successBox.classList.add("d-none");
        }

        async function loadServers() {
            try {
                const response = await fetch(window.appConfig.contextPath + "/vds/servers");
                const data = await response.json();

                if (!response.ok) {
                    showError(data.error || "Failed to load servers");
                    return;
                }

                if (data.servers && data.servers.length > 0) {
                    serversList.innerHTML = data.servers.map(server => `
                        <tr>
                            <td><strong>${server.name}</strong></td>
                            <td><code>${server.ipAddress || "Provisioning..."}</code></td>
                            <td><span class="badge text-bg-info">${server.osType}</span></td>
                            <td>${server.cpuCores} cores</td>
                            <td>${server.ramGb} GB</td>
                            <td>${server.storageGb} GB</td>
                            <td>
                                <span class="badge ${server.status === 'RUNNING' ? 'text-bg-success' : server.status === 'PROVISIONING' ? 'text-bg-warning' : 'text-bg-secondary'}">
                                    ${server.status}
                                </span>
                            </td>
                            <td>$${server.monthlyPrice}</td>
                            <td>
                                <button class="btn btn-sm btn-outline-primary" onclick="viewServer(${server.id})">View</button>
                                <button class="btn btn-sm btn-outline-danger" onclick="deleteServer(${server.id})">Delete</button>
                            </td>
                        </tr>
                    `).join("");
                } else {
                    serversList.innerHTML = `
                        <tr>
                            <td colspan="9" class="text-center text-muted py-4">
                                No servers found. <a href="#" onclick="createServerModal.show(); return false;">Create one now</a>
                            </td>
                        </tr>
                    `;
                }
            } catch (error) {
                showError("Error loading servers: " + error.message);
            }
        }

        function viewServer(serverId) {
            alert("View server " + serverId + " - Feature coming soon!");
        }

        async function deleteServer(serverId) {
            if (!confirm("Are you sure you want to delete this server?")) return;

            try {
                const response = await fetch(window.appConfig.contextPath + "/vds/servers/" + serverId, {
                    method: "DELETE"
                });

                const data = await response.json();

                if (!response.ok) {
                    showError(data.error || "Failed to delete server");
                    return;
                }

                showSuccess("Server deleted successfully");
                setTimeout(() => loadServers(), 1500);
            } catch (error) {
                showError("Error: " + error.message);
            }
        }

        createServerBtn.addEventListener("click", () => {
            hideMessages();
            createServerForm.reset();
            createServerModal.show();
        });

        createServerLink.addEventListener("click", (e) => {
            e.preventDefault();
            createServerBtn.click();
        });

        createServerForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            hideMessages();

            try {
                const payload = {
                    name: document.getElementById("serverName").value,
                    osType: document.getElementById("osType").value,
                    cpuCores: parseInt(document.getElementById("cpuCores").value),
                    ramGb: parseInt(document.getElementById("ramGb").value),
                    storageGb: parseInt(document.getElementById("storageGb").value)
                };

                const response = await fetch(window.appConfig.contextPath + "/vds/servers", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(payload)
                });

                const data = await response.json();

                if (!response.ok) {
                    showError(data.error || "Failed to create server");
                    return;
                }

                showSuccess("Server created successfully! ID: " + data.serverId);
                createServerModal.hide();
                setTimeout(() => loadServers(), 1500);
            } catch (error) {
                showError("Error: " + error.message);
            }
        });

        loadServers();
        window.viewServer = viewServer;
        window.deleteServer = deleteServer;
        </#noparse>
    </script>
</@layout.layout>

