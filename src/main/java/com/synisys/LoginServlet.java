package com.synisys;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    private UsersService usersService = new UsersService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "       <meta charset=\"UTF-8\">\n" +
                "       <title>Register</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "        <label>FirstName</label>\n" +
                "        <input id=\"firstName\"/>\n" +
                "        <br>" +
                "        <label>LastName</label>\n" +
                "        <input id=\"lastName\"/>\n" +
                "        <br>\n" +
                "        <button onclick=\"sendUser()\">sign in</button>\n" +
                "        <p id=\"register\"> Register </p>\n" +
                "</body>\n" +
                "<script>\n" +
                "    function sendUser(){\n" +
                "        let xhr = new XMLHttpRequest();\n" +
                "        let firstName = document.getElementById(\"firstName\");\n" +
                "        let lastName = document.getElementById(\"lastName\");\n" +
                "        xhr.onreadystatechange = function() {\n" +
                "           if (this.readyState === 4 && this.status === 200) {\n" +
                "               if(this.responseText === \"invalid\"){\n" +
                "                   alert(\"firstname or lastname is invalid. Try again!\");\n" +
                "               }\n" +
                "               window.location = '/'\n" +
                "           }\n" +
                "        };\n" +
                "        xhr.open(\"POST\",\"http://localhost:8080/login\");\n" +
                "        xhr.send(JSON.stringify({\"firstName\": firstName.value, \"lastName\": lastName.value}));\n" +
                "    }\n" +
                "    document.getElementById(\"register\").addEventListener(\"click\", function(event) {\n" +
                "       window.location = \"/register\"; \n" +
                "    });\n" +
                "</script>\n" +
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
            int token = usersService.findUser(user);
            if(token>=0){
                Cookie cookieToken = new Cookie("token", Integer.toString(token));
                cookieToken.setMaxAge(5000);
                resp.addCookie(cookieToken);
                Cookie cookieName = new Cookie("name", user.getFirstName());
                cookieName.setMaxAge(5000);
                resp.addCookie(cookieName);
                Cookie cookieLastName = new Cookie("surname", user.getLastName());
                cookieLastName.setMaxAge(5000);
                resp.addCookie(cookieLastName);
            } else {
                resp.getWriter().write("invalid");
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
