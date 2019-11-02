package testingGeneralFixture;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ToolMain {
	
	 public static void listClasses(File projectDir) {
		 
	        new DirectoryExplorer((level, path, file) -> path.endsWith(".java") && 
	        		(path.contains("Test") || path.contains("test")),
	        		(level, path, file) -> {
	        			
	            //System.out.println(path);
	            path=projectDir+path;
	            path=path.replace("/", "\\");
	            
	            BuildCompilationUnit bcu=new BuildCompilationUnit();
	            
		        try {
					bcu.buildCompilationUnit(path, projectDir);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        
	            try {
	                new VoidVisitorAdapter<Object>() {
	                    @Override
	                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
	                        super.visit(n, arg);
	                        System.out.println("\nTesting Ended for Class " + n.getName()+".java");
	                        System.out.println("\n*******************************************************\n\n\n");
	                    }
	                }.visit(JavaParser.parse(file), null);
	                
	            } catch (IOException e) {
	                new RuntimeException(e);
	            }
	        }).explore(projectDir);
	    }
	 
	    public static void main(String[] args) {
	    	
	    	JFrame frame = new JFrame("A simple testing tool");
			frame.getContentPane().setBackground(new Color(255, 255, 255));
			frame.getContentPane().setForeground(Color.black);
			frame.setBounds(100, 100, 800, 500);
			
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(null);
	     
	        JButton btnToChooseFile = new JButton("Choose Directory");
	        
	        btnToChooseFile.setForeground(new Color(255, 255, 255));
			btnToChooseFile.setBackground(new Color(55, 67, 67));
 			btnToChooseFile.setBounds(240, 170, 300, 36);
			btnToChooseFile.addActionListener(null);
			
	        frame.setVisible(true);
	        
	        frame.getContentPane().add(btnToChooseFile);
			
			JLabel lblGeneralFixture = new JLabel("Detect General Fixture Smell");
			lblGeneralFixture.setFont(new Font("Times New Roman", Font.BOLD, 25));
			lblGeneralFixture.setBounds(240, 41, 323, 43);
			frame.getContentPane().add(lblGeneralFixture);
			
			frame.getContentPane().add(btnToChooseFile);
			btnToChooseFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					
					System.out.println("button clicked\n");
					
					JFileChooser jFileChooserc = new JFileChooser();
		    		jFileChooserc.setMultiSelectionEnabled(true);
		    		jFileChooserc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        
					if (jFileChooserc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		                
						File[] files = jFileChooserc.getSelectedFiles();
		                
						File projectDir = new File(jFileChooserc.getSelectedFile().toString());
		                
		                System.out.println("\nProjectDir: "+projectDir+"\n"
		                		+ "***************************************\n");
					 
		                listClasses(projectDir);
					}
				
				}     
				     
		    } );
	         
	    }
}
 
