package io.github.xyz.config;

import org.aeonbits.owner.ConfigCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import io.github.xyz.util.LogUtil;

public class ConfigurationManager {

    private static final Logger log = LogManager.getLogger(ConfigurationManager.class);

    private ConfigurationManager() {
    }

    public static Configuration configuration() {
        LogUtil.logMethodEntry();
        Configuration config = ConfigCache.getOrCreate(Configuration.class);

        LogUtil.logMethodExit();
        return config;
    }
}

