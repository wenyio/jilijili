package work.idler.jilijili.model.entity;

import lombok.Data;
import work.idler.jilijili.model.base.BaseEntity;

import javax.persistence.*;

/**
 * Admin Entity
 *
 * @author isColt
 * @date 2019/10/27
 */
@Data
@Entity
@Table(name = "ji_admin")
public class JiAdmin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String account;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String icon;
}
