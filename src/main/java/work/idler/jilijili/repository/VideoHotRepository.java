package work.idler.jilijili.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import work.idler.jilijili.model.entity.VideoHot;
import work.idler.jilijili.repository.base.BaseRepository;

/**
 * This is description
 *
 * @author isColt
 * @date 2019/10/27
 */
@CrossOrigin
@Repository
public interface VideoHotRepository extends BaseRepository<VideoHot, Integer>, JpaSpecificationExecutor<VideoHot> {
}
