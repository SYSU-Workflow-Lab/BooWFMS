package cn.edu.sysu.workflow.access.filter;

import cn.edu.sysu.workflow.access.dao.AuthorityDAO;
import cn.edu.sysu.workflow.common.entity.access.Authority;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 权限控制
 * TODO 以工作项开始和完成举例，后续补充
 *
 * @author Skye
 * Created on 2020/6/10
 */
@Component
public class AuthorityFilter extends ZuulFilter {

    @Autowired
    private AuthorityDAO authorityDAO;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestUrl = request.getRequestURL().toString();
        return requestUrl.contains("/resource/workitem/start") || requestUrl.contains("/resource/workitem/complete");
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String workerId = request.getParameter("workerId");
        String workItemId = request.getParameter("workItemId");
        Authority authority = authorityDAO.findByAccountIdAndBusinessProcessEntityId(workerId, workItemId);
        if (authority == null || authority.getType().toCharArray()[4] != 'E') {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.getResponse().setCharacterEncoding("UTF-8");
            ctx.setResponseBody("无权限操作");
        }
        return null;

    }

}
