package io.swagger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class ModelHelper {
    // mainly from https://stackoverflow.com/questions/19737626/how-to-ignore-null-values-using-springframework-beanutils-copyproperties
    // This class is to combine old and new (received) object so that null values do not propagate
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    // then use Spring BeanUtils to copy and ignore null
    public static void combine(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(target));
    }
}
