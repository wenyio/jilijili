package work.idler.jilijili.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import work.idler.jilijili.model.entity.VideoComment;
import work.idler.jilijili.repository.base.BaseRepository;

import java.util.List;

/**
 * VideoComment Repository
 *
 * @author isColt
 * @date 2019/11/2
 */
@CrossOrigin
@Repository
public interface VideoCommentRepository extends BaseRepository<VideoComment, Integer>, JpaSpecificationExecutor<VideoComment> {

    /**
     * 通过视频id 查询弹幕
     *
     * @param vid
     * @return
     */
    List<VideoComment> findByVid(Integer vid);
}
