package run.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UDPConf {
    @Value("${myConf.appPath}")
    private String appPath;

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    @Override
    public String toString() {
        return "UDPConf{" +
                "appPath='" + appPath + '\'' +
                '}';
    }
}
