package work.idler.jilijili.model.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import work.idler.jilijili.common.utils.DateUtil;

import javax.persistence.*;
import java.util.Date;

/**
 * Base Entity
 *
 * @author iscolt
 * @date 2019/07/21
 */

/*
小知识：

    @MappedSuperclass
        这个注解表示在父类上面的，用来标识父类。
        基于代码复用和模型分离的思想，在项目开发中使用JPA的@MappedSuperclass注解将实体类的多个属性分别封装到不同的非实体类中。例如，数据库表中都需要id来表示编号，id是这些映射实体类的通用的属性，交给jpa统一生成主键id编号，那么使用一个父类来封装这些通用属性，并用@MappedSuperclas标识。
        注意:
        1.标注为@MappedSuperclass的类将不是一个完整的实体类，他将不会映射到数据库表，但是他的属性都将映射到其子类的数据库字段中。
        2.标注为@MappedSuperclass的类不能再标注@Entity或@Table注解，也无需实现序列化接口。

    @Date @ToString
        lombok 的注解， 不用写get set toString 方法， 简化代码

    @EqualsAndHashCode
        1. 此注解会生成equals(Object other) 和 hashCode()方法。
        2. 它默认使用非静态，非瞬态的属性
        3. 可通过参数exclude排除一些属性
        4. 可通过参数of指定仅使用哪些属性
        5. 它默认仅使用该类中定义的属性且不调用父类的方法
        6. 可通过callSuper=true解决上一点问题。让其生成的方法中调用父类的方法。

        另：@Data相当于@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode这5个注解的合集。

        通过官方文档，可以得知，当使用@Data注解时，则有了@EqualsAndHashCode注解，那么就会在此类中存在equals(Object other) 和 hashCode()方法，且不会使用父类的属性，这就导致了可能的问题。
        比如，有多个类有相同的部分属性，把它们定义到父类中，恰好id（数据库主键）也在父类中，那么就会存在部分对象在比较时，它们并不相等，却因为lombok自动生成的equals(Object other) 和 hashCode()方法判定为相等，从而导致出错。

        修复此问题的方法很简单：
        1. 使用@Getter @Setter @ToString代替@Data并且自定义equals(Object other) 和 hashCode()方法，比如有些类只需要判断主键id是否相等即足矣。
        2. 或者使用在使用@Data时同时加上@EqualsAndHashCode(callSuper=true)注解。

    @Column： 
        当实体的属性与其映射的数据库表的列不同名时需要使用@Column 标注说明，该属性通常置于实体的属性声明语句之前，还可与 @Id 标注一起使用。
        @Column 标注的常用属性是name，用于设置映射数据库表的列名。此外，该标注还包含其它多个属性，如：unique、nullable、length 等。 
        @Column 标注的columnDefinition属性: 表示该字段在数据库中的实际类型.通常 ORM 框架可以根据属性类型自动判断数据库中字段的类型,但是对于Date类型仍无法确定数据库中字段类型究竟是DATE,TIME还是TIMESTAMP.此外,String的默认映射类型为VARCHAR,如果要将 String 类型映射到特定数据库的 BLOB 或TEXT字段类型.
        @Column标注也可置于属性的getter方法之前

    @Temporal 用法
        第一种：@Temporal(TemporalType.DATE)——>实体类会封装成日期“yyyy-MM-dd”的 Date类型。
        第二种：@Temporal(TemporalType.TIME)——>实体类会封装成时间“hh-MM-ss”的 Date类型。
        第三种：@Temporal(TemporalType.TIMESTAMP)——>实体类会封装成完整的时间“yyyy-MM-dd hh:MM:ss”的 Date类型。

    @PrePersist
        可以用来在使用jpa的时记录一些业务无关的字段，比如最后更新时间等等。
        生命周期方法注解（delete没有生命周期事件）

        @PrePersist save之前被调用，它可以返回一个DBObject代替一个空的
        @PostPersist save到datastore之后被调用

        @PostLoad     在Entity被映射之后被调用
        @EntityListeners 指定外部生命周期事件实现类

    @PreUpdate
        用于为相应的生命周期事件指定回调方法。
            该注释可以应用于实体类，映射超类或回调监听器类的方法。 用于setter

        如果要每次更新实体时更新实体的属性，可以使用@PreUpdate注释。
            使用该注释，您不必在每次更新用户实体时显式更新相应的属性。

        preUpdate不允许您更改您的实体。 您只能使用传递给事件的计算的更改集来修改原始字段值。
            如果你检查数据库，你会看到，Person实体已经更新，不会看到它们在$ persons变量，直到下次手动检索它们。

        在刷新后，您必须执行的操作是从数据库中检索实体以查看其更新值
 */
@MappedSuperclass
@Data
@ToString
@EqualsAndHashCode
public class BaseEntity {

    /**
     * Create Time
     */
    @Column(name = "create_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * Update Time
     */
    @Column(name = "update_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * Delete Flag
     */
    @Column(name = "deleted", columnDefinition = "TINYINT default 0")
    private Boolean deleted = false;

    /**
     * 初始化 创建和更新 时间
     */
    @PrePersist
    protected void prePersist() {
        deleted =false;
        Date now = DateUtil.now();
        if (createTime == null) {
            createTime = now;
        }

        if (updateTime == null) {
            updateTime = now;
        }
    }

    /**
     *  在持久化时，数据更新，此方法会执行
     */
    @PreUpdate
    protected void preUpdate() {
        updateTime = new Date();
    }

    /**
     *  在持久化时，数据删除，此方法会执行
     */
    @PreRemove
    protected void preRemove() {
        updateTime = new Date();
    }
}
