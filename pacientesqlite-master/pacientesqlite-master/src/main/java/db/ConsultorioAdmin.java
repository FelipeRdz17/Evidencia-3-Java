package db;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class ConsultorioAdmin {

    static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        int seleccion;
        String user = "";
        String password = "";
        String nombreDoctor = "";
        String contrasenaDoctor = "";
        String nombrePaciente = "";
        String contrasenaPaciente = "";
        String fechaCita = "";
        String horaCita = "";
        Integer rolDoctor = 2;
        Integer rolPaciente = 3;
        Integer usuarioBorrar;
        String especialidadDoctor = "";
        Integer idDoctor;
        Integer idPaciente;
        Integer idCita;
        Integer pacienteBorrar;
        Integer citaBorrar;
        Integer relacionBorrar;
        BaseDatos persist = new BaseDatos("consultorio.db");

        //LLAMAR A CLASE VALIDARSTRING PARA LOS INPUTS
        validarStrings vString = new validarStrings();
        try (Scanner input = new Scanner(System.in)) {
            input.useDelimiter("\n");
            //VALIDAR LOGIN
            System.out.println("Ingrese su usuario y contraseña para iniciar");
            System.out.println("Usuario:");
            user = input.nextLine();
            System.out.println("Contraseña:");
            password = input.nextLine();
            List<Usuario> usuario = persist.getUsuarioByName(user, password);
            if (!usuario.isEmpty()) {
                while (true) {
                    //OPCIONES DEL MENÚ
                    System.out.println("(1) Dar de alta doctores.");
                    System.out.println("(2) Listar doctores.");
                    System.out.println("(3) Dar de alta pacientes.");
                    System.out.println("(4) Listar pacientes.");
                    System.out.println("(5) Crear una cita con fecha y hora.");
                    System.out.println("(6) Listar citas.");
                    System.out.println("(7) Relacionar una cita con un doctor y un paciente.");
                    System.out.println("(8) Listar citas programadas.");
                    System.out.println("(9) Borrar doctor.");
                    System.out.println("(10) Borrar paciente.");
                    System.out.println("(11) Borrar cita.");
                    System.out.println("(12) Borrar relación de una cita programada.");
                    System.out.println("(0) Salir.");
                    System.out.println("\nPor favor ingrese una opción: ");
                    // Fin de Menu
                    // Try Anidado
                    try {
                        // Asigna token Integer parseado
                        seleccion = input.nextInt();
                        switch (seleccion) {
                            case 0:
                                System.out.println("Saliendo..");
                                logger.info("Saliendo...");
                                return;
                            case 1:
                                //CREAR DOCTOR
                                nombreDoctor = vString.pedirString("Escribe el nombre del doctor: ");
                                contrasenaDoctor = vString.pedirString("Escribe la contraseña del doctor: ");
                                especialidadDoctor = vString.pedirString("Escribe la especialidad del doctor: ");
                                persist.addDoctor(nombreDoctor, contrasenaDoctor, especialidadDoctor);
                                break;
                            case 2:
                                //LISTAR DOCTORES
                                System.out.println("ID---NOMBRE---PASSWORD---ESPECIALIDAD");
                                persist.listarDoctor();
                                break;
                            case 3:
                                //CREAR PACIENTE
                                nombrePaciente = vString.pedirString("Escribe el nombre del paciente: ");
                                persist.addPaciente(nombrePaciente);
                                break;
                            case 4:
                                //LISTAR PACIENTES
                                System.out.println("ID-------NOMBRE");
                                persist.listarPaciente();
                                break;
                            case 5:
                                //CREAR CITAS
                                fechaCita = vString.pedirString("Escribe la fecha de la cita: ");
                                horaCita = vString.pedirString("Escribe la hora de la cita: ");


                                persist.addCita(fechaCita, horaCita);
                                break;
                            case 6:
                                //LISTAR CITAS
                                System.out.println("ID---FECHA---HORA");
                                persist.listarCitas();
                                break;
                            case 7:
                                //CREAR RELACION CITAS
                                idDoctor = vString.pedirIntPositivo("Escribe el ID del doctor: ");
                                idPaciente = vString.pedirIntPositivo("Escribe el ID del paciente: ");
                                idCita = vString.pedirIntPositivo("Escribe el ID de la cita: ");

                                persist.addRelacionCitas(idDoctor, idPaciente,idCita);
                                break;
                            case 8:
                                //LISTAR CITAS RELACIONADAS
                                System.out.println("ID---DOCTOR-----PACIENTE----------FECHA-----HORA");
                                persist.listarCitasRelacion();
                                break;
                            case 9:
                                //BORRAR DOCTOR
                                usuarioBorrar = vString.pedirIntPositivo("Escribe el ID del doctor a borrar: ");;

                                persist.deleteDoctor(usuarioBorrar);
                                break;
                            case 10:
                                //BORRAR PACIENTE
                                pacienteBorrar = vString.pedirIntPositivo("Escribe el ID del paciente a borrar: ");;

                                persist.deletePaciente(pacienteBorrar);
                                break;
                            case 11:
                                //BORRAR CITA
                                citaBorrar = vString.pedirIntPositivo("Escribe el ID de la cita a borrar: ");;

                                persist.deleteCita(citaBorrar);
                                break;
                            case 12:
                                //BORRAR RELACIÓN
                                relacionBorrar = vString.pedirIntPositivo("Escribe el ID de la cita a borrar: ");;

                                persist.deleteRelacion(relacionBorrar);
                                break;
                            default:
                                System.err.println("Opción inválida.");
                                logger.error("Opción inválida: {}", seleccion);
                                break;
                        }

                    } catch (Exception ex) {
                        logger.error("{}: {}", ex.getClass(), ex.getMessage());
                        System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
                        input.next();
                    }
                }
            } else {
                //USUARIO NO VÁLIDO
                System.out.println("No tiene autorización");
            }
        } catch (Exception ex) {
            logger.error("{}: {}", ex.getClass(), ex.getMessage());
            System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
        } finally {
            persist.getConnection().close();
        }
    }





}
