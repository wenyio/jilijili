package work.idler.jilijili.service;

import work.idler.jilijili.model.entity.JiUser;
import work.idler.jilijili.service.base.CrudService;

import java.util.List;

/**
 * JiAdminService
 *
 * @author isColt
 * @date 2019/10/27
 */
public interface JiUserService extends CrudService<JiUser, Integer> {

    /**
     * 查询是否重复邮箱
     *
     * @param email
     * @return
     */
    List<JiUser> findByEmail(String email);

    /**
     * 登录
     *
     * @param email
     * @param password
     * @return
     */
    JiUser findByEmailAndPassword(String email, String password);
}
