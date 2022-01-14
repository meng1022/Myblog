package backend.Interceptor;

import backend.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;

@Component
public class LoggerInterceptor implements HandlerInterceptor{
//    final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String KEY_USER = "__user__";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception{
//        logger.info("preHandle interceptor...");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception{
//        logger.info("postHandle interceptor...");
        if(modelAndView!=null){
            long timestamp = System.currentTimeMillis();
            String date = new SimpleDateFormat("yyyy-MM-dd").format(timestamp);
            modelAndView.addObject("date",date);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(KEY_USER);
            modelAndView.addObject("user",user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception{
//        logger.info("after completion {}: exception = {}",request.getRequestURI(),ex);
    }
}
