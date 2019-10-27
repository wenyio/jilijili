package work.idler.jilijili.model.entity;

import lombok.Data;
import work.idler.jilijili.model.base.BaseEntity;

import javax.persistence.*;

/**
 * Video Heat Entity
 *
 * @author isColt
 * @date 2019/10/27
 */
@Data
@Entity
@Table(name = "ji_user_video_hot")
public class VideoHot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer uid;

    @Column
    private Integer vid;

    @Column
    private boolean isGood;

    @Column
    private boolean isGiveJB;

    @Column
    private boolean isCollect;
}
