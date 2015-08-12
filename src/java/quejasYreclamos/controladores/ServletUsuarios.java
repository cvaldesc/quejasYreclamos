/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quejasYreclamos.controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import quejasYreclamos.modelo.ConnectionModel;
import quejasYreclamos.modelo.dao.UsuariosJpaController;
import quejasYreclamos.modelo.dao.exceptions.IllegalOrphanException;
import quejasYreclamos.modelo.dao.exceptions.NonexistentEntityException;
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
        try {
            processAction(request,response);
        } catch (Exception error) {
            
            error.printStackTrace();
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
        
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
    public void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, Exception{
    	String action = request.getParameter("action");
      
    	if(action.equals("guardar") == true ){
    		saveUsuario(request,response);
    	}
        else if(action.equals("sol_agregar")){
            ConnectionModel connection = ConnectionModel.getConnection();
            UsuariosJpaController addUserAgregar = new UsuariosJpaController(connection.getFactoryConnection());
           
            int Id = addUserAgregar.getUsuariosCount();
            int nextId;
            if(Id != 0){
                nextId = addUserAgregar.nextId();
                System.err.println("ID:"+ nextId);
                request.setAttribute("next_Id", nextId);  
                request.setAttribute("action", "mos_agregar");
                request.getRequestDispatcher("/web/usuarios/crud_usuario.jsp?action=mos_agregar").forward(request, response);
            }else{
               nextId = addUserAgregar.lastId();
                request.setAttribute("next_Id", nextId);  
                request.setAttribute("action", "mos_agregar");
                request.getRequestDispatcher("/web/usuarios/crud_usuario.jsp?action=mos_agregar").forward(request, response);
            }
           
                  /*  int nextId = addUserAgregar.nextId();
                    System.err.println("ID:"+ nextId);
                    request.setAttribute("next_Id", nextId);  
                    request.setAttribute("action", "mos_agregar");
                    request.getRequestDispatcher("/web/usuarios/crud_usuario.jsp?action=mos_agregar").forward(request, response);
              */
               
       

            
                
        }
        else if(action.equals("sol_buscar")){
                request.setAttribute("action", "mos_buscar");
                request.getRequestDispatcher("/web/usuarios/crud_usuario.jsp?action=mos_buscar").forward(request, response);
        }
        else if(action.equals("buscar") == true){
           searchUsuario(request, response);
        }
        else if (action.equals("editar") == true){
                editUser(request, response);
        }
        else if(action.equals("eliminar")== true){
                deleteUser(request,response); 
        }
        else if(action.equals("sol_listar")){
                ListUser(request,response); 
        }
        else if(action.equals("inicio")){
              
            response.sendRedirect(request.getContextPath()+"/web/usuarios/index.jsp");
        }
        else if(action.equals("eliminar_listado") == true){
              
             deleteUser(request,response); 
        }
    }
    private void ListUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, Exception {
        try {
            ConnectionModel connection = ConnectionModel.getConnection();
            UsuariosJpaController addUser = new UsuariosJpaController(connection.getFactoryConnection());
            
            List<Usuarios> users = addUser.findUsuariosEntities();
            if(users != null){
                request.setAttribute("Listados_Usuarios", users);
                request.setAttribute("mensaje", "listado con exito");
                request.getRequestDispatcher("/web/usuarios/listar.jsp").forward(request, response);
            }else{
                request.setAttribute("mensaje", "no hay usuarios en la base de datos");
                request.setAttribute("estilo", "info");
                request.setAttribute("url", "ServletUsuarios");
                request.setAttribute("nextAction", "inicio");
                request.getRequestDispatcher("/error.jsp").forward(request, response); 
            }
        } catch (Exception error) {
            request.setAttribute("mensaje", "ocurrio un error desconocido");
            request.setAttribute("estilo", "info");
            request.setAttribute("url", "ServletUsuarios");
            request.setAttribute("nextAction", "inicio");
            throw error;
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
                    request.setAttribute("url", "ServletUsuarios");
                    request.setAttribute("nextAction", "sol_agregar");
                    error.printStackTrace();
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }else{
                    request.setAttribute("mensaje", "ocurrio un error desconocido");
                    request.setAttribute("estilo", "info");
                    request.setAttribute("url", "ServletUsuarios");
                    request.setAttribute("nextAction", "sol_agregar");
                    error.printStackTrace();
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            }
    }
    public void searchUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String id = request.getParameter("id");

        ConnectionModel connection = ConnectionModel.getConnection();
        UsuariosJpaController addUserBuscar = new UsuariosJpaController(connection.getFactoryConnection());
        try {
           
            
            Usuarios user = addUserBuscar.findUsuarios(Integer.valueOf(id).intValue());

             request.setAttribute("user", user);
             request.setAttribute("action", "mos_buscar");
             request.setAttribute("mensaje", "usuario "+ user.getNombre()+" fue encontrado con exito" );
             request.getRequestDispatcher("/web/usuarios/crud_usuario.jsp").forward(request, response);

        } catch (Exception error) {
             request.setAttribute("mensaje", "usuario no existe");
             request.setAttribute("estilo", "info");
             request.setAttribute("url", "ServletUsuarios");
             request.setAttribute("nextAction", "sol_buscar");
             error.printStackTrace();
             request.getRequestDispatcher("/error.jsp").forward(request, response);
         }
            
    }
    public void editUser(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException, Exception{

        String id = request.getParameter("id");
        String cedula = request.getParameter("cedula");
        String password = request.getParameter("password");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");

        Usuarios user = new Usuarios();
        user.setUsuariosId(Integer.valueOf(id));
        user.setCedula(cedula);
        user.setPassword(password);
        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setEmail(email);
        user.setTelefono(telefono);

        ConnectionModel connection = ConnectionModel.getConnection();
        UsuariosJpaController addUser = new UsuariosJpaController(connection.getFactoryConnection()); 
        
        try {
            addUser.edit(user);
            
            request.setAttribute("action", "mos_buscar");
            request.setAttribute("user", user);
            request.setAttribute("mensaje", "usuario "+ user.getUsuariosId()+" fue editado con exito" );
            request.getRequestDispatcher("/web/usuarios/crud_usuario.jsp").forward(request, response);

        } catch (NonexistentEntityException error) {
            request.setAttribute("mensaje", "usuario"+ user.getUsuariosId() + "no esta registrado en el sistema");
            request.setAttribute("estilo", "info");
            request.setAttribute("url", "ServletUsuarios");
            request.setAttribute("nextAction", "sol_buscar");
            //throw new Exception("usuario"+ user.getUsuariosId() + "no esta registrado en el sistema");
            throw error;
            
        } catch(IllegalOrphanException error ){
            request.setAttribute("mensaje", "error en el BD del sistema");
            request.setAttribute("estilo", "info");
            request.setAttribute("url", "ServletUsuarios");
            request.setAttribute("nextAction", "sol_buscar");
            throw error;
            
        }catch (Exception error) {
            request.setAttribute("mensaje", "error desconocido en el sistema.");
            request.setAttribute("estilo", "info");
            request.setAttribute("url", "ServletUsuarios");
            request.setAttribute("nextAction", "sol_buscar");
            throw error;
        }
    }
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IllegalOrphanException, NonexistentEntityException, ServletException, IOException, Exception {
        String id = request.getParameter("id");
        ConnectionModel connection = ConnectionModel.getConnection();
        UsuariosJpaController addUser = new UsuariosJpaController(connection.getFactoryConnection()); 
        try{
            addUser.destroy(Integer.valueOf(id));
            request.setAttribute("user", null);
            request.setAttribute("action", "mos_buscar");
            request.setAttribute("mensaje", "el Usuario fue eliminado con exito" );
            String action = (String) request.getParameter("action");
            if(action.equals("eliminar_listado")){
                ListUser(request, response);
            }
                request.getRequestDispatcher("/web/usuarios/crud_usuario.jsp").forward(request, response);
           
           
        }catch (Exception error) {
            request.setAttribute("mensaje", "error desconocido en el sistema.");
            request.setAttribute("estilo", "info");
            request.setAttribute("url", "ServletUsuarios");
            request.setAttribute("nextAction", "sol_buscar");
            throw error;
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
