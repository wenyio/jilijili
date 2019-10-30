package work.idler.jilijili.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import work.idler.jilijili.model.entity.JiAdmin;
import work.idler.jilijili.repository.base.BaseRepository;

/**
 * JiAdminRepository
 *
 * @author isColt
 * @date 2019/10/27
 */
@Repository
public interface JiAdminRepository extends BaseRepository<JiAdmin, Integer>, JpaSpecificationExecutor<JiAdmin> {

}
