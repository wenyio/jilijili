package work.idler.jilijili.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import work.idler.jilijili.common.exception.NotFoundException;

import java.util.List;

/**
 * 通用 Crud 方法
 *
 * @Auther https://blog.idler.work
 * @Date 2019/5/11
 */
public interface CrudService<DOMAIN, ID> {

    /**
     * 根据删除状态查询
     *
     * @param deleted
     * @return
     */
    List<DOMAIN> listByDeleted(boolean deleted);

    /**
     * 查询全部
     *
     * @return List
     */
    @NonNull
    List<DOMAIN> listAll();

    /**
     * 排序查询全部
     *
     * @param sort sort
     * @return List
     */
    @NonNull
    List<DOMAIN> listAll(@NonNull Sort sort);

    /**
     * 分页查询全部
     *
     * @param pageable pageable
     * @return Page
     */
    @NonNull
    Page<DOMAIN> listAll(@NonNull Pageable pageable);

    /**
     * 按IDs获取
     *
     * @param ids
     * @return
     */
    List<DOMAIN> listAll(Iterable<ID> ids);

    /**
     * 按ID获取
     *
     * @param id id
     * @return Optional
     */
    @NonNull
    DOMAIN getById(@NonNull ID id);

    /**
     * 判断id是否存在
     *
     * @param id
     * @return boolean
     */
    boolean existsById(@NonNull ID id);

    /**
     * 统计所有
     *
     * @return long
     */
    long count();

    /**
     * 按对象保存
     *
     * @param domain domain
     * @return DOMAIN
     */
    @NonNull
    DOMAIN create(@NonNull DOMAIN domain);

    /**
     * 按对象更新
     *
     * @param domain domain
     * @return DOMAIN
     */
    @NonNull
    DOMAIN update(@NonNull DOMAIN domain);

    /**
     * 按ID删除
     *
     * @param id id
     * @return DOMAIN
     * @throws NotFoundException If the specified id does not exist
     */
    @NonNull
    DOMAIN removeById(@NonNull ID id);

    /**
     * 按对象删除
     *
     * @param domain domain
     */
    void remove(@NonNull DOMAIN domain);
}