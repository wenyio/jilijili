package work.idler.jilijili;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;
import work.idler.jilijili.model.entity.JiAdmin;
import work.idler.jilijili.model.entity.VideoCategory;
import work.idler.jilijili.service.JiAdminService;
import work.idler.jilijili.service.VideoCategoryService;

@SpringBootTest
class JilijiliApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    VideoCategoryService videoCategoryService;

    @Autowired
    JiAdminService jiAdminService;

    @Test
    public void addCategory() {
        VideoCategory videoCategory = new VideoCategory();
        videoCategory.setName("首页");
        videoCategoryService.create(videoCategory);
    }

    @Test
    public void addAdmin() {
        JiAdmin jiAdmin = new JiAdmin();
        jiAdmin.setAccount("admin");
        jiAdmin.setEmail("iscolt@qq.com");
        jiAdmin.setPassword(DigestUtils.md5DigestAsHex("admin".getBytes()));
        jiAdminService.create(jiAdmin);
    }

}
