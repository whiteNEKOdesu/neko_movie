package neko.movie.nekomoviemember.service;

import neko.movie.nekomoviecommonbase.utils.entity.ResultObject;
import neko.movie.nekomoviemember.entity.AdminInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomoviemember.vo.AdminInfoVo;
import neko.movie.nekomoviemember.vo.LogInVo;
import neko.movie.nekomoviemember.vo.NewAdminInfoVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
public interface AdminInfoService extends IService<AdminInfo> {
    ResultObject<AdminInfoVo> logIn(LogInVo vo, HttpServletRequest request);

    void newAdmin(NewAdminInfoVo vo);

    boolean userNameIsRepeat(String userName);
}
