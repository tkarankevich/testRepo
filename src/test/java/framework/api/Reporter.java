package framework.api;

import framework.configurations.PropertiesResourceManager;

public class Reporter {

    private static PropertiesResourceManager manager = new PropertiesResourceManager("testrail");

    private APIClient apiClient;
    private String projectId;
    private Object runId;

    private static Reporter reporter;

    private Reporter() {
        this.apiClient = new APIClient(manager.getProperty("link"));
        this.projectId = manager.getProperty("projectid");
        this.apiClient.setUser(manager.getProperty("email"));
        this.apiClient.setPassword(manager.getProperty("password"));
    }

    public static Reporter getReporter() {
        if (reporter == null) {
            reporter = new Reporter();
        }
        return reporter;
    }

    public APIClient getApiClient() {
        return apiClient;
    }

    public String getProjectId() {
        return projectId;
    }

    public Object getRunId() {
        return runId;
    }

    public void setRunId(Object runId) {
        this.runId = runId;
    }
}
