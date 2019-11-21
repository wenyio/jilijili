package work.idler.jilijili.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.idler.jilijili.model.entity.UserVideo;
import work.idler.jilijili.model.entity.VideoCategory;
import work.idler.jilijili.repository.UserVideoRepository;
import work.idler.jilijili.repository.base.BaseRepository;
import work.idler.jilijili.service.UserVideoService;
import work.idler.jilijili.service.base.AbstractCrudService;

import java.util.List;

/**
 * JiAdminServiceImpl
 *
 * @author isColt
 * @date 2019/10/27
 */
@Service
public class UserVideoServiceImpl extends AbstractCrudService<UserVideo, Integer> implements UserVideoService {

    @Autowired
    UserVideoRepository userVideoRepository;

    protected UserVideoServiceImpl(BaseRepository<UserVideo, Integer> baseRepository) {
        super(baseRepository);
    }

    @Override
    public List<UserVideo> randThree() {
        return userVideoRepository.randThree();
    }

    @Override
    public List<UserVideo> othersVideo(Integer id) {
        return userVideoRepository.othersVideo(id);
    }

    @Override
    public List<UserVideo> findByCategory(VideoCategory videoCategory) {
        return userVideoRepository.findByCategory(videoCategory);
    }

    @Override
    public List<UserVideo> findByTitleLike(String title) {
        return userVideoRepository.findByTitleLike("%" + title + "%");
    }
}
