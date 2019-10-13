package com.synisys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(value = "/search")
public class SearchServlet extends HttpServlet {
    private UsersService usersService = new UsersService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        String[] splitted = data.split(" ");
        Map<User, Integer> users = usersService.getUsers();
        StringBuilder result = new StringBuilder();
        for( User u : users.keySet()){
            String firstName = u.getFirstName().toLowerCase();
            String lastName = u.getLastName().toLowerCase();
            if(splitted.length == 1){
                if(firstName.contains(splitted[0]) || lastName.contains(splitted[0])){
                    result.append(u.getFirstName()).append(" ").append(u.getLastName()).append(", ");
                }
            } else {
                if((firstName.contains(splitted[0]) && lastName.contains(splitted[1])) || (firstName.contains(splitted[1]) && lastName.contains(splitted[0]))){
                    result.append(u.getFirstName()).append(" ").append(u.getLastName()).append(", ");
                }
            }

        }
        String finalResult = result.toString();
        finalResult = finalResult.substring(0, finalResult.length()-2);
        PrintWriter out = resp.getWriter();
        out.write(finalResult);
    }
}
