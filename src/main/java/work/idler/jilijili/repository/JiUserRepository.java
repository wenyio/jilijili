package work.idler.jilijili.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import work.idler.jilijili.model.entity.JiUser;
import work.idler.jilijili.repository.base.BaseRepository;

import java.util.List;

/**
 * This is description
 *
 * @author isColt
 * @date 2019/10/27
 */
@CrossOrigin
@Repository
public interface JiUserRepository extends BaseRepository<JiUser, Integer>, JpaSpecificationExecutor<JiUser> {

    /**
     * 判断是否重复邮箱
     *
     * @return
     */
    List<JiUser> findByEmail(String email);

    /**
     * 用于登录
     *
     * @param email
     * @param password
     * @return
     */
    JiUser findByEmailAndPassword(String email, String password);
}
