package work.idler.jilijili.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Base Repository
 *
 * @author iscolt
 * @date 2019/07/22
 */
/*
    @NoRepositoryBean
        将扩展存储库的接口排除在实例化为存储库实例之外
        标示 封装的repository
 */
@NoRepositoryBean
public interface BaseRepository<DOMAIN, ID> extends JpaRepository<DOMAIN, ID> {

    /**
     * 根据删除状态查询
     *
     * @param deleted
     * @return
     */
    List<DOMAIN> findAllByDeleted(boolean deleted);
}