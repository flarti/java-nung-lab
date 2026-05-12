<#import "../layout.ftl" as layout>

<@layout.layout>
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-6">
            <div class="card shadow-sm">
                <div class="card-body p-4 text-center">
                    <#if messageType!"info" == "success">
                        <div class="alert alert-success mb-4">
                            ${message!"Operation completed successfully."}
                        </div>
                    <#elseif messageType!"info" == "error">
                        <div class="alert alert-danger mb-4">
                            ${message!"An error occurred."}
                        </div>
                    <#else>
                        <div class="alert alert-info mb-4">
                            ${message!"Information message."}
                        </div>
                    </#if>

                    <a href="${redirectUrl!contextPath + '/dashboard'}" class="btn btn-primary">
                        ${redirectLabel!"Continue"}
                    </a>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>