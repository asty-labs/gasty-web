package gasty

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 12/5/12
 * Time: 12:41 AM
 * To change this template use File | Settings | File Templates.
 */
class ObjectRef<T> implements Serializable {

    Class<T> clazz
    Long id

    private ObjectRef(Class<T> clazz, Long id) {
        this.clazz = clazz
        this.id = id
    }

    static <U> ObjectRef<U> create(Class<U> clazz) {
        new ObjectRef<U>(clazz, null)
    }

    static <U> ObjectRef<U> from(U obj) {
        new ObjectRef(obj.class, obj.id)
    }

    Boolean isNew() {
        id == null
    }

    T get() {
        if (isNew())
            clazz.newInstance()
        else
            clazz.get(id)
    }

    void delete() {
        get().delete(flush: true)
    }
}