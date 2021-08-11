<%@page import="java.sql.*" %>
<%
try {
	String data = "";
	Class.forName("com.mysql.cj.jdbc.Driver");
	String sid = request.getParameter("sid");
	String query = "select lat, lng, date_time from student_loc where sid = ?";
	Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/sts?user=root&password=admin");
	PreparedStatement st = cn.prepareStatement(query);
	st.setString(1, sid);
	ResultSet rs = st.executeQuery();
	while(rs.next()) {
		data += rs.getString(1) + "<--->" + rs.getString(2) + "<--->" + rs.getString(3) + ";";
	}
	out.println(data);
	cn.close();
}
catch(Exception e) {
	out.println(e.getMessage());
}
%>