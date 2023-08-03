package org.cubewhy.lunarcn;

import com.google.gson.JsonPrimitive;
import org.cubewhy.lunarcn.files.Config;

import java.io.File;

public class ProxyConfig extends Config {
    public ProxyConfig(String configFilePath) {
        super(configFilePath);
    }

    public ProxyConfig(File configFile) {
        super(configFile);
    }

    public void init() {
        if (!getConfig().has("proxy-address")) {
            setProxyAutomatically();
        } else {
            ProxyManager.getInstance().setAddress(getConfig().get("proxy-address").getAsString());
        }
    }

    @Override
    public void load() {
        super.load();
        init(); // init values
    }

    /**
     * Automatically set proxy
     */
    public void setProxyAutomatically() {
        getConfig().add("proxy-address", new JsonPrimitive(ProxyManager.getInstance().getProxyAddress()));
        save();
    }
}
