package cn.example.servlet;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * ServletConfig（接口） 对象详解
 * <p>
 * 1. ServletConfig 对象是由谁创建的?
 * tomcat 创建的
 *
 * @author ryanfawcett
 * @since 2024/03/26
 */
@SuppressWarnings("all")
public class ServletConfigExample extends GenericServlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        // 1. 获取 ServletConfig 对象
        ServletConfig config = this.getServletConfig();
        // 2. 通过 ServletConfig 可以获取其封装的配置信息
        String servletName = config.getServletName(); // 获取注册的 servlet 名字
        // 在注册 servlet 时配置的初始化参数
        String url = config.getInitParameter("url");

        PrintWriter out = response.getWriter();
        // 或者获取全部的初始化参数
        Enumeration<String> initParameterNames = config.getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            String initParamName = initParameterNames.nextElement();
            String initParamValue = config.getInitParameter(initParamName);
            out.print("<h2>" + initParamName + ":" + initParamValue + "</h2>");
        }

        // 但是 GenericServlet 对这些方法进行了封装
        // 因此可以直接通过 this.methodName的方式直接调用
        this.getServletName();
        this.getInitParameter("url");
        this.getInitParameterNames();
    }

}
