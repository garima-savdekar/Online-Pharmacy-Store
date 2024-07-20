<%@page import="com.pharmacystore.dao.UserDAO"%>
<%@page import="com.pharmacystore.daoimpl.UserDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	
	<jsp:useBean id="user" 
	class="com.pharmacystore.pojo.User" scope="page">
	</jsp:useBean>
	
	<jsp:setProperty property="*" name="user"/>
	
	<%
		UserDAO daoImpl = new UserDAOImpl();
		if(daoImpl.register(user)) {
			response.sendRedirect("UserRegistration_Success.jsp");		
		}
		else {
			response.sendRedirect("UserRegistration_Failure.jsp");
		}
	%>