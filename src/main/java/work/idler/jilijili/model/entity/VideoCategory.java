package work.idler.jilijili.model.entity;

import lombok.Data;
import work.idler.jilijili.model.base.BaseEntity;

import javax.persistence.*;

/**
 * Category Entity
 *
 * @author isColt
 * @date 2019/10/27
 */
@Data
@Entity
@Table(name = "ji_user_video_category")
public class VideoCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分类名
     */
    @Column(name = "name", columnDefinition = "varchar(50) not null")
    private String name;

    /**
     * 分类描述
     */
    @Column(name = "description", columnDefinition = "varchar(100) default ''")
    private String description;

    @Override
    protected void prePersist() {
        super.prePersist();

        id = null;

        if (description == null) {
            description = "";
        }
    }
}
