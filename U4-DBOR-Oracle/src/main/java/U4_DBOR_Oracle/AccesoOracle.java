package U4_DBOR_Oracle;

import java.sql.*;

public class AccesoOracle {
	private Connection con;

	void abrirConexion() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "SYS as SYSDBA", "1234");

			System.out.println("Conexion OK");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void cerrarConexion() {
		try {
			System.out.println("Conexion cerrada");
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void mostrarContactos() {
		try {

			Statement st = con.createStatement();
			ResultSet resul = st.executeQuery("SELECT c.nombre, c.telefono FROM contactos c");
			System.out.println("INFORMACION DE CONTACTOS--------------");
			while (resul.next()) {

				System.out.printf("\nNOMBRE: %s\nTELEFONO: ", resul.getString(1), resul.getString(2));
			}
			System.out.println("\n--------------");
			resul.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void crearTablaMisAlumnos() {
		try {

			Statement st = con.createStatement();
			ResultSet resul = st.executeQuery("CREATE TABLE MISALUMNOS OF ESTUDIANTE");

			System.out.println("TABLA CREADA CON ÉXITO");
			resul.close();
			st.close();
		} catch (Exception e) {
			System.out.println("ERROR, NO SE HA PODIDO CREAR LA TABLA");

			e.printStackTrace();
		}
	}

	void insertarAlumno() { 
		try {

			Statement st = con.createStatement();
			ResultSet resul = st.executeQuery(
					"INSERT INTO MISALUMNOS VALUES(ESTUDIANTE('01B', PERSONA('Fulanito Pérez','967111112')))");

			System.out.println("ALUMNO INSERTADO CON ÉXITO");
			resul.close();
			st.close();
		} catch (Exception e) {
			System.out.println("ERROR, NO SE HA PODIDO INSERTAR EL ALUMNO");

			e.printStackTrace();
		}
	}

	void borrarAlumno(String nombre) { //este método no funciona
	    try {
	       
	        String sql = "DELETE FROM MISALUMNOS WHERE (SELECT id_estudiante FROM MISALUMNOS WHERE datos_personales.getnombre() = ? ) "
	        		+ "= (SELECT m.id_estudiante FROM MISALUMNOS m WHERE m.datos_personales.getnombre() = ? )";

	        PreparedStatement pst = con.prepareStatement(sql);

	     
	        pst.setString(1, nombre);
	        pst.setString(2, nombre); 

	       
	        int filasAfectadas = pst.executeUpdate();

	        
	        if (filasAfectadas > 0) {
	            System.out.println("ALUMNO BORRADO CON ÉXITO");
	        } else {
	            System.out.println("NO SE ENCONTRÓ NINGÚN ALUMNO CON EL NOMBRE ESPECIFICADO");
	        }

	       
	        pst.close();
	    } catch (SQLException e) {
	        System.out.println("ERROR: NO SE HA PODIDO BORRAR EL ALUMNO");
	        e.printStackTrace();
	    }
	}


	
	void buscarTelefonoAlumno(String nombre) {
	    try {
	        
	        String sql = "SELECT m.datos_personales.telefono " +   "FROM MISALUMNOS m " +  "WHERE m.datos_personales.nombre = ?";

	        PreparedStatement pst = con.prepareStatement(sql);

	        
	        pst.setString(1, nombre);

	        
	        ResultSet rs = pst.executeQuery();

	     
	        if (rs.next()) {
	            String telefono = rs.getString(1);
	            System.out.println("TELÉFONO DE " + nombre + ": " + telefono);
	        } else {
	            System.out.println("NO SE ENCONTRÓ NINGÚN ALUMNO CON EL NOMBRE ESPECIFICADO");
	        }

	       
	        rs.close();
	        pst.close();
	    } catch (SQLException e) {
	        System.out.println("ERROR: NO SE HA PODIDO BUSCAR EL TELÉFONO DEL ALUMNO");
	        e.printStackTrace();
	    }
	}
	
	void mostrarAlumnos() {
	    try {
	        Statement st = con.createStatement();
	        ResultSet resul = st.executeQuery("SELECT m.datos_personales.nombre, m.datos_personales FROM MISALUMNOS m");
	        System.out.println("LISTADO DE ALUMNOS--------------");
	        while (resul.next()) {
	            
	            String nombre = resul.getString(1);

	            
	            oracle.sql.STRUCT datosPersonalesStruct = (oracle.sql.STRUCT) resul.getObject(2);

	            
	            Object[] datosPersonalesAttrs = datosPersonalesStruct.getAttributes();
	            
	            
	            String telefono = (String) datosPersonalesAttrs[1]; 

	            
	            System.out.println("\nNombre: " + nombre + "\nTeléfono: " + telefono);
	        }
	        System.out.println("\n--------------");
	        resul.close();
	        st.close();
	    } catch (Exception e) {
	    	System.out.println("ERROR: NO SE HA PODIDO MOSTRAR LA LISTA DE ALUMNOS");
	        e.printStackTrace();
	    }
	}
	
	void mostrarAdmitidos() {
	    try {
	        Statement st = con.createStatement();
	        ResultSet resul = st.executeQuery("SELECT a.dia, a.matriculado FROM ADMITIDOS a");
	        System.out.println("INFORMACIÓN DE ADMITIDOS--------------");
	        while (resul.next()) {
	           
	            Date dia = resul.getDate(1);

	            
	            oracle.sql.STRUCT matriculadoStruct = (oracle.sql.STRUCT) resul.getObject(2);

	            
	            Object[] matriculadoAttrs = matriculadoStruct.getAttributes();

	           
	            String nombre = (String) matriculadoAttrs[0];
	            
	           
	            System.out.println("\nFecha de Matriculación: " + dia.toString() + "\nNombre: " + nombre);
	        }
	        System.out.println("\n--------------");
	        resul.close();
	        st.close();
	    } catch (Exception e) {
	    	System.out.println("ERROR: NO SE HA PODIDO MOSTRAR LA LISTA DE ADMITIDOS");
	        e.printStackTrace();
	    }
	}










	
	


}
