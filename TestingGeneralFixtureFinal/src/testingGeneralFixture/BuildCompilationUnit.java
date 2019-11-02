package testingGeneralFixture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class BuildCompilationUnit {

	private List<FieldDeclaration> listOfFields=new ArrayList<FieldDeclaration>();
    private List<MethodDeclaration> declaredMethodList = new ArrayList<MethodDeclaration>();
    private List<MethodDeclaration> setupMethodList = new ArrayList<MethodDeclaration>();
    private List<MethodDeclaration> testMethodList = new ArrayList<MethodDeclaration>();
    private List<String> setupFields=new ArrayList<String>();
    private List<String> testFields=new ArrayList<String>();
    Set<String> testMethodFieldSet = new HashSet<String>(); 
    private List<MethodDeclaration> smellyMethodList = new ArrayList<MethodDeclaration>();
    
	public BuildCompilationUnit() {
		
	}
	
	public void buildCompilationUnit(String path, File projectDir) throws FileNotFoundException {
		
		CompilationUnit compilationUnit=null;
	
		FileInputStream fileInputStream;
	 
		if(path!=null) {
		
		fileInputStream = new FileInputStream(path);
		System.out.println("\nFile Path (BuildCompilationUnit) : "+path);
		compilationUnit = JavaParser.parse(fileInputStream);
		}
		
		GenFixVisitor genfixVisitor=new GenFixVisitor(projectDir);

		genfixVisitor.visit(compilationUnit, null);
		genfixVisitor.makeSetupFieldsList();
		genfixVisitor.checkSmell();
    
	}
}
