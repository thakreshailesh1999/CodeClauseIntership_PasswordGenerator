package com.password;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.password.check.Helper;

@WebServlet("/check")
public class PasswordCheck extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//super.doPost(req, resp);
		String id=req.getParameter("id");
		String name=req.getParameter("name");
		
		int id1=Integer.parseInt(id);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=Helper.getConnection();
			PreparedStatement statement=con.prepareStatement("select * from studentpassword where id=? and name=?");
			statement.setInt(1, id1);
			statement.setString(2, name);
			ResultSet set=statement.executeQuery();
			
			if(set.next())
			{
				PrintWriter pout=resp.getWriter();
				pout.println("Student Id :- "+set.getInt(1));
				pout.println("Student Name :- "+set.getString(2));
				pout.println("student Phone No :- "+set.getLong(3));
				pout.println("Student Password Generated :- "+set.getString(4));
				pout.println("-----------------------------------------------------");
			}
			else
			{
				PrintWriter pout=resp.getWriter();
				pout.println("<h1 align ='Center' style='color: red'> Invalid credentials</h1>");
				
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
