package com.example.employee.mgmt.system.controller;

import com.example.employee.mgmt.system.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BaseServlet extends HttpServlet {
    public User loginUser;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // 获取登录用户
        loginUser = (User) session.getAttribute("loginUser");
        // 获取请求的URI /user/login
        String uri = request.getRequestURI();
        // 从最后一个/开始截取 login
        uri = uri.substring(uri.lastIndexOf("/") + 1);
        // 具体的servlet
        Class<?> targetClass = this.getClass();

        System.out.println(this); // ?
        try {
            // 通过截取uri(方法名),反射出对应的方法 getMethod(方法名,参数列表...) 根据方法名和方法调用需要的参数列表类型来找方法执行
            Method method = targetClass.getMethod(uri, HttpServletRequest.class, HttpServletResponse.class);
            // 传入参数并执行方法
            method.invoke(this, request, response);
        } catch (java.lang.reflect.InvocationTargetException ite) {
            // 打印被调用方法抛出的异常
            Throwable targetException = ite.getTargetException();
            targetException.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=utf8");
            try {
                response.getWriter().write("{\"error\":\"" + targetException.getMessage() + "\"}");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=utf8");
            try {
                response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 获取request中的键值对，返回Map<String,Object>
     *
     * @param request
     * @return
     */
    public static Map<String, String> getParam(HttpServletRequest request) {
        // 从request中接收过来的参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        // 获取全部的key,value的Entry对象
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        // 将所有的键值对存在此Map集合中
        Map<String, String> returnMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : entries) {
            String[] values = entry.getValue();
            // 真的提交了多值(多选框)
            if (values.length > 1) {
                // 使用逗号拼接字符串 3,4,5,6,7
                String val = Arrays.toString(values);
                returnMap.put(entry.getKey(), val);
            } else {
                // 只要第0个下标的值
                returnMap.put(entry.getKey(), values[0]);
            }
        }
        return returnMap;
    }

    /**
     * 将对象转成字符串写出(处理ajax请求)
     *
     * @param obj
     * @param response
     */
    public void writerObjToString(HttpServletResponse response, Object obj) {
        try {

            ObjectMapper om = new ObjectMapper();

            String jsonStr = om.writeValueAsString(obj);

            // 统一设置编码
            response.setContentType("application/json;charset=utf8");
            response.getWriter().write(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
