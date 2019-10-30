package work.idler.jilijili.service.impl;

import org.springframework.stereotype.Service;
import work.idler.jilijili.model.entity.UserVideo;
import work.idler.jilijili.repository.base.BaseRepository;
import work.idler.jilijili.service.UserVideoService;
import work.idler.jilijili.service.base.AbstractCrudService;

/**
 * JiAdminServiceImpl
 *
 * @author isColt
 * @date 2019/10/27
 */
@Service
public class UserVideoServiceImpl extends AbstractCrudService<UserVideo, Integer> implements UserVideoService {

    protected UserVideoServiceImpl(BaseRepository<UserVideo, Integer> baseRepository) {
        super(baseRepository);
    }
}
