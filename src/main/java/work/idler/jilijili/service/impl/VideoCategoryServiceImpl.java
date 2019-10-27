package work.idler.jilijili.service.impl;

import work.idler.jilijili.model.entity.VideoCategory;
import work.idler.jilijili.repository.base.BaseRepository;
import work.idler.jilijili.service.VideoCategoryService;
import work.idler.jilijili.service.base.AbstractCrudService;

/**
 * JiAdminServiceImpl
 *
 * @author isColt
 * @date 2019/10/27
 */
public class VideoCategoryServiceImpl extends AbstractCrudService<VideoCategory, Integer> implements VideoCategoryService {

    protected VideoCategoryServiceImpl(BaseRepository<VideoCategory, Integer> baseRepository) {
        super(baseRepository);
    }
}
