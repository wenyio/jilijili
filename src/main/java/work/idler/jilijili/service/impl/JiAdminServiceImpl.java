package work.idler.jilijili.service.impl;

import org.springframework.stereotype.Service;
import work.idler.jilijili.model.entity.JiAdmin;
import work.idler.jilijili.repository.base.BaseRepository;
import work.idler.jilijili.service.JiAdminService;
import work.idler.jilijili.service.base.AbstractCrudService;

/**
 * JiAdminServiceImpl
 *
 * @author isColt
 * @date 2019/10/27
 */
@Service
public class JiAdminServiceImpl extends AbstractCrudService<JiAdmin, Integer> implements JiAdminService {

    protected JiAdminServiceImpl(BaseRepository<JiAdmin, Integer> baseRepository) {
        super(baseRepository);
    }
}
