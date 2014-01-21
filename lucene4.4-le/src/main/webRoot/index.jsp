<%@ page import="org.joda.time.DateTime" %>
<%@ page import="com.lj4s.handle.PageSearch" %>
<%@ page import="org.apache.lucene.document.Document" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.lj4s.handle.SimpleHightlightSearcher" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

    <meta http-equiv="Expires" CONTENT="0"/>
    <meta http-equiv="Cache-Control" CONTENT="no-cache"/>
    <meta http-equiv="Pragma" CONTENT="no-cache"/>

    <title>测试</title>


</head>

<body>
<%@ include file="headers.jsp" %>
<%
    request.setCharacterEncoding("UTF-8");

    //指定输入编码

    response.setCharacterEncoding("UTF-8");
    //        String query =new String(request.getParameter("q").getBytes("utf-8"),"utf-8");
    String query = request.getParameter("q");
//    String query = request.getParameter("p");
    if (null == query) {
        query = "特价";
    }
//    query = URLDecoder.decode(query, "utf-8");

%>
hello jetty!
<form method=GET action="/testContext/index.jsp?">
    please input searcher string: <input type=text name="q" value="<%=query %>"
                                         size="20">
    <input type=submit class="btn-info">
</form>

<%--The time is ： <%= new DateTime().toString("yyyy-MM-dd HH:mm:ss") %>--%>
<%--<%--%>
<%--out.println("<BR>Your machine's address is ");--%>
<%--out.println(request.getLocalAddr());--%>
<%--out.println(query);--%>
<%--%>--%>
<%
    Integer pNum = 1;
    String pageNum = request.getParameter("page");
    String query1 = request.getParameter("p");
//        String keyword = URLDecoder.decode(request.getParameter("p"), "utf-8");
    if (null == query1) {
        query1 = "特价";
    }
    if (null != pageNum) {
        pNum = Integer.valueOf(pageNum);
    }
    PageSearch pageSearch = new PageSearch();
    try {
        pageSearch.doSearch(new SimpleHightlightSearcher(), pNum, query);
    } catch (Exception e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    Document[] documents = pageSearch.documents;
%>
分词信息：<%=pageSearch.queryString%>
<br />
耗时：<%=pageSearch.searchingTime%>  ms
<hr/>
<table class="table table-striped table-bordered">
    <thead>
    <tr>
        <th>img</th>
        <th>标题</th>
    </tr>
    </thead>
    <tbody>
    <%
        if (documents != null && documents.length > 0)
            for (int i = 0; i < documents.length; i++) {
                String title = documents[i].get("contents");
    %>
    <TR>
        <TD><img src="http://gi1.md.alicdn.com/bao/uploaded/i1/17531027397822865/T1WdWAFkFbXXXXXXXX_!!0-item_pic.jpg"
                 style="width:60px;height:40px;"/></TD>
        <TD><%=title%>
        </TD>
    </TR>
    <%
            }
    %>
    </tbody>
</table>

<div id="paginate">
    <div class="pagination">
        <ul>
            <%
                for (int i = 1; i <= pageSearch.pages; i++) {
                    if (i == pageSearch.curPage) {
            %>
            <li class="prev disabled">
                <a><%=i%>
                </a>
            </li>
            <%
            } else {
            %>
            <li>
                     <span class="info">
                    <a href="${pageContext.request.contextPath}/index.jsp?page=<%=i%>&q=<%=URLEncoder.encode(query,"utf-8")%>&"><%=i%>
                    </a>
                     </span>
            </li>
            <%
                }
            %>
            <%
                }
            %>
            <li class="prev disabled">
                <span class="info"> 总数 <%=pageSearch.len%>
            </li>
        </ul>
    </div>
</div>

</body>

</html>