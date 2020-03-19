package cn.edu.sysu.workflow.resouce.util;

import cn.edu.sysu.workflow.resouce.core.allocator.Allocator;
import cn.edu.sysu.workflow.resouce.core.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Rinkako
 * Date  : 2018/2/6
 * Usage : Some static methods for reflection.
 */
public final class ReflectUtil {

    private static final Logger log = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * Package path of Allocators.
     */
    private static final String ALLOCATOR_PACKAGE_PATH = "org.sysu.renResourcing.allocator.";

    /**
     * Package path of Allocators.
     */
    private static final String FILTER_PACKAGE_PATH = "org.sysu.renResourcing.filter.";

    /**
     * Postfix of Allocator.
     */
    private static final String ALLOCATOR_POSTFIX = "Allocator";

    /**
     * Postfix of Filter.
     */
    private static final String FILTER_POSTFIX = "Filter";

    /**
     * Create a new allocator by its name.
     *
     * @param allocatorName     name of allocator to be created
     * @param processInstanceId process instance id
     * @return Specific allocator
     * @throws Exception reflect instance create failed
     */
    public static Allocator reflectAllocator(String allocatorName, String processInstanceId) throws Exception {
        try {
            Class classType = Class.forName(ReflectUtil.ALLOCATOR_PACKAGE_PATH + allocatorName + ReflectUtil.ALLOCATOR_POSTFIX);
            return (Allocator) classType.newInstance();
        } catch (Exception ex) {
            log.error(String.format("[%s]Reflect create allocator (%s) failed, %s", processInstanceId, allocatorName, ex));
            // rethrow to engine
            throw ex;
        }
    }

    /**
     * Create a new filter by its name.
     *
     * @param filterName name of filter to be created
     * @param processInstanceId       process instance id
     * @return Specific filter
     * @throws Exception reflect instance create failed
     */
    public static Filter reflectFilter(String filterName, String processInstanceId) throws Exception {
        try {
            Class classType = Class.forName(ReflectUtil.FILTER_PACKAGE_PATH + filterName + ReflectUtil.FILTER_POSTFIX);
            return (Filter) classType.newInstance();
        } catch (Exception ex) {
            log.error(String.format("[%s]Reflect create filter (%s) failed, %s", processInstanceId, filterName, ex));
            // rethrow to engine
            throw ex;
        }
    }
}
