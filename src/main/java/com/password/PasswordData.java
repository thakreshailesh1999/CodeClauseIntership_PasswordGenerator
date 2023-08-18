package com.password;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/password")
public class PasswordData extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String upperchar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerchar = "abcdefghijklmnopqrstuvwxyz";
		String number = "0123456789";
		String specialchar = "!@#$%^&*()_";
		String combine = upperchar + lowerchar + number + specialchar;

		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");

		int id1 = Integer.parseInt(id);
		long phone1 = Long.parseLong(phone);

		int len = 8;

		String password = "";
		Random random = new Random();

		while (password.length() < len) {
			
			float value=random.nextFloat();
			int index = (int) (value* combine.length());
			
			password += combine.charAt(index);
			
		}

//		super.doPost(req, resp);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/password", "root","12345");
			PreparedStatement statement = connection.prepareStatement("insert into studentpassword values (?,?,?,?)");

			statement.setInt(1, id1);
			statement.setString(2, name);
			statement.setLong(3, phone1);
			statement.setString(4, password);

			statement.execute();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		PrintWriter printWriter2 = resp.getWriter();
		printWriter2.print("<h1 align='Center' style='color:green' >Password genereted successfully </h1>");

		RequestDispatcher requestDispatcher = req.getRequestDispatcher("checkpass.html");
		requestDispatcher.include(req, resp);
	}
}
