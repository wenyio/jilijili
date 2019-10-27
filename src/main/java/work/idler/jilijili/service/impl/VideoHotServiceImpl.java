package work.idler.jilijili.service.impl;

import work.idler.jilijili.model.entity.VideoHot;
import work.idler.jilijili.repository.base.BaseRepository;
import work.idler.jilijili.service.VideoHotService;
import work.idler.jilijili.service.base.AbstractCrudService;

/**
 * JiAdminServiceImpl
 *
 * @author isColt
 * @date 2019/10/27
 */
public class VideoHotServiceImpl extends AbstractCrudService<VideoHot, Integer> implements VideoHotService {

    protected VideoHotServiceImpl(BaseRepository<VideoHot, Integer> baseRepository) {
        super(baseRepository);
    }
}
