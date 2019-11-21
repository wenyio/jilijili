package work.idler.jilijili.model.entity;

import lombok.Data;
import org.apache.catalina.User;
import work.idler.jilijili.model.base.BaseEntity;

import javax.persistence.*;

/**
 * This is description
 *
 * @author isColt
 * @date 2019/11/2
 */
@Data
@Entity
@Table(name = "ji_user_video_comment")
public class VideoComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 评论内容
     */
    @Column
    private String text;

    @Column
    private String color;

    @Column
    private String time;
    /**
     * 评论属于那个视频
     */
    @Column
    private Integer vid;

    /**
     * 谁的评论
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "from_uid")
    private JiUser user;

//    /**
//     * 回复给谁
//     */
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "to_uid")
//    private JiUser forUid;
}
