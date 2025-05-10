package com.techpalle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
 * Project name : JDBC Console Project 
 * Description : The main purpose of this project is to explore 
 *               JDBC api's for performing DML operations.
 */
public class JDBCProject {
	public static int count = 1; 

	public static void main(String[] args) {
		//call creating function - so that it creates table
		Dao d = new Dao();
		/*
		 * lets ask user for input..
		 */
		Scanner sc = new Scanner(System.in);
		int option = 0;
		System.out.println("----JDBC CONSOLE PROJECT WELCOMES YOU-----\n");
		do {
			System.out.println("-----CHOOSE CORRECT OPTION-----");
			System.out.println("1 : creating table");
			System.out.println("2 : inserting into table");
			System.out.println("3 : update the row table");
			System.out.println("4 : delete a row of table");
			System.out.println("5 : read one row");
			System.out.println("6 : read all rows");
			System.out.println("0 : exit");
			
			option = sc.nextInt();//what ever user enters that goes to option variable
			switch(option)
			{
			case 1:
				if(count == 1)
				{
					d.createTable();
					count++;
				}
				else
				{
					System.out.println("YOU CANT CREATE A TABLE MULTIPLE TIMES.");
				}
				break;
			case 2:
				System.out.println("enter eno ename esal sequentially...");
			    int eno = sc.nextInt();//user enters a number into eno
			    //let us remove extra enter key
			    sc.nextLine();
			    
			    String ename = sc.nextLine();//user enters string into name
			    int esal = sc.nextInt();//user enters salary into sal
			    
			    d.inserting(eno, ename, esal);
			   
			    break;
			case 3:
				try {
					//we have read eno and new salary from keyboard
					System.out.println("enter eno and new salary");
					int no = sc.nextInt();//we will enter eno here
					int newsal = sc.nextInt();// we will enter new salary here.
					d.updating(no, newsal);
				}catch(Exception e) {
					System.out.println("something went wrong..try again..");
				}
			    break;
			case 4:
				//Assignment to delete record
				System.out.println("Enter eno to delete the record");
			    int deleteEno = sc.nextInt();
			    d.deleting(deleteEno); // Call delete method
				break;
			case 5:
				//Assignment:student has to write for reading one row
				System.out.println("Enter eno to read the record");
			    int readeno = sc.nextInt();
			    d.displaying1(readeno); // Call a method to fetch and display the record
			    break;
			case 6:
				
				d.displaying();
				
				
			case 0:
				System.out.println("Ok I AM EXITING");
				try
				{
					Thread.sleep(3000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				return;
			}
		}while(option != 0);
		d.createTable();
		
		d.inserting(1, "Ramesh", 80000);//This will insert first employee
		d.inserting(2,"Suresh",90000);//This will insert second employee
        
		d.updating(1,60000);//this will update
		d.displaying();
		d.deleting(2);
		d.displaying1(1);
	}

}

/*
 * let us take another class for JDBC code
 * class name : DAO - data access object - DAO layout
 *              In this class we are going to write code for 5 operations
 */
class Dao
{
	//in this method we will create employee table
	public void createTable()

	{
		Statement s = null;
		Connection c = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    c = DriverManager.getConnection("jdbc:mysql://localhost:3306/palle","root","system");
		    s = c.createStatement();
			s.executeUpdate("create table employee(eno int primary key, ename varchar(55), esal int);");
			System.out.println("EMPLOYEE TABLE CREATED SUCCESSFULLY");
			try {
				Thread.sleep(4000);
			}catch (InterruptedException e) {
		    	e.printStackTrace();
		    } 
		} 
		catch (ClassNotFoundException e) {
		    System.out.println("DRIVER IS NOT PROPERLY LOADED");
			e.printStackTrace();
		} 
		catch (SQLException e) {
			System.out.println("SOMETHING WENT WRONG WITH DATABASE");
			e.printStackTrace();
		}
		finally
		{
			if(s!=null)
			{
				try {
					s.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(c!=null)
			{
				try {
					c.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//in below method we will insert data into employee table
	public void inserting(int eno, String ename, int esal)
	{
		Connection c = null;
		//Since we are getting Dynamic values
		PreparedStatement s = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/palle","root","system");
			
			String query = "insert into employee values(?,?,?)";
			s = c.prepareStatement(query);
			//1 for first question mark
			s.setInt(1, eno);
			//2 for Second question mark
			s.setString(2, ename);
			//3 for Third question mark
			s.setInt(3, esal);
			
			s.executeUpdate();
			System.out.println("SUCCESSFULLY INSERTED A ROW....");
			try {
		    	 Thread.sleep(4000);
		    }catch (InterruptedException e) {
		    	e.printStackTrace();
		    }
		} 
		catch (ClassNotFoundException e) {
			System.out.println("DRIVER IS NOT LOADED PROPERLY");
			//Error variable e
			// prints root cause clear error message
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		catch (SQLException e) {
			System.out.println("SOMETHING WENT WRONG WITH DATABASE");
			e.printStackTrace();
		}
		catch(Exception e)
		{
			//IF SOME UNKNOWN EXCEPTION OCCURS THIS CATCH BLOCK WILL EXECUTE
			System.out.println("SOMETHING UNUSAL THING HAPPEND");
		}
		finally 
		{
			if(s!=null)
			{
				try {
					s.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(s!=null)
			{
				try 
				{
					c.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}	
		}		
	}
	//in below method we will update .. for a given eno update new salary
	public void updating(int eno, int newsal)
	{
		Connection c = null;
		PreparedStatement s = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/palle","root","system");
			
			String query = "update employee set esal=? where eno=?";
			s = c.prepareStatement(query);
			s.setInt(1, newsal);
			s.setInt(2, eno);
			s.executeUpdate();

			System.out.println("RECORD UPDATED SUCCUSSFULLY.....");
			try {
				Thread.sleep(4000);
			}catch (InterruptedException e) {
		    	e.printStackTrace();
		    } 
		}
		catch (ClassNotFoundException e) {
			System.out.println("DRIVER IS NOT LOADED PROPERLY");
			e.printStackTrace();
		}
		catch (Exception e) {
		    System.out.println("SOMETHING WRONG WITH SQL CONNECTION");
			e.printStackTrace();
		} 
		finally
		{
			if(s!=null)
			{
				try 
				{
					s.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			if(c!=null)
			{
				try 
				{
				  c.close();
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	//Assignment : delete an employee based on eno
	public void deleting(int eno)
	{
		Connection c = null;
		PreparedStatement s  = null;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/palle","root","system");
			String query = "delete from employee where eno = ?";
			s = c.prepareStatement(query);
			s.setInt(1,eno);
			s.executeUpdate();
			System.out.println("SUCCESSFULLY RECORD DELETED");
			try {
				Thread.sleep(4000);
			}catch (InterruptedException e) {
		    	e.printStackTrace();
		    } 
		}
		catch (ClassNotFoundException e) {
		    System.out.println("DRIVER IS NOT PROPERLY LOADED");
			e.printStackTrace();
		} 
		catch (SQLException e) {
			System.out.println("SQL CONNECTION ISSUE");
			e.printStackTrace();
		}
		finally
		{
			if(s!=null)
			{
				try {
					s.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(c!=null)
			{
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//in below method we will read all employees and display their details
	public void displaying()
	{
		Connection c = null;
		Statement s = null;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/palle","root","system");
			s = c.createStatement();
			ResultSet res = s.executeQuery("select * from employee");
			System.out.println("\n---- EMPLOYEE TABLE DATA BELOW----\n");
			System.out.println("eno   ename     esal");
			System.out.println("---   -----     ----");
			while(res.next())
			{
				System.out.println(res.getInt(1)+"  "+res.getString(2)+"  "+res.getInt(3));
			}
			
			System.out.println("SUCCESSFULLY SHOW ALL RECORDS");
			try {
				Thread.sleep(4000);
			}catch (InterruptedException e) {
		    	e.printStackTrace();
		    } 
		}
		catch (ClassNotFoundException e) {
		    System.out.println("DRIVER IS NOT PROPERLY LOADED");
			e.printStackTrace();
		} 
		catch (SQLException e) {
			System.out.println("SQL CONNECTION ISSUE");
			e.printStackTrace();
		}
		finally
		{
			if(s!=null)
			{
				try {
					s.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(c!=null)
			{
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			}
	}
	
} 
	//ASSIGNMENT : display employee details based on eno
	public void displaying1(int eno)
	{
		Connection c = null;
		PreparedStatement ps = null;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost/palle","root","system");
			String query = "SELECT * FROM employee WHERE eno = ?";
            ps = c.prepareStatement(query);
            
            // Set the parameter (eno)
            ps.setInt(1, eno);
			ResultSet res1 = ps.executeQuery();
			System.out.println("\n---- EMPLOYEE TABLE DATA BELOW----\n");
			System.out.println("eno   ename     esal");
			System.out.println("---   -----     ----");
			while(res1.next())
			{
				System.out.println(res1.getInt(1)+"  "+res1.getString(2)+"  "+res1.getInt(3));
			}
			
			System.out.println("SUCCESSFULLY FETCH THE RECORD...");
			try {
				Thread.sleep(4000);
			}catch (InterruptedException e) {
		    	e.printStackTrace();
		    } 
		}
		catch (ClassNotFoundException e) {
		    System.out.println("DRIVER IS NOT PROPERLY LOADED");
			e.printStackTrace();
		} 
		catch (SQLException e) {
			System.out.println("SQL CONNECTION ISSU");
			e.printStackTrace();
		}
		finally
		{
			if(ps!=null)
			{
				try {
					ps.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(c!=null)
			{
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

		
		
	
