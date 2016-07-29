package com.mycompany.mongodb;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.List;
import org.bson.Document;

public class Turma {
    
    private int id;
    private String curso;
    private List<Pessoa> alunos;

    public Turma(){
        alunos = new ArrayList<>();
    }
    
    public Turma(int id, String curso) {
        this.id = id;
        this.curso = curso;
        this.alunos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public List<Pessoa> getAlunos() {
        return alunos;
    }
    
    public boolean addAluno(Pessoa p){
        return alunos.add(p);
    }
    
    public Document toDocument(){
        
        Document doc = new Document();
        
        doc.append("_id", id);
        doc.append("curso", curso);
        
        List<Document> listaAlunos = new ArrayList<>();
        
        for(Pessoa p : alunos){
            listaAlunos.add(p.toDocument());
        }
        
        doc.append("alunos", listaAlunos);
        
        return doc;                
    }

    public Turma fromDocument(Document doc){
        
        id = doc.getInteger("_id");
        curso = doc.getString("curso");
        
        List<Document> listaAlunos = (List<Document>) doc.get("alunos");
        for(Document d : listaAlunos){
            alunos.add(new Pessoa().fromDocument(d));
        }
        
        return this;
        
    }

    @Override
    public String toString() {
        return "Turma{" + "id=" + id + ", curso=" + curso + ", alunos=" + alunos + '}';
    }
    
}
