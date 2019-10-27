package work.idler.jilijili.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;
import work.idler.jilijili.common.exception.NotFoundException;
import work.idler.jilijili.common.logging.Logger;
import work.idler.jilijili.repository.base.BaseRepository;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 通用 Crud 方法实现
 *
 * @Auther https://blog.idler.work
 * @Date 2019/5/11
 */
public abstract class AbstractCrudService<DOMAIN, ID> implements CrudService<DOMAIN, ID> {

    private final Logger log = Logger.getLogger(getClass());

    private final String domainName;

    private final BaseRepository<DOMAIN, ID> baseRepository;

    protected AbstractCrudService(BaseRepository<DOMAIN, ID> baseRepository) {
        this.baseRepository = baseRepository;

        // Get domain name
        Class<DOMAIN> domainClass = (Class<DOMAIN>) fetchType(0);
        domainName = domainClass.getSimpleName();
    }

    /**
     * Gets actual generic type.
     *
     * @param index generic type index
     * @return real generic type will be returned
     */
    private Type fetchType(int index) {
        Assert.isTrue(index >= 0 && index <= 1, "type index must be between 0 to 1");

        return ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[index];
    }

    /**
     * 根据删除状态查询
     *
     * @param deleted
     * @return
     */
    public List<DOMAIN> listByDeleted(boolean deleted){
        return baseRepository.findAllByDeleted(deleted);
    }

    /**
     * 查询全部
     *
     * @return List
     */
    @Override
    public List<DOMAIN> listAll() {
        return baseRepository.findAll();
    }

    /**
     * 排序查询全部
     *
     * @param sort sort
     * @return List
     */
    @Override
    public List<DOMAIN> listAll(Sort sort) {
        Assert.notNull(sort, "Sort info must not be null");

        return baseRepository.findAll(sort);
    }

    /**
     * 分页查询全部
     *
     * @param pageable pageable
     * @return Page
     */
    @Override
    public Page<DOMAIN> listAll(Pageable pageable) {
        Assert.notNull(pageable, "Pageable info must not be null");

        return baseRepository.findAll(pageable);
    }

    /**
     * 按IDs获取
     *
     * @param ids
     * @return
     */
    @Override
    public List<DOMAIN> listAll(Iterable<ID> ids) {
        return baseRepository.findAllById(ids);
    }

    /**
     * 按ID获取
     *
     * @param id id
     * @return Optional
     */
    @Override
    public DOMAIN getById(ID id) {
        Assert.notNull(id, domainName + " id must not be null");

        return  baseRepository.findById(id).get();
    }

    /**
     * 判断id是否存在
     *
     * @param id id
     * @return boolean
     */
    @Override
    public boolean existsById(ID id) {
        Assert.notNull(id, domainName + " id must not be null");

        return baseRepository.existsById(id);
    }

    /**
     * 统计所有
     *
     * @return long
     */
    @Override
    public long count() {
        return baseRepository.count();
    }

    /**
     * 按对象保存
     *
     * @param domain domain
     * @return DOMAIN
     */
    @Override
    public DOMAIN create(DOMAIN domain) {
        Assert.notNull(domain, domainName + " data must not be null");

        return baseRepository.save(domain);
    }

    /**
     * 按对象更新
     *
     * @param domain domain
     * @return DOMAIN
     */
    @Override
    public DOMAIN update(DOMAIN domain) {
        Assert.notNull(domain, domainName + " data must not be null");

        return baseRepository.saveAndFlush(domain);
    }

    /**
     * 按ID删除
     *
     * @param id id
     * @return DOMAIN
     * @throws NotFoundException If the specified id does not exist
     */
    @Override
    public DOMAIN removeById(ID id) {
        // Get non null domain by id
        DOMAIN domain = getById(id);

        // Remove it
        remove(domain);

        // return the deleted domain
        return domain;
    }

    /**
     * 按对象删除
     *
     * @param domain domain
     */
    @Override
    public void remove(DOMAIN domain) {
        Assert.notNull(domain, domainName + " data must not be null");

        baseRepository.delete(domain);
    }
}