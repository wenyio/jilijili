package work.idler.jilijili.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 首页轮播图, 随机从视频中选择三个
 *
 * @author isColt
 * @date 2019/10/31
 */
@Data
public class SwiperListDTO implements Serializable {

    private Integer id;

    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 预览图
     */
    private String icon;
}
