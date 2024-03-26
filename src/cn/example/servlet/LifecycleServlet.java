package cn.example.servlet;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

/**
 * Servlet 的生命周期
 *
 * @author ryanfawcett
 * @since 2024/03/24
 */
@SuppressWarnings("all")
public class LifecycleServlet implements Servlet {

    public LifecycleServlet() {
        System.out.println("1. 调用无参构造方法创建 Servlet 对象");
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("2. 调用 init 方法对 Servlet 对象进行初始化");
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        System.out.println("3. 初始化完成后调用 service 方法处理用户请求");
    }

    @Override
    public void destroy() {
        System.out.println("4. 销毁 Servlet 对象");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }
}
