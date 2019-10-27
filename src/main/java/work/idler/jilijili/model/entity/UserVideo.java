package work.idler.jilijili.model.entity;

import lombok.Data;
import work.idler.jilijili.model.base.BaseEntity;

import javax.persistence.*;

/**
 * Video Entity
 *
 * @author isColt
 * @date 2019/10/27
 */
@Data
@Entity
@Table(name = "ji_user_video")
public class UserVideo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "uid")
    private JiUser user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private VideoCategory category;

    @Column
    private String title;

    /**
     * 描述
     */
    @Column
    private String description;

    /**
     * 预览图
     */
    @Column
    private String icon;

    /**
     * 地址
     */
    @Column
    private String url;

    /**
     * 点赞数
     */
    @Column
    private int numOfGood;

    /**
     * 投币数
     */
    @Column
    private int numOfJB;

    /**
     * 收藏数
     */
    @Column
    private int numOfCollect;
}
