package net.devopskb.sleeplog;

/**
 * Created by chen on 1/23/17.
 */

public class SleepRecord {

    private String timestamp = "";
    private String recordType = "SLEEP";
    private Boolean sleepWithTits = false;
    private Boolean cry = false;
    private Integer eat_cc = 0;

    public SleepRecord(String recordType, Boolean sleepWithTits, Boolean cry, Integer eat_cc, String timestamp) {
        this.recordType = recordType;
        this.sleepWithTits = sleepWithTits;
        this.cry = cry;
        this.eat_cc = eat_cc;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{'timestamp': '" + timestamp+
                "', 'record_type': '" +recordType+
                "', 'sleep_with_tits':'" + sleepWithTits+
                "', 'cried':'" +cry+
                "', 'eaten_cc': '" +eat_cc+ "'}";
    }
}
