package com.synisys;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/register")
public class RegisterServlet extends HttpServlet {
    private UsersService usersService = new UsersService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "       <meta charset=\"UTF-8\">\n" +
                "       <title>Register</title>\n" +
                "   </head>\n" +
                "<script>\n" +
                "    function sendUser(){\n" +
                "        let xhr = new XMLHttpRequest();\n" +
                "        let firstName = document.getElementById(\"firstName\");\n" +
                "        let lastName = document.getElementById(\"lastName\");\n" +
                "        xhr.onreadystatechange = function() {\n" +
                "           if (this.readyState === 4 && this.status === 200) {\n" +
                "               window.location = '/'\n" +
                "           }\n" +
                "        };\n" +
                "        xhr.open(\"POST\",\"http://localhost:8080/register\");\n" +
                "        xhr.send(JSON.stringify({\"firstName\": firstName.value, \"lastName\": lastName.value}));\n" +
                "    }\n" +
                "</script>\n" +
                "<body>\n" +
                "        <label>FirstName</label>\n" +
                "        <input id=\"firstName\"/>\n" +
                "        <br>" +
                "        <label>LastName</label>\n" +
                "        <input id=\"lastName\"/>\n" +
                "        <br>\n" +
                "        <button onclick=\"sendUser()\">sign up</button>\n" +
                "</body>\n" +
                "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(data, User.class);
            usersService.addUser(user);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
