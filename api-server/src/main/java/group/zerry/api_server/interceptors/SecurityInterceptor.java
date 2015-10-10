package group.zerry.api_server.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import group.zerry.api_server.annotation.AuthPass;
import group.zerry.api_server.enumtypes.UserStatusEnum;
import group.zerry.api_server.utils.CacheTools;
import redis.clients.jedis.Jedis;

public class SecurityInterceptor implements HandlerInterceptor {

	@Autowired
	CacheTools cacheTools;

	//private static Jedis jedis = new Jedis("localhost", 6379);

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            AuthPass authPass = ((HandlerMethod) handler).getMethodAnnotation(AuthPass.class);
            if (authPass == null || authPass.validate() == false)
                return true;
            else {
            	String username = request.getParameter("username");
            	String userToken = request.getParameter("userToken");
        		if (null == cacheTools.get(username) || !cacheTools.get(username).equals(userToken)) {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
        			StringBuilder regMsg = new StringBuilder("{\"returnmsg\":\"");
        			regMsg.append(UserStatusEnum.UNV);
        			regMsg.append("\"}");
                    response.getWriter().write(regMsg.toString());
                    return false;
        		}
        		else
        			return true;
            }
        }
        else
        	return true;
	}
}
