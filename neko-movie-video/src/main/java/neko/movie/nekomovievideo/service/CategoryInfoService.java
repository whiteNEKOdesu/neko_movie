package neko.movie.nekomovievideo.service;

import neko.movie.nekomovievideo.entity.CategoryInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import neko.movie.nekomovievideo.vo.NewCategoryInfoVo;

import java.util.List;

/**
 * <p>
 * 影视分类表 服务类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
public interface CategoryInfoService extends IService<CategoryInfo> {
    List<CategoryInfo> getLevelCategory();

    void newCategoryInfo(NewCategoryInfoVo vo);

    void deleteLeafCategoryInfo(Integer categoryId);
}
