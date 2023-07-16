package neko.movie.nekomoviegateway.config;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 权限设置配置
 */
@Component
public class SAWeightConfig implements StpInterface {
    @Override
    public List<String> getPermissionList(Object o, String s) {
        return Collections.emptyList();
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return Collections.emptyList();
    }
}
