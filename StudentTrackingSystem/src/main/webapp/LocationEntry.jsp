<%@page import="java.sql.*" %>
<%
try {
	Class.forName("com.mysql.cj.jdbc.Driver");
	String sid = request.getParameter("sid");
	String lat = request.getParameter("lat");
	String lng = request.getParameter("lng");
	String date_time = request.getParameter("date_time");
	String query = "insert into student_loc(sid, lat, lng, date_time) values(?, ?, ?, ?)";
	Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/sts?user=root&password=admin");
	PreparedStatement st = cn.prepareStatement(query);
	st.setString(1, sid);
	st.setFloat(2, Float.parseFloat(lat));
	st.setFloat(3, Float.parseFloat(lng));
	st.setString(4, date_time);
	st.executeUpdate();
	cn.close();
	out.println("Data Saved");
}
catch(Exception e) {
	e.printStackTrace();
	out.println(e.getMessage());
}
%>