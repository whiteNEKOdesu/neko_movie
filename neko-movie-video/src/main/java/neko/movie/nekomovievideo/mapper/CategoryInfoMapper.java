package neko.movie.nekomovievideo.mapper;

import neko.movie.nekomovievideo.entity.CategoryInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 影视分类表 Mapper 接口
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Mapper
public interface CategoryInfoMapper extends BaseMapper<CategoryInfo> {
    int deleteLeafCategoryInfo(Integer categoryId);
}
