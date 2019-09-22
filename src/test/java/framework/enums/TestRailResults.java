package framework.enums;

public enum TestRailResults {
    UNTESTED (0),
    PASSED (1),
    BLOCKED(2),
    RETEST(4),
    FAILED(5);

    private int caseStatus;

    TestRailResults(int caseStatus){
        this.caseStatus = caseStatus;
    }

    public int getCaseStatus() {
        return caseStatus;
    }
}
