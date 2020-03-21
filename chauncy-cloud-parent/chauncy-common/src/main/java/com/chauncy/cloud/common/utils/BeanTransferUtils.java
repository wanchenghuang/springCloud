package com.chauncy.cloud.common.utils;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Object adapter, conversion between objects, such as: VO to PO, PO to VO
 *
 * @author CipherCui
 */
public class BeanTransferUtils {

    /**
     * Generally used for the conversion of multiple A objects to B objects
     * <p>
     * After the objectMapper is used to transform the object, run the decorator to decorate it.
     * The decorator is an expression of the form (vo,po) -&gt; {vo.setA(po.getB()}.
     *
     * @param source  source object collection
     * @param targetClass target type
     * @param decorator   modify the target object
     * @param <A>         source object generic
     * @param <B>         target generic
     * @return target object collection
     */
    public static <A, B> List<B> transform(Collection<A> source, Class<B> targetClass, BiConsumer<A, B> decorator) {
        if (source == null || source.size() == 0) {
            return new ArrayList<>();
        } else {
            return source.stream().map(a -> {
                B b = JSONUtils.objectMapper.convertValue(a, targetClass);
                decorator.accept(a, b);
                return b;
            }).collect(Collectors.toList());
        }
    }

    /**
     * Generally used for conversion of multiple A objects to B objects
     *
     * @param source  source object collection
     * @param targetClass target type
     * @param <A>         source object generic
     * @param <B>         target generic
     * @return target object collection
     */
    public static <A, B> List<B> transform(Collection<A> source, Class<B> targetClass) {
        return transform(source, targetClass, (a, b) -> {});
    }

    /**
     * Generally used for the conversion of a single A object to a B object
     *
     * @param source      source object
     * @param targetClass target type
     * @param decorator   modify the target object
     * @param <A>         source object generic
     * @param <B>         target generic
     * @return target object
     */
    @SuppressWarnings("unchecked")
    public static <A, B> B transform(@NonNull Object source, Class<B> targetClass, BiConsumer<A, B> decorator) {
        List<A> list = new ArrayList<>();
        list.add((A) source);
        List<B> result = BeanTransferUtils.transform(list, targetClass, decorator);
        return result.get(0);
    }

    /**
     * Generally used for the conversion of a single A object to a B object
     *
     * @param source      source object
     * @param targetClass target type
     * @param <T>         target generic
     * @return target object
     *
     * List<A>---> List<B>
     *  Collection<MetaModel> metaModels = modelService.getMetaModelsByDataModelId(dataModelId);
     *  List<MetaModelDTO> metaModelDTOS = metaModels.stream()
     *                 .map(m -> BeanTransferUtils.transform(m, MetaModelDTO.class))
     *                 .collect(Collectors.toList());
     */
    public static <T> T transform(@NonNull Object source, Class<T> targetClass) {
        return transform(source, targetClass, (a, b) -> {});
    }


}