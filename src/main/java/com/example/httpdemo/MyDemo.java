package com.example.httpdemo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "MyDemo", value = "/MyDemo")
public class MyDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String where=request.getHeader("referer");
        if(where!=null)
        {
            if(where.contains("canNocan"))
            {
                String abbr;
                abbr=request.getParameter("abbr");
                ConnectDB connectDB=new ConnectDB();
                connectDB.init();
                String word=connectDB.getWord(abbr);
                if(word==null){word="矮油，这个星球上没有你想找的呦，如果你对这个词有了解，快去提交给我们吧！";}
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write(word);
                connectDB.close();
            }
            else if(where.contains("HotWord"))
            {
                String word;
                word=request.getParameter("word");
                ConnectDB connectDB=new ConnectDB();
                connectDB.init();
                String description=connectDB.getMeaning(word);
                if(description==null){description="矮油，这个星球上没有你想找的呦，如果你对这个词有了解，快去提交给我们吧！";}
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write(description);
                connectDB.close();
            }
            else if(where.contains("input"))
            {
                String abbr,word,description;
                abbr=request.getParameter("abbr");
                word=request.getParameter("word");
                description=request.getParameter("description");
                ConnectDB connectDB=new ConnectDB();
                connectDB.init();
                boolean ok=connectDB.writeRecord(abbr,word,description);
                connectDB.close();
                if(ok)
                {
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().write("success");
                }
                else
                {
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().write("failure");
                }
            }
        }
        else
        {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("警告！！！&nbsp;您试图非法访问本站，我们已记录保存证据，同学请你自重！");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
