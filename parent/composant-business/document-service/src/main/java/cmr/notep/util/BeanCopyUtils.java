package cmr.notep.util;

import org.dozer.DozerBeanMapper;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

    /**
     * Copy only properties whose type is Collection, Map or array from source to target,
     * excluding properties whose name is provided in excludedPropertyNames.
     * Null values are ignored (i.e. they don't overwrite target values).
     */
    public static void copyPropertiesIncludingOnlyCollections(Object source, Object target, String... excludedPropertyNames) {
        copyPropertiesIncludingOnlyCollections(source, target, true, excludedPropertyNames);
    }

    /**
     * Copy only properties whose type is Collection, Map or array from source to target,
     * excluding properties whose name is provided in excludedPropertyNames.
     * @param ignoreNulls when true, null values from source will not overwrite target properties.
     * @param excludedPropertyNames property names to exclude from copy.
     */
    public static void copyPropertiesIncludingOnlyCollections(Object source, Object target, boolean ignoreNulls,
            String... excludedPropertyNames) {
        Objects.requireNonNull(source, "source must not be null");
        Objects.requireNonNull(target, "target must not be null");

        Set<String> excludedProperties = excludedPropertyNames == null
                ? new HashSet<>()
                : new HashSet<>(Arrays.asList(excludedPropertyNames));

        try {
            BeanInfo srcInfo = Introspector.getBeanInfo(source.getClass(), Object.class);
            BeanInfo targetInfo = Introspector.getBeanInfo(target.getClass(), Object.class);

            Map<String, PropertyDescriptor> targetPdMap = new HashMap<>();
            for (PropertyDescriptor pd : targetInfo.getPropertyDescriptors()) {
                targetPdMap.put(pd.getName(), pd);
            }

            for (PropertyDescriptor srcPd : srcInfo.getPropertyDescriptors()) {
                String name = srcPd.getName();
                if ("class".equals(name) || excludedProperties.contains(name)) {
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
                if (!(propType.isArray() || Collection.class.isAssignableFrom(propType)
                        || Map.class.isAssignableFrom(propType))) {
                    continue;
                }

                Object value = read.invoke(source);
                if (ignoreNulls && value == null) {
                    continue;
                }

                if (value != null && !targetPd.getPropertyType().isAssignableFrom(value.getClass())) {
                    continue;
                }

                write.invoke(target, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to copy bean collection properties", e);
        }
    }

    /**
     * Copie uniquement les attributs de type {@link Collection} du modèle source vers l'entité cible,
     * en excluant les noms de propriétés fournis dans {@code excludedPropertyNames}.
     * <p>
     * Pour chaque collection trouvée dans la source :
     * <ul>
     *     <li>l'attribut correspondant est recherché dans la cible (même nom, nom + "Entities",
     *     ou via l'annotation Dozer {@code @Mapping} portée par le champ de l'entité) ;</li>
     *     <li>si l'attribut cible existant n'est pas vide, il est vidé ;</li>
     *     <li>chaque occurrence de la collection source est mappée individuellement vers le type
     *     d'élément déclaré par la collection cible, via Dozer ;</li>
     *     <li>la nouvelle collection obtenue est affectée à l'attribut cible.</li>
     * </ul>
     * Si l'attribut cible ou le type d'élément cible ne peuvent pas être résolus, une
     * {@link BeanCollectionMappingException} est levée.
     *
     * @param source                 le modèle source (ex: {@code Documents})
     * @param target                 l'entité cible (ex: {@code DocumentsEntity})
     * @param dozerMapper            le mapper Dozer à utiliser pour mapper chaque occurrence
     * @param excludedPropertyNames  noms des propriétés (collections) du modèle source à ignorer
     */
    public static void copyCollectionsWithDozer(Object source, Object target, DozerBeanMapper dozerMapper,
            String... excludedPropertyNames) {
        Objects.requireNonNull(source, "source must not be null");
        Objects.requireNonNull(target, "target must not be null");
        Objects.requireNonNull(dozerMapper, "dozerMapper must not be null");

        Set<String> excludedProperties = excludedPropertyNames == null
                ? new HashSet<>()
                : new HashSet<>(Arrays.asList(excludedPropertyNames));

        try {
            BeanInfo srcInfo = Introspector.getBeanInfo(source.getClass(), Object.class);

            for (PropertyDescriptor srcPd : srcInfo.getPropertyDescriptors()) {
                String sourceName = srcPd.getName();
                if ("class".equals(sourceName) || excludedProperties.contains(sourceName)) {
                    continue;
                }

                Class<?> propType = srcPd.getPropertyType();
                if (propType == null || !Collection.class.isAssignableFrom(propType)) {
                    // On ne traite ici que les collections
                    continue;
                }

                Method read = srcPd.getReadMethod();
                if (read == null) {
                    continue;
                }

                Object rawValue = read.invoke(source);
                if (rawValue == null) {
                    // Rien à copier pour cette collection
                    continue;
                }
                Collection<?> sourceCollection = (Collection<?>) rawValue;

                copySingleCollection(target, sourceName, sourceCollection, dozerMapper);
            }
        } catch (BeanCollectionMappingException e) {
            throw e;
        } catch (Exception e) {
            throw new BeanCollectionMappingException(
                    "Echec lors de la copie des collections vers l'entité cible " + target.getClass().getName(), e);
        }
    }

    private static void copySingleCollection(Object target, String sourceName, Collection<?> sourceCollection,
            DozerBeanMapper dozerMapper) throws Exception {
        Class<?> targetClass = target.getClass();

        Field targetField = resolveTargetField(targetClass, sourceName);
        if (targetField == null) {
            throw new BeanCollectionMappingException(
                    "Impossible de résoudre l'attribut cible correspondant à la collection '" + sourceName
                            + "' dans l'entité " + targetClass.getName());
        }

        Class<?> elementTargetClass = resolveElementTargetClass(targetField);
        if (elementTargetClass == null) {
            throw new BeanCollectionMappingException(
                    "Impossible de déterminer le type d'élément cible pour l'attribut '" + targetField.getName()
                            + "' de l'entité " + targetClass.getName());
        }

        BeanInfo targetInfo = Introspector.getBeanInfo(targetClass, Object.class);
        PropertyDescriptor targetPd = null;
        for (PropertyDescriptor pd : targetInfo.getPropertyDescriptors()) {
            if (pd.getName().equals(targetField.getName())) {
                targetPd = pd;
                break;
            }
        }
        if (targetPd == null || targetPd.getWriteMethod() == null) {
            throw new BeanCollectionMappingException(
                    "Aucun setter disponible pour l'attribut '" + targetField.getName() + "' de l'entité "
                            + targetClass.getName());
        }

        // Vider l'attribut cible existant s'il n'est pas vide
        Method targetRead = targetPd.getReadMethod();
        if (targetRead != null) {
            Object existing = targetRead.invoke(target);
            if (existing instanceof Collection<?> existingCollection && !existingCollection.isEmpty()) {
                existingCollection.clear();
            }
        }

        // Mapper chaque occurrence une par une vers le type cible via Dozer
        List<Object> mapped = new ArrayList<>();
        for (Object item : sourceCollection) {
            if (item == null) {
                mapped.add(null);
                continue;
            }
            mapped.add(dozerMapper.map(item, elementTargetClass));
        }

        // Affecter la réponse à l'attribut cible
        targetPd.getWriteMethod().invoke(target, mapped);
    }

    /**
     * Recherche l'attribut de la classe cible correspondant au nom de la propriété source :
     * même nom, nom + "Entities", ou champ annoté {@code @org.dozer.Mapping(sourceName)}.
     */
    private static Field resolveTargetField(Class<?> targetClass, String sourceName) {
        Field directField = findDeclaredField(targetClass, sourceName);
        if (directField != null) {
            return directField;
        }

        Field pluralEntitiesField = findDeclaredField(targetClass, sourceName + "Entities");
        if (pluralEntitiesField != null) {
            return pluralEntitiesField;
        }

        for (Field field : targetClass.getDeclaredFields()) {
            org.dozer.Mapping mapping = field.getAnnotation(org.dozer.Mapping.class);
            if (mapping != null && sourceName.equals(mapping.value())) {
                return field;
            }
        }

        return null;
    }

    private static Field findDeclaredField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * Détermine la classe d'élément attendue par la collection cible, à partir de son type générique déclaré.
     */
    private static Class<?> resolveElementTargetClass(Field targetField) {
        Type genericType = targetField.getGenericType();
        if (genericType instanceof ParameterizedType parameterizedType) {
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            if (typeArguments.length > 0 && typeArguments[0] instanceof Class<?> elementClass) {
                return elementClass;
            }
        }
        return null;
    }
}
