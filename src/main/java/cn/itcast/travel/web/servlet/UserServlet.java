package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.web.servlet.BaseServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService service = new UserServiceImpl();

    public void regist(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{

        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo info = new ResultInfo();

            info.setFlag(false);
            info.setErrorMsg("验证码错误");

            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //完成注册

        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();

        if(flag){
            info.setFlag(true);
        }else {
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("applocation/json;charset=utf-8");
        response.getWriter().write(json);
    }

    public void login(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{

        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo info = new ResultInfo();

            info.setFlag(false);
            info.setErrorMsg("验证码错误");

            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }


        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        User u = service.login(user);
        ResultInfo info = new ResultInfo();

        if(u==null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        }
        //判断用户是否激活
        if(u!=null&&!"Y".equals(u.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请激活！");
        }
        //判断是否登录成功
        if(u!=null&&"Y".equals(u.getStatus())){
            request.getSession().setAttribute("user",u);
            info.setFlag(true);
        }
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("applocation/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
    }

    public void find(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        Object user = request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("applocation/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);
    }

    public void exit(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    public void active(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        String code = request.getParameter("code");
        if(code != null){
            boolean falg = service.active(code);
            String msg = null;
            if(falg){
                msg = "激活成功，请<a href='login.html'>登录</a>";
            }else {
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
