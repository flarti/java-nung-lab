<#import "../layout.ftl" as layout>

<@layout.layout>
    <div class="row">
        <div class="col-12">
            <h1 class="h3 mb-4"><i class="bi bi-speedometer2"></i> Resource Monitoring</h1>
        </div>
    </div>

    <div class="row mb-4">
        <div class="col-md-6 col-lg-3">
            <div class="card shadow-sm border-0">
                <div class="card-body">
                    <div class="text-muted small mb-2">Average CPU Usage</div>
                    <div class="display-6">--</div>
                    <small class="text-muted">Last 24 hours</small>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-3">
            <div class="card shadow-sm border-0">
                <div class="card-body">
                    <div class="text-muted small mb-2">Average RAM Usage</div>
                    <div class="display-6">--</div>
                    <small class="text-muted">Last 24 hours</small>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-3">
            <div class="card shadow-sm border-0">
                <div class="card-body">
                    <div class="text-muted small mb-2">Total Bandwidth</div>
                    <div class="display-6">--</div>
                    <small class="text-muted">Last 24 hours</small>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-3">
            <div class="card shadow-sm border-0">
                <div class="card-body">
                    <div class="text-muted small mb-2">Storage Usage</div>
                    <div class="display-6">--</div>
                    <small class="text-muted">Current</small>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-12">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-light">
                    <h5 class="mb-0">Server Resources</h5>
                </div>
                <div class="table-responsive">
                    <table class="table table-sm mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>Server</th>
                                <th>CPU %</th>
                                <th>RAM %</th>
                                <th>Storage %</th>
                                <th>Bandwidth (GB)</th>
                                <th>Timestamp</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="6" class="text-center text-muted py-4">
                                    No resource data available.
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>

