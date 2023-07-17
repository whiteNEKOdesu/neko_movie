package neko.movie.nekomovievideo.service.impl;

import cn.hutool.json.JSONUtil;
import neko.movie.nekomoviecommonbase.utils.entity.Constant;
import neko.movie.nekomovievideo.entity.CategoryInfo;
import neko.movie.nekomovievideo.mapper.CategoryInfoMapper;
import neko.movie.nekomovievideo.service.CategoryInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品分类表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class CategoryInfoServiceImpl extends ServiceImpl<CategoryInfoMapper, CategoryInfo> implements CategoryInfoService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取层级商品分类信息
     */
    @Override
    public List<CategoryInfo> getLevelCategory() {
        String key = Constant.VIDEO_REDIS_PREFIX + "level_category";
        String cache = stringRedisTemplate.opsForValue().get(key);

        //缓存有数据
        if(cache != null){
            return JSONUtil.toList(JSONUtil.parseArray(cache), CategoryInfo.class);
        }

        List<CategoryInfo> categoryInfos = this.list();
        //找到父分类
        List<CategoryInfo> parentCategoryInfos = categoryInfos.stream().filter(Objects::nonNull)
                .filter(categoryInfo -> categoryInfo.getLevel().equals(0))
                .collect(Collectors.toList());

        //递归设置子分类信息
        List<CategoryInfo> result = parentCategoryInfos.stream().peek(categoryInfo -> categoryInfo.setChild(getLevelCategory(categoryInfo, categoryInfos)))
                .collect(Collectors.toList());
        //缓存无数据，查询存入缓存
        stringRedisTemplate.opsForValue().setIfAbsent(key,
                JSONUtil.toJsonStr(result),
                1000 * 60 * 60 * 5,
                TimeUnit.MILLISECONDS);

        return result;
    }

    /**
     * 递归设置子分类信息
     */
    private List<CategoryInfo> getLevelCategory(CategoryInfo root, List<CategoryInfo> all){
        return all.stream().filter(categoryInfo -> root.getCategoryId().equals(categoryInfo.getParentId()))
                .peek(categoryInfo -> {
                    List<CategoryInfo> todoChild = getLevelCategory(categoryInfo, all);
                    categoryInfo.setChild(!todoChild.isEmpty() || !categoryInfo.getLevel().equals(1) ? todoChild : null);
                }).collect(Collectors.toList());
    }
}
