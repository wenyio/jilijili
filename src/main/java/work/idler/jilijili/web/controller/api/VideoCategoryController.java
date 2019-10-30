package work.idler.jilijili.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.idler.jilijili.model.dto.JsonResult;
import work.idler.jilijili.service.VideoCategoryService;

/**
 * VideoCategory
 *
 * @author isColt
 * @date 2019/10/30
 */
@RestController
@RequestMapping("/api/categories")
public class VideoCategoryController {

    @Autowired
    VideoCategoryService videoCategoryService;

    @GetMapping
    public JsonResult list() {
        return new JsonResult(HttpStatus.OK.value() ,videoCategoryService.listAll());
    }
}
