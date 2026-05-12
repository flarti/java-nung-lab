<#import "layout.ftl" as layout>

<@layout.layout>
    <div class="hero-section">
        <div class="container">
            <h1 class="display-4 fw-bold mb-3">Welcome to VDS Platform 🚀</h1>
            <p class="lead">Manage your virtual dedicated servers with ease and confidence</p>
        </div>
    </div>

    <div class="container">
        <#if isAuthenticated?? && isAuthenticated>
            <div class="row">
                <div class="col-md-3 mb-4">
                    <div class="card shadow-sm border-0 h-100">
                        <div class="card-body text-center">
                            <div class="display-4 text-primary mb-3">
                                <i class="bi bi-server"></i>
                            </div>
                            <h5 class="card-title">Your Servers</h5>
                            <p class="card-text text-muted">Manage and monitor your VDS instances</p>
                            <a href="${contextPath!''}/dashboard/servers" class="btn btn-primary">View Servers</a>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 mb-4">
                    <div class="card shadow-sm border-0 h-100">
                        <div class="card-body text-center">
                            <div class="display-4 text-info mb-3">
                                <i class="bi bi-speedometer2"></i>
                            </div>
                            <h5 class="card-title">Resources</h5>
                            <p class="card-text text-muted">Monitor CPU, RAM, and storage usage</p>
                            <a href="${contextPath!''}/dashboard/resources" class="btn btn-info text-white">View Resources</a>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 mb-4">
                    <div class="card shadow-sm border-0 h-100">
                        <div class="card-body text-center">
                            <div class="display-4 text-success mb-3">
                                <i class="bi bi-cloud-upload"></i>
                            </div>
                            <h5 class="card-title">Deployments</h5>
                            <p class="card-text text-muted">Deploy and manage applications</p>
                            <a href="${contextPath!''}/dashboard/deployments" class="btn btn-success">View Deployments</a>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 mb-4">
                    <div class="card shadow-sm border-0 h-100">
                        <div class="card-body text-center">
                            <div class="display-4 text-warning mb-3">
                                <i class="bi bi-person-circle"></i>
                            </div>
                            <h5 class="card-title">Profile</h5>
                            <p class="card-text text-muted">Manage your account settings</p>
                            <a href="${contextPath!""}/user/profile" class="btn btn-warning btn-sm">View Profile</a>
                        </div>
                    </div>
                </div>
            </div>

            <hr class="my-5">

            <div class="row">
                <div class="col-md-8">
                    <div class="card shadow-sm border-0">
                        <div class="card-header bg-light">
                            <h5 class="mb-0"><i class="bi bi-info-circle"></i> Quick Start</h5>
                        </div>
                        <div class="card-body">
                            <ol>
                                <li class="mb-2">
                                    <strong>Create a Server:</strong> Click "Your Servers" to provision your first virtual dedicated server.
                                </li>
                                <li class="mb-2">
                                    <strong>Configure:</strong> Choose your OS, CPU cores, RAM, and storage size.
                                </li>
                                <li class="mb-2">
                                    <strong>Deploy:</strong> Upload and deploy your applications to the server.
                                </li>
                                <li class="mb-2">
                                    <strong>Monitor:</strong> Track resource usage and server performance in real-time.
                                </li>
                            </ol>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card shadow-sm border-0 bg-light">
                        <div class="card-header">
                            <h5 class="mb-0"><i class="bi bi-bookmark-star"></i> Features</h5>
                        </div>
                        <div class="card-body">
                            <ul class="list-unstyled">
                                <li class="mb-2"><i class="bi bi-check-circle-fill text-success"></i> 99.9% Uptime SLA</li>
                                <li class="mb-2"><i class="bi bi-check-circle-fill text-success"></i> Auto-scaling</li>
                                <li class="mb-2"><i class="bi bi-check-circle-fill text-success"></i> Full Root Access</li>
                                <li class="mb-2"><i class="bi bi-check-circle-fill text-success"></i> 24/7 Support</li>
                                <li class="mb-2"><i class="bi bi-check-circle-fill text-success"></i> Secure Backups</li>
                                <li class="mb-2"><i class="bi bi-check-circle-fill text-success"></i> DDoS Protection</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        <#else>
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="card shadow-lg border-0">
                        <div class="card-body p-5">
                            <h2 class="card-title text-center mb-4">Welcome to VDS Platform</h2>
                            <p class="text-center text-muted mb-4">
                                The most reliable Virtual Dedicated Server platform. Deploy, manage, and scale your infrastructure with ease.
                            </p>

                            <div class="row mb-4">
                                <div class="col-md-4 text-center mb-3">
                                    <div class="mb-2">
                                        <i class="bi bi-lightning-charge-fill text-warning" style="font-size: 2rem;"></i>
                                    </div>
                                    <h6>Lightning Fast</h6>
                                    <small class="text-muted">SSD storage with blazing fast performance</small>
                                </div>
                                <div class="col-md-4 text-center mb-3">
                                    <div class="mb-2">
                                        <i class="bi bi-shield-check text-success" style="font-size: 2rem;"></i>
                                    </div>
                                    <h6>Secure</h6>
                                    <small class="text-muted">Enterprise-grade security and DDoS protection</small>
                                </div>
                                <div class="col-md-4 text-center mb-3">
                                    <div class="mb-2">
                                        <i class="bi bi-graph-up text-info" style="font-size: 2rem;"></i>
                                    </div>
                                    <h6>Scalable</h6>
                                    <small class="text-muted">Easy scaling to match your needs</small>
                                </div>
                            </div>

                            <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                                <a href="${contextPath!""}/user/login" class="btn btn-primary btn-lg px-4">Get Started</a>
                                <a href="${contextPath!""}/user/register" class="btn btn-outline-secondary btn-lg px-4">Create Account</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </#if>
    </div>
</@layout.layout>

