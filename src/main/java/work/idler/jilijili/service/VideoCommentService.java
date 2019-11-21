package work.idler.jilijili.service;

import work.idler.jilijili.model.entity.VideoComment;
import work.idler.jilijili.service.base.CrudService;

import java.util.List;

/**
 * This is description
 *
 * @author isColt
 * @date 2019/11/2
 */
public interface VideoCommentService extends CrudService<VideoComment, Integer> {

    /**
     * 通过视频id 查询弹幕
     *
     * @param vid
     * @return
     */
    List<VideoComment> findByVid(Integer vid);
}
