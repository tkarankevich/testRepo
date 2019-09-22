package framework.api;

import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.*;

public class APICreateRun {

    public APICreateRun() {
    }

    public static JSONObject createTestRun() throws IOException, APIException {
        Reporter reporter = Reporter.getReporter();
        List<Integer> caseIds = new ArrayList<>();
        caseIds.add(1);
        Map<String, Object> data = new HashMap();
        data.put("name", String.format("TestRun%s", new Random().nextInt(100)));
        data.put("include_all", false);
        data.put("case_ids", caseIds);

        JSONObject response = (JSONObject) reporter.getApiClient().sendPost(String.format("add_run/%s", reporter.getProjectId()), data);
        return response;
    }
}
