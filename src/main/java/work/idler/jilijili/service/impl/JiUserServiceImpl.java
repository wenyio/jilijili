package work.idler.jilijili.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import work.idler.jilijili.model.entity.JiUser;
import work.idler.jilijili.repository.JiUserRepository;
import work.idler.jilijili.repository.base.BaseRepository;
import work.idler.jilijili.service.JiUserService;
import work.idler.jilijili.service.base.AbstractCrudService;

import java.util.List;

/**
 * JiAdminServiceImpl
 *
 * @author isColt
 * @date 2019/10/27
 */
@Service
public class JiUserServiceImpl extends AbstractCrudService<JiUser, Integer> implements JiUserService {

    @Autowired
    private JiUserRepository jiUserRepository;

    protected JiUserServiceImpl(BaseRepository<JiUser, Integer> baseRepository) {
        super(baseRepository);
    }

    @Override
    public List<JiUser> findByEmail(String email) {
        return jiUserRepository.findByEmail(email);
    }

    @Override
    public JiUser findByEmailAndPassword(String email, String password) {
        return jiUserRepository.findByEmailAndPassword(email, DigestUtils.md5DigestAsHex(password.getBytes()));
    }
}
