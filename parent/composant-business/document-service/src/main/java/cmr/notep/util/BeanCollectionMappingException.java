package cmr.notep.util;

/**
 * Thrown when {@link BeanCopyUtils#copyCollectionsWithDozer(Object, Object, org.dozer.DozerBeanMapper, String...)}
 * cannot resolve the target entity property (or its element type) matching a source model collection.
 */
public class BeanCollectionMappingException extends RuntimeException {

    public BeanCollectionMappingException(String message) {
        super(message);
    }

    public BeanCollectionMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}

