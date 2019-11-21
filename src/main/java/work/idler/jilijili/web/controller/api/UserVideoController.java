package work.idler.jilijili.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import work.idler.jilijili.model.dto.JsonResult;
import work.idler.jilijili.model.entity.JiUser;
import work.idler.jilijili.model.entity.UserVideo;
import work.idler.jilijili.model.entity.VideoCategory;
import work.idler.jilijili.service.UserVideoService;

import java.util.List;

/**
 * UserVideoController
 *
 * @author isColt
 * @date 2019/10/31
 */
@RestController
@RequestMapping("/api/videos")
public class UserVideoController {

    @Autowired
    private UserVideoService userVideoService;

    @GetMapping
    public JsonResult list() {
        return new JsonResult(HttpStatus.OK.value(), userVideoService.listAll());
    }

    @GetMapping("{id}")
    public JsonResult getById(@PathVariable Integer id) {
        return new JsonResult(HttpStatus.OK.value(), userVideoService.getById(id));
    }

    @GetMapping("randThree")
    public JsonResult randThree() {
        return new JsonResult(HttpStatus.OK.value(), userVideoService.randThree());
    }

    @GetMapping("othersVideo/{id}")
    public JsonResult othersVideo(@PathVariable Integer id) {
        return new JsonResult(HttpStatus.OK.value(), userVideoService.othersVideo(id));
    }

    /**
     * 通过分类查
     *
     * @param cid
     * @return
     */
    @GetMapping("cid/{cid}")
    public JsonResult findByCategory(@PathVariable Integer cid) {
        if (cid == 1) {
            return new JsonResult(HttpStatus.OK.value(), userVideoService.listAll());
        }

        VideoCategory videoCategory = new VideoCategory();
        videoCategory.setId(cid);
        List<UserVideo> userVideos = userVideoService.findByCategory(videoCategory);

        if (userVideos == null || userVideos.isEmpty()) {
            return new JsonResult(HttpStatus.NO_CONTENT.value(), "此专栏还没有up上传视频");
        }
        return new JsonResult(HttpStatus.OK.value(), userVideos);
    }

    /**
     * 通过标题模糊查
     *
     * @param title
     * @return
     */
    @GetMapping("search/{title}")
    public JsonResult search(@PathVariable String title) {

        if (title == null || title.isEmpty()) {
            return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "没有搜索内容");
        }

        List<UserVideo> videos = userVideoService.findByTitleLike(title);

        if (videos.size() > 0) {
            return new JsonResult(HttpStatus.OK.value(), "为你搜索到以下内容~", userVideoService.findByTitleLike(title));
        }

        return new JsonResult(HttpStatus.OK.value(), "没有相关内容~");
    }
}
