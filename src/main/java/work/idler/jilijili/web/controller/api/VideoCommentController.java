package work.idler.jilijili.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import work.idler.jilijili.model.dto.JsonResult;
import work.idler.jilijili.model.entity.VideoComment;
import work.idler.jilijili.service.VideoCommentService;

/**
 * VideoComment Controller
 *
 * @author isColt
 * @date 2019/11/2
 */
@RestController
@RequestMapping("/api/comments")
public class VideoCommentController {

    @Autowired
    private VideoCommentService videoCommentService;

    /**
     * 发送弹幕
     *
     * @param videoComment
     * @return
     */
    @PostMapping
    public JsonResult addComment(@RequestBody VideoComment videoComment) {
        VideoComment comment = videoCommentService.create(videoComment);
        return new JsonResult(HttpStatus.OK.value(), comment);
    }

    /**
     * 获取video 通过id
     * @param vid
     * @return
     */
    @GetMapping("/vid/{vid}")
    public JsonResult getByVideoId(@PathVariable Integer vid) {
        return new JsonResult(HttpStatus.OK.value(), videoCommentService.findByVid(vid));
    }
}
