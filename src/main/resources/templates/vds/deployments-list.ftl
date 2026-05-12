<#import "../layout.ftl" as layout>

<@layout.layout>
    <div class="row">
        <div class="col-12">
            <h1 class="h3 mb-4"><i class="bi bi-cloud-upload"></i> Deployments</h1>
        </div>
    </div>

    <div id="alertBox" class="alert alert-danger d-none mb-4" role="alert"></div>
    <div id="successBox" class="alert alert-success d-none mb-4" role="alert"></div>

    <div class="row">
        <div class="col-12">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-light">
                    <h5 class="mb-0">Your Application Deployments</h5>
                </div>
                <div class="table-responsive">
                    <table class="table table-sm mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>Application</th>
                                <th>Server</th>
                                <th>Version</th>
                                <th>Port</th>
                                <th>Status</th>
                                <th>Deployed</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="7" class="text-center text-muted py-4">
                                    No deployments found. Deploy your first application to a server.
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>

