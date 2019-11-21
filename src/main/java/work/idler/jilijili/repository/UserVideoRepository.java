package work.idler.jilijili.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import work.idler.jilijili.model.entity.UserVideo;
import work.idler.jilijili.model.entity.VideoCategory;
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
public interface UserVideoRepository extends BaseRepository<UserVideo, Integer>, JpaSpecificationExecutor<UserVideo> {

    /**
     * 随机取出3条数据
     * TODO * 后期需要修改
     * @return
     */
    @Query(value = "SELECT * FROM ji_user_video  ORDER BY  RAND() LIMIT 3", nativeQuery = true)
    List<UserVideo> randThree();

    /**
     * 获取此视频外其他视频(当作推荐视频)
     * TODO * 后期需要修改
     * @return
     */
    @Query(value = "SELECT * FROM ji_user_video where id != :id ORDER BY RAND()", nativeQuery = true)
    List<UserVideo> othersVideo(Integer id);

    /**
     * 通过分类查询
     *
     * @param videoCategory
     * @return
     */
    List<UserVideo> findByCategory(VideoCategory videoCategory);

    /**
     * 通过标题模糊查询
     *
     * @param title
     * @return
     */
    List<UserVideo> findByTitleLike(String title);
}
