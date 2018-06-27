package xyz.cocktailize.proximity;

import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

public class Database {

    public Database() {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
        Session session = driver.session();

        session.run("MATCH (n)\n" +
                "OPTIONAL MATCH (n)-[r]-()\n" +
                "DELETE n,r");
        session.run("CREATE (a:xyz.cocktailize.webservices.Cocktail {name: {name}})", parameters("name", "Mojito"));
        session.run("CREATE (b:xyz.cocktailize.webservices.Cocktail {name: {name}})", parameters("name", "Mint Mojito"));
        session.run("MATCH (n:xyz.cocktailize.webservices.Cocktail {name:'Mojito'}), (u:xyz.cocktailize.webservices.Cocktail {name:'Mint Mojito'}) CREATE (n)-[:CONNECTED_TO {distance : 4 }]->(u)");
        /*
        MATCH (n:xyz.cocktailize.webservices.Cocktail {name:'Mojito'}), (u:xyz.cocktailize.webservices.Cocktail {name:'Mint Mojito'}) CREATE (n)-[:CONNECTED_TO {distance : 4 }]->(u)
        session.run("MATCH (n:xyz.cocktailize.webservices.Cocktail)\n" + "DETACH DELETE n");
        */
        StatementResult result = session.run("MATCH (a:Person) WHERE a.name = {name} " + "RETURN a.name AS name, a.title AS title", parameters("name", "Arthur"));
        while (result.hasNext()) {
            Record record = result.next();
            System.out.println(record.get("title").asString() + " " + record.get("name").asString());
        }
        session.close();
        driver.close();
    }

    public static void main(String[] args) {
        Database database = new Database();
    }
}
