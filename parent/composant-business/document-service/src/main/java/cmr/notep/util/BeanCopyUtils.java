package cmr.notep.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Utility to copy bean properties while excluding collection/array/map properties.
 * Designed to be used when a mapper (Dozer) cannot be configured to ignore collections.
 */
public final class BeanCopyUtils {

    private BeanCopyUtils() { }

    /**
     * Copy properties from source to target, excluding properties whose type is Collection, Map or array.
     * Null values are ignored (i.e. they don't overwrite target values).
     */
    public static void copyPropertiesExcludingCollections(Object source, Object target) {
        copyPropertiesExcludingCollections(source, target, true);
    }

    /**
     * Copy properties from source to target, excluding properties whose type is Collection, Map or array.
     * @param ignoreNulls when true, null values from source will not overwrite target properties.
     */
    public static void copyPropertiesExcludingCollections(Object source, Object target, boolean ignoreNulls) {
        Objects.requireNonNull(source, "source must not be null");
        Objects.requireNonNull(target, "target must not be null");

        try {
            BeanInfo srcInfo = Introspector.getBeanInfo(source.getClass(), Object.class);
            BeanInfo targetInfo = Introspector.getBeanInfo(target.getClass(), Object.class);

            Map<String, PropertyDescriptor> targetPdMap = new HashMap<>();
            for (PropertyDescriptor pd : targetInfo.getPropertyDescriptors()) {
                targetPdMap.put(pd.getName(), pd);
            }

            for (PropertyDescriptor srcPd : srcInfo.getPropertyDescriptors()) {
                String name = srcPd.getName();
                if ("class".equals(name)) {
                    continue;
                }
                Method read = srcPd.getReadMethod();
                if (read == null) {
                    continue;
                }

                PropertyDescriptor targetPd = targetPdMap.get(name);
                if (targetPd == null) {
                    continue;
                }

                Method write = targetPd.getWriteMethod();
                if (write == null) {
                    continue;
                }

                Class<?> propType = srcPd.getPropertyType();
                if (propType.isArray() || Collection.class.isAssignableFrom(propType) || Map.class.isAssignableFrom(propType)) {
                    // Exclude collections, maps and arrays
                    continue;
                }

                Object value = read.invoke(source);
                if (ignoreNulls && value == null) {
                    continue;
                }

                // Only attempt to set the value if the target property type is compatible
                if (value != null && !targetPd.getPropertyType().isAssignableFrom(value.getClass())) {
                    // incompatible type, skip
                    continue;
                }

                write.invoke(target, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to copy bean properties excluding collections", e);
        }
    }
}
