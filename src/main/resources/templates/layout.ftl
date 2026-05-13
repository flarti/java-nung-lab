<#-- Base layout template -->
<#macro layout>
    <!DOCTYPE html>
    <html lang="uk">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${title!"VDS Platform"}</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet"
              crossorigin="anonymous">
        <script>
            window.appConfig = {
                contextPath: "${contextPath!''}",

                firebaseConfig: {
                    apiKey: "${firebaseWebApiKey!''}",
                    authDomain: "${firebaseWebAuthDomain!''}",
                    projectId: "${firebaseWebProjectId!''}",
                    storageBucket: "${firebaseWebStorageBucket!''}",
                    messagingSenderId: "${firebaseWebMessagingSenderId!''}",
                    appId: "${firebaseWebAppId!''}",
                    measurementId: "${firebaseWebMeasurementId!''}"
                },

                auth: {
                    isAuthenticated: <#if isAuthenticated?? && isAuthenticated>true<#else>false</#if>,
                    userId: "${currentUserId!''}",
                    email: "${currentUserEmail!''}",
                    displayName: "${currentDisplayName!''}",
                    role: "${currentUserRole!''}",
                    firebaseUid: "${currentFirebaseUid!''}",
                    provider: "${currentAuthProvider!''}",
                    emailVerified: <#if currentEmailVerified?? && currentEmailVerified>true<#else>false</#if>,
                    enabled: <#if currentUserEnabled?? && currentUserEnabled>true<#else>false</#if>,
                    photoUrl: "${currentUserPhotoUrl!''}"
                }
            };
        </script>
    </head>

    <body class="bg-light">

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${contextPath!""}/dashboard">🖥️ VDS</a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navBar">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navBar">

                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${contextPath!""}/dashboard">
                            <i class="bi bi-house-fill"></i> Dashboard
                        </a>
                    </li>
                    <#if isAuthenticated?? && isAuthenticated>
                        <li class="nav-item">
                            <a class="nav-link" href="${contextPath!""}/dashboard/servers">
                                <i class="bi bi-server"></i> Servers
                            </a>
                        </li>
                    </#if>
                </ul>

                <ul class="navbar-nav ms-auto">

                    <#if isAuthenticated?? && isAuthenticated>

                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="userMenu" role="button" data-bs-toggle="dropdown">
                                <i class="bi bi-person-circle"></i> ${currentDisplayName!currentUserEmail!"User"}
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userMenu">
                                <li><a class="dropdown-item" href="${contextPath!""}/user/profile">Profile</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><button id="logoutBtn" class="dropdown-item" style="border: none; background: none; cursor: pointer;">
                                    Logout
                                </button></li>
                            </ul>
                        </li>

                    <#else>

                        <li class="nav-item">
                            <a class="nav-link" href="${contextPath!""}/user/login">
                                <i class="bi bi-box-arrow-in-right"></i> Login
                            </a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="${contextPath!""}/user/register">
                                <i class="bi bi-person-plus"></i> Register
                            </a>
                        </li>

                    </#if>

                </ul>
            </div>
        </div>
    </nav>

    <main class="container py-4">
        <#nested>
    </main>

    <footer class="border-top py-3 bg-white mt-5">
        <div class="container text-muted small">
            <div class="row">
                <div class="col-md-6">
                    VDS Platform © ${.now?string("yyyy")} - Virtual Dedicated Server Solutions
                </div>
                <div class="col-md-6 text-end">
                    <a href="#" class="text-muted text-decoration-none">Privacy</a> |
                    <a href="#" class="text-muted text-decoration-none">Terms</a> |
                    <a href="#" class="text-muted text-decoration-none">Support</a>
                </div>
            </div>
        </div>
    </footer>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            crossorigin="anonymous"></script>

    <#-- 🔐 Logout logic -->
    <#if isAuthenticated?? && isAuthenticated>
        <script type="module">
            import { initializeApp } from "https://www.gstatic.com/firebasejs/11.6.1/firebase-app.js";
            import { getAuth, signOut } from "https://www.gstatic.com/firebasejs/11.6.1/firebase-auth.js";

            const app = initializeApp(window.appConfig.firebaseConfig);
            const auth = getAuth(app);

            const logoutBtn = document.getElementById("logoutBtn");

            if (logoutBtn) {
                logoutBtn.addEventListener("click", async () => {

                    try {
                        await signOut(auth);
                    } catch (e) {
                        console.error("Firebase logout failed", e);
                    }

                    await fetch(window.appConfig.contextPath + "/auth/logout", {
                        method: "POST"
                    });

                    window.location.href = window.appConfig.contextPath + "/user/login";
                });
            }
        </script>
    </#if>

    </body>
    </html>
</#macro>