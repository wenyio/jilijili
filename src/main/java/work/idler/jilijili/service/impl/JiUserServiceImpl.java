package work.idler.jilijili.service.impl;

import work.idler.jilijili.model.entity.JiUser;
import work.idler.jilijili.repository.base.BaseRepository;
import work.idler.jilijili.service.JiUserService;
import work.idler.jilijili.service.base.AbstractCrudService;

/**
 * JiAdminServiceImpl
 *
 * @author isColt
 * @date 2019/10/27
 */
public class JiUserServiceImpl extends AbstractCrudService<JiUser, Integer> implements JiUserService {

    protected JiUserServiceImpl(BaseRepository<JiUser, Integer> baseRepository) {
        super(baseRepository);
    }
}
