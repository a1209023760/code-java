package com.keqi.apihu.core.util;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 全局属性配置类(在程序中根据操作系统动态决定文件存储路径)
 *
 * @author keqi
 */
@AllArgsConstructor
@Component
public class GlobalPropertyUtil {

    private final Environment environment;

    /**
     * 根据当前程序运行所处的不同环境来决定使用哪个存储路径
     *
     * @return uploadPath路径
     */
    public String getUploadPath() {
        String linux = environment.getProperty("apihu-file-path.linux");
        String windows = environment.getProperty("apihu-file-path.windows");

        String osName = System.getProperty("os.name");

        return osName.startsWith("Windows") ? windows : linux;
    }

    /**
     * 获取配置文件的属性值
     *
     * @param key key
     * @return value
     */
    public String getProperty(String key) {
        return environment.getProperty(key);
    }
}
