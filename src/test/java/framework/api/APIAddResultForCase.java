package framework.api;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class APIAddResultForCase {

    public APIAddResultForCase() {
    }

    public static JSONObject addResultForCase(Object runId, int caseId, int result, String comment) throws IOException, APIException {
        Reporter reporter = Reporter.getReporter();
        Map<String, Object> data = new HashMap();
        data.put("status_id", result);
        data.put("comment", comment);
        JSONObject response = (JSONObject) reporter.getApiClient().sendPost(String.format("add_result_for_case/%s/%s", runId, caseId), data);
        return response;
    }
}
