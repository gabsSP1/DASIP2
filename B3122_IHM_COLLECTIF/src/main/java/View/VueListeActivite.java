package View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Activite;
import metier.service.ServiceException;
import metier.service.ServiceMetier;

/**
 *
 * @author gspecq
 */
@WebServlet(urlPatterns = {"/VueListeActivite"})
public class VueListeActivite extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ActionCasDusage</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Liste des activités</h1>");
            List<Activite> l;
            l = (List<Activite>) request.getAttribute( "Activites" );
            out.println("<ul>");
            for (int i=0; i<l.size(); i++)
            {
                String s = "<li>[";
                s+=l.get(i).getId();
                s+="] ";
                s += l.get(i).getDenomination();
                s += " <a href='hey.html'>voir</a>";
                s += "</li>";
                out.println(s);
            }
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public static void printListeActivites(PrintWriter out, List<Activite> l)
    {
        /*JsonArray jlist = new JsonArray();
        for(Activite a : l){
            JsonObject jact = new JsonObject();
            jact.addProperty("nom", a.getDenomination() );
            jact.addProperty("nbParticipants", a.getNbParticipants() );
            jact.addProperty("id", a.getId());
            jact.addProperty("parEquipe",a.isParEquipe());
            jlist.add(jact);
        }
        JsonObject container = new JsonObject();
        container.add("activites",jlist);
        
        //ecriture
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(container);*/
        out.println("dez");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
