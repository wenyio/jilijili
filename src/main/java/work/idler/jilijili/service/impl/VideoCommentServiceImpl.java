package work.idler.jilijili.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.idler.jilijili.model.entity.VideoComment;
import work.idler.jilijili.repository.VideoCommentRepository;
import work.idler.jilijili.repository.base.BaseRepository;
import work.idler.jilijili.service.VideoCommentService;
import work.idler.jilijili.service.base.AbstractCrudService;

import java.util.List;

/**
 * This is description
 *
 * @author isColt
 * @date 2019/11/2
 */
@Service
public class VideoCommentServiceImpl extends AbstractCrudService<VideoComment, Integer> implements VideoCommentService {

    @Autowired
    private VideoCommentRepository videoCommentRepository;

    protected VideoCommentServiceImpl(BaseRepository<VideoComment, Integer> baseRepository) {
        super(baseRepository);
    }

    @Override
    public List<VideoComment> findByVid(Integer vid) {
        return videoCommentRepository.findByVid(vid);
    }
}
