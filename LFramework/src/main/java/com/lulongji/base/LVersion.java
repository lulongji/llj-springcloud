package com.lulongji.base;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class LVersion {

    public LVersion(String version) {
        log.info("Startup complete, current version ：" + version);
    }
}
