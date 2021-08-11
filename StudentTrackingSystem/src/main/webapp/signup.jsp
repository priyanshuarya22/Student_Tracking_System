<%@page import="java.sql.*" %>
<%
try {
	Class.forName("com.mysql.cj.jdbc.Driver");
	String sid = request.getParameter("sid");
	String password = request.getParameter("password");
	String name = request.getParameter("name");
	String pno = request.getParameter("pno");
	String query = "insert into students values(?, ?, ?, ?)";
	Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/sts?user=root&password=admin");
	PreparedStatement st = cn.prepareStatement(query);
	st.setString(1, sid);
	st.setString(2, password);
	st.setString(3, name);
	st.setString(4, pno);
	st.executeUpdate();
	cn.close();
	out.println("Data Saved");
}
catch(Exception e) {
	out.println(e.getMessage());
}
%>