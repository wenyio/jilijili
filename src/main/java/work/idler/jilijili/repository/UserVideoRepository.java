package work.idler.jilijili.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import work.idler.jilijili.model.entity.UserVideo;
import work.idler.jilijili.repository.base.BaseRepository;

/**
 * This is description
 *
 * @author isColt
 * @date 2019/10/27
 */
public interface UserVideoRepository extends BaseRepository<UserVideo, Integer>, JpaSpecificationExecutor<UserVideo> {
}
