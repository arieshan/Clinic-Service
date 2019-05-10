import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.*;

public class Main {
	public static void main(String args[]) {
		sparqlTest();
	}

	static void sparqlTest() {
		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader()); 
		Model model = FileManager.get().loadModel("/Users/shilunli/Documents/data.rdf");
		
		String queryString =
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
				"PREFIX owl:<http://www.w3.org/2002/07/owl#>"+ "PREFIX n:<http://edu.stevens.cs548/clinic#/>"+ 
				"SELECT ?a WHERE {?a rdf:type n:RadiologyPatient }";
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
			try {
				ResultSet results = qexec.execSelect(); while (results.hasNext() ) {
					QuerySolution soln = results.nextSolution();
					Resource name = soln.getResource("a") ;
					System.out.println(name); }
			} finally { 
				qexec.close();
			} 
	}
}
