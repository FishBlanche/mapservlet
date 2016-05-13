package com.huanying.map.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huanying.map.domain.Data;
import com.huanying.map.service.IDataService;
import com.huanying.map.service.impl.DataServiceImpl;

public class ServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Type type_list_data;
	private IDataService dataService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		type_list_data = new TypeToken<List<Data>>() {
		}.getType();
		dataService = new DataServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(getClass().getName() + " 服务器收到请求" + request.getRemoteAddr());
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		PrintWriter pw = response.getWriter();
		String type = request.getParameter("dealtype");
		String data = request.getParameter("dealdata");
		doMethod(type, data, pw);
	}

	public void doMethod(String type, String data, PrintWriter pw) {
		String back = null;
		Gson gson = new Gson();
		// 参数格式 遵循 {"String":"fsdfs","int":10}
		if (type != null) {
			System.out.println("type:" + type);
			System.out.println("data:" + data);
			switch (type) {
			case "1"://
				back = gson.toJson(dataService.getProvince(), type_list_data);
				break;
			case "2":
				back = gson.toJson(dataService.getCity(data), type_list_data);
				break;
			case "3":
				back = gson.toJson(dataService.getwaterfactory(data), type_list_data);
				break;
			case "hello":
				back = "world";
				break;
			default:
				back = null;
				break;
			}
		}
		if (back != null) {
			pw.write(back);
			System.out.println(back);
		} else {
			back = "无数据返回";
			pw.write(back);
		}
		pw.flush();
		pw.close();
	}
}
