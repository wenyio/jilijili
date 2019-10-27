package work.idler.jilijili.model.entity;

import lombok.Data;
import work.idler.jilijili.model.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * User Entity
 *
 * @author isColt
 * @date 2019/10/26
 */
@Data
@Entity
@Table(name = "ji_user")
public class JiUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String account;

    @Column
    private String password;

    @Column
    private String email;

    /**
     * 用户名
     */
    @Column
    private String username;

    /**
     * 性别
     */
    @Column
    private String sex;

    /**
     * 生日
     */
    @Column
    private Date birthday;

    /**
     * 签名
     */
    @Column
    private String signature;

    /**
     * 积分
     */
    @Column
    private int integral;

    /**
     * 等级
     */
    @Column
    private int grade;

    /**
     * 头像
     */
    @Column
    private String icon;

    /**
     * 是否是up主
     */
    @Column
    private Boolean isUp;
}
