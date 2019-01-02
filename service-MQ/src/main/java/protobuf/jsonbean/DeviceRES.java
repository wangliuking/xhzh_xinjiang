package protobuf.jsonbean;

public class DeviceRES {
    private String RespResult;
    private String callId;
    private String rtuId ;
    private DBInfo DBInfo;

    public String getRespResult() {
        return RespResult;
    }

    public void setRespResult(String respResult) {
        RespResult = respResult;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getRtuId() {
        return rtuId;
    }

    public void setRtuId(String rtuId) {
        this.rtuId = rtuId;
    }

    public protobuf.jsonbean.DBInfo getDBInfo() {
        return DBInfo;
    }

    public void setDBInfo(protobuf.jsonbean.DBInfo DBInfo) {
        this.DBInfo = DBInfo;
    }

    @Override
    public String toString() {
        return "DeviceRES{" +
                "RespResult='" + RespResult + '\'' +
                ", callId='" + callId + '\'' +
                ", rtuId='" + rtuId + '\'' +
                ", DBInfo=" + DBInfo +
                '}';
    }
}
