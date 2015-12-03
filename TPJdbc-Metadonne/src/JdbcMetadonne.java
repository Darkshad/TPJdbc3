import java.io.Console;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JdbcMetadonne {
	private Connection connect;
	private DatabaseMetaData dmd;

	public JdbcMetadonne(String login,String password) throws SQLException {
		this.connect = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fil.univ-lille1.fr:1521:filora",login,password);
		this.dmd = this.connect.getMetaData();
	}

	public void seDeconnecter() throws SQLException {
		this.connect.close();
	}
	
	public void listTables() throws SQLException {
		String[] type = {"TABLE","VIEW"}; 
		
		ResultSet rs = this.dmd.getTables(null,"CORNAIRE", "%",type) ;
		System.out.println("********Liste des tables***********");
		while(rs.next()) {
			System.out.println(rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(2) );
		}
	}
	
	public void listProcedure() throws SQLException {
		ResultSet rs = this.dmd.getProcedures(null, "CORNAIRE", "%");
		System.out.println("********Liste des procédures***********");
		while(rs.next()) {
			System.out.println(rs.getString(3) + " " + rs.getString(2)  );
		}
	}
		
	public void desc(String name) throws SQLException {
			ResultSet rs2 = this.dmd.getColumns(null, "CORNAIRE", name, null);
			System.out.println(name);
			while(rs2.next()) {
				String nullable;
				if(rs2.getInt(11) == 1) 
					nullable = "NULL";
				else
					nullable = "NOT NULL";
					
				System.out.println(rs2.getString(4) + " " + rs2.getString(6) + " " + rs2.getString(5) + " " + nullable + " ");
			}
			System.out.println("");
	}
	
	public static void main(String [] args) throws SQLException {
		Console console = System.console();
		String name = console.readLine("[Identifiant Oracle]: ");
		char[] pdt =console.readPassword("[Mot de passe]: ");
	  	String passdata = new String(pdt);
	  	JdbcMetadonne bdd = null;
		try {
			bdd = new JdbcMetadonne(name,passdata);
			//bdd.listTables();
		    //bdd.listProcedure();
			bdd.desc("TP_LIVRE");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			bdd.seDeconnecter();
		}
			
	}
}

