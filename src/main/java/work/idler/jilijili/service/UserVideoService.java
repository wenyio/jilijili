package work.idler.jilijili.service;

import work.idler.jilijili.model.entity.UserVideo;
import work.idler.jilijili.model.entity.VideoCategory;
import work.idler.jilijili.service.base.CrudService;

import java.util.List;

/**
 * JiAdminService
 *
 * @author isColt
 * @date 2019/10/27
 */
public interface UserVideoService extends CrudService<UserVideo, Integer> {

    /**
     * 随机取三条数据
     * @return
     */
    List<UserVideo> randThree();

    /**
     * 获取推荐视频
     * @param id
     * @return
     */
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
