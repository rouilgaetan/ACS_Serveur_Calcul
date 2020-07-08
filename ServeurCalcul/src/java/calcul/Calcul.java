package calcul;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static java.lang.Math.sqrt;

/**
 *
 * @author grouil
 */
@WebServlet(urlPatterns = {"/Calcul"})
public class Calcul extends HttpServlet {

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
        String operande1 = request.getParameter("operande1");
        String operande2 = request.getParameter("operande2");
        String operateur = request.getParameter("operateur");
        Response reponse = new Response(0, "no error");//Création de l'objet data class Response
        //Test paramètres existant
        if(operateur == null){
            reponse.setError("No operator");
        }
        else if (operande1==null ||operande1.equals("")){
            reponse.setError("No operande");
        }
        else{
            try{
                //Test si un seul opérande est présent.
                if(operande2==null || operande2.equals("")){
                    //Gestion des int ou des doubles
                    if(operande1.contains(".")){
                        switch(operateur.toUpperCase()){
                            case "PLUS" : reponse.setResult(+Double.parseDouble(operande1));
                                break;
                            case "MINUS" : reponse.setResult(-Double.parseDouble(operande1));
                                break;
                            case "SQRT" : reponse.setResult(sqrt(Double.parseDouble(operande1)));
                                break;
                            case "INV" : reponse.setResult(1/(Double.parseDouble(operande1)));
                                break;
                            default : reponse.setError("Non-supported operator");
                                break;
                        }
                    }
                    else{
                        switch(operateur.toUpperCase()){
                            case "PLUS" : reponse.setResult(+Integer.parseInt(operande1));
                                break;
                            case "MINUS" : reponse.setResult(-Integer.parseInt(operande1));
                                break;
                            case "SQRT" : reponse.setResult((int)sqrt(Double.parseDouble(operande1)));
                                break;
                            case "INV" : reponse.setResult(1/(Integer.parseInt(operande1)));
                                break;
                            default : reponse.setError("Non-supported operator");
                                break;
                        }
                    }
                }
                else{
                    //Opérateur binaire gestion int ou doubles.
                    if(operande1.contains(".")||operande2.contains(".")){
                        switch(operateur.toUpperCase()){
                            case "PLUS" : reponse.setResult(Double.parseDouble(operande1)+Double.parseDouble(operande2));
                                break;
                            case "MINUS" : reponse.setResult(Double.parseDouble(operande1)-Double.parseDouble(operande2));
                                break;
                            case "MULT" : reponse.setResult(Double.parseDouble(operande1)*Double.parseDouble(operande2));
                                break;
                            case "DIV" : try{
                                            reponse.setResult(Double.parseDouble(operande1)/Double.parseDouble(operande2));
                                        }catch(ArithmeticException e){
                                            reponse.setError("Divided by zero ? I don't think so... : "+e);
                                        }
                                break;
                            default : reponse.setError("Non-supported operator");
                                break;
                        }
                    }
                    else{
                        switch(operateur.toUpperCase()){
                            case "PLUS" : reponse.setResult(Integer.parseInt(operande1)+Integer.parseInt(operande2));
                                break;
                            case "MINUS" : reponse.setResult(Integer.parseInt(operande1)-Integer.parseInt(operande2));
                                break;
                            case "MULT" : reponse.setResult(Integer.parseInt(operande1)*Integer.parseInt(operande2));
                                break;
                            case "DIV" :  try{
                                            reponse.setResult(Integer.parseInt(operande1)/Integer.parseInt(operande2));
                                        }catch(ArithmeticException e){
                                            reponse.setError("Divided by zero ? I don't think so... : "+e);
                                        }
                                break;
                            default : reponse.setError("Non-supported operator");
                                break;
                        }
                    }
                }
            }
            catch(NumberFormatException e){
                reponse.setError("Int or double please "+e);
            }
        }
        
        PrintWriter out = response.getWriter();
        //conversion de Response en Json.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String reponse_json = gson.toJson(reponse);
        response.setContentType("application/json;charset=UTF-8");
        out.println(reponse_json);
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
