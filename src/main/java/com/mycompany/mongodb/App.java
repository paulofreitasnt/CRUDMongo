package com.mycompany.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

public class App {

    public static void main(String[] args){
        
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        
        MongoDatabase database = mongoClient.getDatabase("testes");
        
        MongoCollection<Document> collection = database.getCollection("Pessoa");
        
        //Inserção - Pessoa
        Pessoa p = new Pessoa("111.111.111-01","Fulano",20);
        collection.insertOne(p.toDocument());
        

        // Busca - Pessoa
        String cpf = "111.111.111-01";

        MongoCursor <Document> cursor = collection.find(eq("_id",cpf)).iterator();
        
        if(cursor.hasNext()){
            Pessoa p2 = new Pessoa().fromDocument(cursor.next());
            System.out.println(p2);
        }else System.out.println("Pessoa não encontrada");
        
        // Atualização - Pessoa
        Pessoa p2 = new Pessoa("111.111.111-01","Cicrano",21);
        UpdateResult result = collection.updateOne(eq("_id",p2.getCpf()),
                new Document("$set", new Document("nome",p2.getNome()).
                        append("idade", p2.getIdade())));        
        
        if(result.getModifiedCount()>0) System.out.println("Modificado com sucesso");
        else System.out.println("Falha ao modificar");
        
        //Remoção - Pessoa
        collection.findOneAndDelete(eq("_id",cpf));
                
        
        MongoCollection<Document> collection2 = database.getCollection("Turma");
        
        
        Turma turma = new Turma(1, "ADS");
        turma.addAluno(new Pessoa("111.111.111-01","João",20));
        turma.addAluno(new Pessoa("222.222.222-02","Maria",21));
        
        // Inserção - Turma
        collection2.insertOne(turma.toDocument());
        
        // Busca - Turma
        MongoCursor <Document> cursor2 = collection2.find(eq("_id",1)).iterator();
              
        if(cursor2.hasNext()){
            Turma t = new Turma().fromDocument(cursor2.next());
            System.out.println(t);
        }
        
        mongoClient.close();
        
    }
    
}
