package com.synisys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Register</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <p id=\"welcome\"> Welcome </p>\n" +
                "    <div>\n" +
                "        <input id=\"search\" type=\"text\" placeholder=\"Search..\">\n" +
                "    </div>\n" +
                "    <div id = \"searchResult\">\n" +
                "    </div>\n" +
                "    <script>\n" +
                "        let name, surname;\n" +
                "        let cookies = document.cookie.split(\";\");\n" +
                "        let hasToken = false;\n" +
                "        for( let cookie of cookies) {\n" +
                "            cookie = cookie.trim().toLowerCase();\n" +
                "            let splitted = cookie.split(\"=\");\n" +
                "            if(splitted[0] === \"token\"){\n" +
                "                hasToken = true;\n" +
                "            } else if(splitted[0] === \"lastsearch\"){\n" +
                "                document.getElementById(\"search\").value = splitted[1];\n" +
                "            } else if(splitted[0] === \"name\"){\n" +
                "                name = splitted[1];\n" +
                "            } else if(splitted[0] === \"surname\"){\n" +
                "                surname = splitted[1];\n" +
                "            }\n" +
                "        }\n" +
                "        if(!hasToken){\n" +
                "            window.location = \"/\";\n" +
                "        }\n" +
                "        document.getElementById(\"welcome\").innerText += \" \" + name + \" \" + surname;\n" +
                "        document.getElementById(\"search\").addEventListener(\"keyup\", function (event) {\n" +
                "            if(event.key === \"Enter\"){\n" +
                "                let input = document.getElementById(\"search\").value.trim();\n" +
                "                document.cookie = \"lastsearch=\" + input;\n" +
                "                let list = document.getElementById(\"searchResult\");\n" +
                "                let xhr = new XMLHttpRequest();\n" +
                "                xhr.onreadystatechange = function() {\n" +
                "                    if (this.readyState === 4 && this.status === 200) {\n" +
                "                        while (list.firstChild) {\n" +
                "                            list.removeChild(list.firstChild);\n" +
                "                        }\n" +
                "                        let users = this.responseText.split(\", \");\n" +
                "                        for( let u of users){\n" +
                "                            var node = document.createElement(\"p\");\n" +
                "                            node.innerText = u;\n" +
                "                            list.appendChild(node);\n" +
                "                        }\n" +
                "                    }\n" +
                "                };\n" +
                "                xhr.open(\"Post\",\"http://localhost:8080/search\");\n" +
                "                xhr.send(input);\n" +
                "            }\n" +
                "        })\n" +
                "\n" +
                "    </script>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n");
    }
}
