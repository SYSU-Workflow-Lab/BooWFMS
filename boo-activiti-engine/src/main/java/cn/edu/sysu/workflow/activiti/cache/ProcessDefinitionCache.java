package cn.edu.sysu.workflow.activiti.cache;

import cn.edu.sysu.workflow.activiti.util.CommonUtil;
import cn.edu.sysu.workflow.activiti.util.JedisPoolUtil;
import org.activiti.engine.impl.persistence.deploy.DeploymentCache;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * 自定义缓存处理类
 * 缓存流程模型
 * @author: Gordan Lin
 * @create: 2019/10/10
 **/
public class ProcessDefinitionCache implements DeploymentCache<ProcessDefinitionEntity> {

    private final static Logger logger = LoggerFactory.getLogger(ProcessDefinitionCache.class);

    @Override
    public ProcessDefinitionEntity get(String id) {
        Jedis jedis = null;
        ProcessDefinitionEntity pdf = null;
        try {
            // 获取数据
            jedis = JedisPoolUtil.getJedisPoolInstance().getResource();
            byte[] bs = jedis.get(id.getBytes());
            if (bs == null) {
                return null;
            }
            // 将二进制数据转换为ProcessDefinitionEntity对象
            Object object = CommonUtil.toObject(bs);
            if (object == null) {
                return null;
            }
            pdf = (ProcessDefinitionEntity) object;
        } catch (Exception e) {
            logger.error("redis查询发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return pdf;
    }

    @Override
    public void add(String id, ProcessDefinitionEntity object) {
        Jedis jedis = null;
        try {
            // 添加到缓存，因为value为object对象，所以需要将该对象转化为二进制进行存储
            jedis = JedisPoolUtil.getJedisPoolInstance().getResource();
            jedis.set(id.getBytes(), CommonUtil.toByteArray(object));
        } catch (Exception e) {
            logger.error("redis插入发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void remove(String id) {
        Jedis jedis = null;
        try {
            // 添加到缓存，因为value为object对象，所以需要将该对象转化为二进制进行存储
            jedis = JedisPoolUtil.getJedisPoolInstance().getResource();
            jedis.del(id.getBytes());
        } catch (Exception e) {
            logger.error("redis删除发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void clear() {

    }

}
