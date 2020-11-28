package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {

    private String database;
    private Connection connection;
    private Statement statement;

    public BaseDatos(String db) throws ClassNotFoundException, SQLException {
        this.database = db;
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + database);
        this.statement = connection.createStatement();
    }

    public Connection getConnection() {
        return connection;
    }
    //FUNCION VALIDAR USUARIO
    public List<Usuario> getUsuarioByName(String nombre, String password) throws SQLException {
        ResultSet rs = this.statement.executeQuery("select * from usuario where upper(nombre)='" + nombre.toUpperCase() + "' and password='" + password.toUpperCase() + "'");
        List<Usuario> usuario = new ArrayList();
        while (rs.next()) {
            Usuario temp = new Usuario();
            temp.setIdUsuario(rs.getInt("id_usuario"));
            temp.setIdUsuario(rs.getInt("nombre"));
            temp.setIdUsuario(rs.getInt("password"));
            temp.setIdUsuario(rs.getInt("rol"));
            usuario.add(temp);
        }
        return usuario;
    }
    //FUNCION PARA AÑADIR DOCTOR
    public boolean addDoctor(String nombre, String password, String especialidad) throws SQLException {
        String sql = "insert into doctores(nombre, password, especialidad) "
                + "values (?,?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setString(1, nombre);
        prepStmt.setString(2, password);
        prepStmt.setString(3, especialidad);
        return prepStmt.execute();
    }
    //FUNCION PARA LISTAR DOCTORES
    public void listarDoctor() throws SQLException {
        String sql = "SELECT IdDoctor, nombre, password, especialidad FROM doctores";

        try (Statement stmt = this.connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("IdDoctor") +  "\t" +
                        rs.getString("nombre") + "\t" +
                        rs.getString("password") + "\t" +
                        rs.getString("especialidad"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //FUNCION PARA LISTAR PACIENTES
    public void listarPaciente() throws SQLException {
        String sql = "SELECT IdPaciente, nombre FROM pacientes";

        try (Statement stmt = this.connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("IdPaciente") +  "\t" +
                        rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //FUNCION PARA LISTAR CITAS
    public void listarCitas() throws SQLException {
        String sql = "SELECT Folio,Fecha, Hora FROM citasMedicas";

        try (Statement stmt = this.connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("Folio") +  "\t" +
                        rs.getString("Fecha")+ "\t" +
                        rs.getString("Hora"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //FUNCION PARA LISTAR LA RELACION ENTRE LAS CITAS, DOCTORES Y PACIENTES
    public void listarCitasRelacion() throws SQLException {
        String sql = "SELECT Folio, (SELECT nombre from doctores d where d.IdDoctor = rDP.IdDoctor) AS Doctor,(SELECT nombre from pacientes p where p.IdPaciente = rDP.IdPaciente) AS Paciente, " +
                "(SELECT Fecha from citasMedicas where Folio = IdCita) AS CitaFecha, (SELECT Hora from citasMedicas where Folio = IdCita) AS CitaHora from relacionDoctorPaciente rDP";

        try (Statement stmt = this.connection.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("Folio") +  "\t" +
                        rs.getString("Doctor") +  "\t" +
                        rs.getString("Paciente") + "\t" +
                        rs.getString("CitaFecha") + "\t" +
                        rs.getString("CitaHora"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //BORRAR DOCTOR
    public int deleteDoctor(Integer id) throws SQLException {
        String sql = "delete from doctores where IdDoctor = ? ";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setInt(1, id);
        prepStmt.execute();
        return prepStmt.getUpdateCount();
    }
    //BORRAR PACIENTE
    public int deletePaciente(Integer id) throws SQLException {
        String sql = "delete from pacientes where IdDPaciente = ? ";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setInt(1, id);
        prepStmt.execute();
        return prepStmt.getUpdateCount();
    }
    //BORRAR CITA
    public int deleteCita(Integer id) throws SQLException {
        String sql = "delete from citasMedicas where Folio = ? ";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setInt(1, id);
        prepStmt.execute();
        return prepStmt.getUpdateCount();
    }
    //BORRAR RELACION CITA
    public int deleteRelacion(Integer id) throws SQLException {
        String sql = "delete from relacionDoctorPaciente where Folio = ? ";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setInt(1, id);
        prepStmt.execute();
        return prepStmt.getUpdateCount();
    }
    // pstmt.setString(1, nombre);
      //  pstmt.setString(2, password);
      //  pstmt.setString(3, rol);
      //  return pstmt.execute();

    public boolean addPaciente(String nombre) throws SQLException {
        String sql = "insert into pacientes(nombre) "
                + "values (?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setString(1, nombre);
        return prepStmt.execute();
    }
    public boolean addCita(String fecha, String hora) throws SQLException {
        String sql = "insert into citasMedicas(Fecha, Hora) "
                + "values (?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setString(1, fecha);
        prepStmt.setString(2, hora);
        return prepStmt.execute();
    }
    public boolean addRelacionCitas(Integer idDoctor, Integer idPaciente, Integer idCita) throws SQLException {
        String sql = "insert into relacionDoctorPaciente(IdDoctor, IdPaciente, IdCita) "
                + "values (?,?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setInt(1, idDoctor);
        prepStmt.setInt(2, idPaciente);
        prepStmt.setInt(3, idCita);
        return prepStmt.execute();
    }
    public int deleteCustomer(Usuario usuario) throws SQLException {
        String sql = "delete from Customer where CustomerId = ? ";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        /* prepStmt.setInt(1, customer.getCustomerId());*/
        prepStmt.execute();
        return prepStmt.getUpdateCount();
    }
}
