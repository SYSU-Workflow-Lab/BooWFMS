package cn.edu.sysu.workflow.resouce.core.principle;

import cn.edu.sysu.workflow.common.enums.WorkitemDistributionType;
import cn.edu.sysu.workflow.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Rinkako, Skye
 * Date  : 2018/2/9
 * Usage : A grammar parser for simplest principle descriptor. A descriptor
 * in this grammar is in a pattern of string:<br/>
 * <b>"DistributeType@DistributorName@ArgumentDictionaryForDistributorJSON@
 * DictionaryOfConstraintsKeyWithItsParameterDictValue"</b><br/>
 * While distribute type and name are case sensitive.<br/>
 * For example, shortest queue allocation is described like this:<br/>
 * <b>"Allocate@ShortestQueue@{}@{}"</b><br/>
 * Another example, filter for offering can be described like this:<br/>
 * <b>"Offer@QueueLength@{"length":"lt 3"}@{"CapabilityConstraint":{"contains":"cook"}}"</b><br/>
 * The above descriptor means offer work item to participant who has cook capability and their
 * queue length should less than 3 work items.<br/>
 * About the valid distribute type, distributor name and their parameters
 * can see the project document.
 */
public class SimplePrincipleGrammar implements PrincipleGrammar {

    private static final Logger log = LoggerFactory.getLogger(SimplePrincipleGrammar.class);

    /**
     * Parse a principle descriptor to a principle object.
     *
     * @param descriptor principle descriptor string
     * @return parsed principle object, null if parse failure
     */
    @Override
    public Principle Parse(String descriptor) {
        try {
            assert descriptor != null;
            String[] descriptorItems = descriptor.split("@");
            assert descriptorItems.length >= 2;
            Principle retPrinciple = new Principle();
            WorkitemDistributionType distributionType = WorkitemDistributionType.valueOf(descriptorItems[0]);
            HashMap filterArgs = descriptorItems.length >= 3 ? JsonUtil.jsonDeserialization(descriptorItems[2], HashMap.class) : new HashMap();
            retPrinciple.SetParsed(distributionType, descriptorItems[1], filterArgs);
            if (descriptorItems.length > 3) {
                HashMap<String, Map> constraints = JsonUtil.jsonDeserialization(descriptorItems[3], HashMap.class);
                for (Map.Entry<String, Map> constraintKvp : constraints.entrySet()) {
                    retPrinciple.AddConstraint(constraintKvp.getKey(), new HashMap(constraintKvp.getValue()));
                }
            }
            return retPrinciple;
        } catch (Exception e) {
            log.error(String.format("Parse (%s) failed: %s", descriptor, e));
            return null;
        }
    }
}
