package cn.edu.sysu.workflow.booenginefeign.dag;

import cn.edu.sysu.workflow.booenginefeign.BooEngineFeignApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * {@link ProcessInstanceDag}
 *
 * @author Skye
 * Created on 2020/4/3
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooEngineFeignApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProcessInstanceDagTest {

    @Test
    public void test1() {
        ProcessInstanceDag processInstanceDag = new ProcessInstanceDag();
        ProcessInstanceDagItem processInstanceDagItem1 = new ProcessInstanceDagItem();
        processInstanceDagItem1.setTaskItemId("1");

        ProcessInstanceDagItem processInstanceDagItem2 = new ProcessInstanceDagItem();
        processInstanceDagItem2.setTaskItemId("2");
        processInstanceDag.add(processInstanceDagItem1);
        processInstanceDag.add(processInstanceDagItem2);

        Assert.assertEquals("1", processInstanceDag.get(0).getTaskItemId());
        Assert.assertEquals("2", processInstanceDag.get(1).getTaskItemId());

        processInstanceDag.remove(processInstanceDagItem1);
        Assert.assertEquals("2", processInstanceDag.get(0).getTaskItemId());
        processInstanceDag.remove(processInstanceDagItem2);
        Assert.assertEquals(0, processInstanceDag.size());
    }

}
