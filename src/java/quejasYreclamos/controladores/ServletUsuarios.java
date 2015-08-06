/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quejasYreclamos.controladores;

import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import quejasYreclamos.modelo.ConnectionModel;
import quejasYreclamos.modelo.dao.UsuariosJpaController;
import quejasYreclamos.modelo.entidades.Usuarios;

/**
 *
 * @author cvaldes
 */
public class ServletUsuarios extends HttpServlet {

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
       
       
        //System.out.println();
        processAction(request,response);
        /*try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletUsuarios</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletUsuarios at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }*/
    }
    public void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    	String action = request.getParameter("action");
      
    	if(action.equals("guardar") == true ){
    		saveUsuario(request,response);
    	}
        else if(action.equals("sol_agregar")){
           
                ConnectionModel connection = ConnectionModel.getConnection();
                UsuariosJpaController addUserAgregar = new UsuariosJpaController(connection.getFactoryConnection());
                int nextId = addUserAgregar.nextId();
                System.err.println("ID:"+ nextId);
                request.setAttribute("next_Id", nextId);
                request.setAttribute("action", "mos_agregar");
                
                request.getRequestDispatcher("/web/usuarios/crud_usuario.jsp?action=mos_agregar").forward(request, response);
        }
    }
    public void saveUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    	String id = request.getParameter("id");
        String cedula = request.getParameter("cedula");
    	String password = request.getParameter("password");
    	String nombre = request.getParameter("nombre");
    	String apellido = request.getParameter("apellido");
    	String email = request.getParameter("email");
    	String telefono = request.getParameter("telefono");
    	
    	Usuarios usuario = new Usuarios();
    	usuario.setCedula(cedula);
    	usuario.setPassword(password);
    	usuario.setNombre(nombre);
    	usuario.setApellido(apellido);
    	usuario.setEmail(email);
    	usuario.setTelefono(telefono);
            
            try {
                ConnectionModel connection = ConnectionModel.getConnection();
                UsuariosJpaController addUser = new UsuariosJpaController(connection.getFactoryConnection());
                addUser.create(usuario);
                int nextId = addUser.nextId();
                System.err.println("ID:"+ nextId);
                request.setAttribute("next_Id", nextId);
                request.setAttribute("action", "mos_agregar");
                request.setAttribute("mensaje", "usuario "+ usuario.getUsuariosId()+" fue agregado con exito" );
                 request.getRequestDispatcher("/web/usuarios/crud_usuario.jsp?action=sol_agregar").forward(request, response);
                
                //response.sendRedirect("/quejasYreclamos/web/usuarios/agregar.jsp?mensaje=OK, usuario fue agregado al sistema");
            } catch (Exception error) {
                // TODO Auto-generated catch block
                //response.sendRedirect("/quejasYreclamos/web/usuarios/agregar.jsp?mensaje=ERROR , usuario No fue agregado al sistema");
                if(error instanceof PersistenceException){
                    request.setAttribute("mensaje", "problemas con el servidor de BD");
                    request.setAttribute("estilo", "error");
                    request.setAttribute("url", "/ServletUsuarios");
                    request.setAttribute("nextAction", "sol_agregar");
                    error.printStackTrace();
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }else{
                    request.setAttribute("mensaje", "ocurrio un error desconocido");
                    request.setAttribute("estilo", "info");
                    request.setAttribute("url", "/ServletUsuarios");
                    request.setAttribute("nextAction", "sol_agregar");
                    error.printStackTrace();
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            }
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
