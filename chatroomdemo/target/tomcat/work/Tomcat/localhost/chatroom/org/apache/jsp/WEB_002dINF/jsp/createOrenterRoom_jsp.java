/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-10-22 07:05:01 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class createOrenterRoom_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("    <div id=\"welcome\">welcome</div><br/>         \r\n");
      out.write("    <form id=\"room_form\" action=\"");
      out.print(basePath);
      out.write("/chat.action\" method=\"post\">\r\n");
      out.write("    <input type=\"hidden\" value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${userId }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write(" name=\"userId\"/>\r\n");
      out.write("    <label id=\"choose room\">请选择创建房间或者进入已有的房间</label>\r\n");
      out.write("    <select id=\"roomchoice\" name=\"roomchoice\" onclick=\"change()\">\r\n");
      out.write("    <!-- 如果是创建房间，则标记位1，如果是进入已有房间，则标记位0 -->\r\n");
      out.write("    \t\t\t<option value=\"1\" >创建房间</option>\r\n");
      out.write("    \t\t\t<option value=\"0\">进入已有房间</option>\r\n");
      out.write("    </select>\r\n");
      out.write("    <p id=\"choice\"></p>\r\n");
      out.write("    <input id=\"roomName\" name=\"roomName\" type=\"text\"><br/>\r\n");
      out.write("           <input type=\"hidden\" id=\"fromId\" name=\"fromId\">\r\n");
      out.write("           <button type=\"submit\" id=\"login\" >进入房间主页</button>\r\n");
      out.write("    </form>\r\n");
      out.write("</body>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("   var userId='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionScope.userId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("';//获取从登录页面添加到session中的userId\r\n");
      out.write("   welcomeInnerHTML(userId);//添加欢迎用户显示出来\r\n");
      out.write("   function welcomeInnerHTML(innerHTML){\r\n");
      out.write("  \t document.getElementById('welcome').innerHTML += ','+innerHTML+ '<br/>';\r\n");
      out.write("  }\r\n");
      out.write("   function change(){\r\n");
      out.write("\t   var choice=document.getElementById('roomchoice').value;\r\n");
      out.write("\t   if(choice==\"1\"){\r\n");
      out.write("\t\t   document.getElementById('choice').innerHTML = '请输入房间名进行创建'+ '<br/>';\r\n");
      out.write("\t   }else{\r\n");
      out.write("\t\t   document.getElementById('choice').innerHTML = '请输入房间名进入'+ '<br/>';   \r\n");
      out.write("\t   }\r\n");
      out.write("   }\r\n");
      out.write("   \r\n");
      out.write("</script>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
