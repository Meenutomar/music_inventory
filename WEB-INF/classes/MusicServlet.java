import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MusicServlet extends HttpServlet{

    private MusicBackend backend;

    

    @Override
    public void init() throws ServletException {
        super.init();
        backend = new MusicBackend();
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // super.doPost(req, resp);
            //super.doPost(req, resp);
      
            PrintWriter writer = resp.getWriter();
            Music  music  = new Music ();
            music.setName(req.getParameter("name"));
            music.setQuantity(Integer.parseInt(req.getParameter("quenty")));
            music.setDateString(req.getParameter("date"));
            try {
            int num = backend.registerUser(music);
            writer.println("<html><body><p>Inventory Added Successfully " + num + " !</p>");
            writer.println("<Table border='1'><TR><TD>Name</TD><TD>Quantity</TD><TD>Date of Entry</TD></TR>");;
            List<Music> musicList = backend.showInventory();   
         

            for(Music music1 : musicList){
                writer.println("<TR><TD>");
                writer.println(music1.getName() + "</TD><TD>");
                writer.println(music1.getQuantity() + "</TD><TD>");
                writer.println(music1.getDateString() + "</TD></TR>");
            }
            writer.println("</Table></body></html>");
           
            // RequestDispatcher dispatch = req.getRequestDispatcher("welcome");
            // dispatch.forward(req, resp);
            } catch(MusicException exc){
                writer.println("Error Occurred:: " + exc);
            }
            writer.close();
    }
   
}
