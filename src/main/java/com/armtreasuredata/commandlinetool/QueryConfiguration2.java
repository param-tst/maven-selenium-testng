package com.armtreasuredata.commandlinetool;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "query")
public class QueryConfiguration2 implements Runnable  {
	@Parameters(index = "0", arity = "1..*", description = "one ore more files to archive")
	private String db_name;
	@Parameters(index = "1", arity = "1..*", description = "one ore more files to archive")
    private String table_name;
	@Option(names = "-c", defaultValue = "*", description = "col_list=${DEFAULT-VALUE}")
	private String col_list;
	@Option(names = "-m",  description = "min_time=${DEFAULT-VALUE}")
	private String min_time;
	@Option(names = "-M",  description = "max_time=${DEFAULT-VALUE}")
	private String max_time;
	@Option(names = "-e", defaultValue = "presto", description = "engine=${DEFAULT-VALUE}")
	private String engine;
	@Option(names = "-f", defaultValue = "tabular", description = "format=${DEFAULT-VALUE}")
	private String format;
	@Option(names = "-l",  description = "limit=${DEFAULT-VALUE}")
	private String limit;

	 @Override
	    public void run() {
		 System.out.println("inside max");
		 if(this.max_time!=null && this.min_time!=null && !(Long.parseLong(this.max_time)>Long.parseLong(this.min_time))) {
			 this.max_time=this.min_time=null;
			 System.out.println("inside max");
		 }
	    }
	
	
	public String getDb_name() { return this.db_name ; }
	  public String getTable_name() { return this.table_name ; }
	

	public String getCol_list() {
		return this.col_list;
	}

	public String getMin_time() {
		return this.min_time;
	}

	public String getMax_time() {
		return this.max_time;
	}

	public String getEngine() {
		return this.engine;
	}

	public String getFormat() {
		return this.format;
	}

	public String getLimit() {
		return this.limit;
	}
	
	 @Override
	    public String toString()
	    {
	         String query="select " +this.getCol_list() + " from " + this.getTable_name() ;
	         
	         if(this.getMin_time()!=null ||this.getMax_time()!=null || (this.getMin_time()!=null && this.getMax_time()!=null ))
	        	 query=query+" WHERE TD_TIME_RANGE("+"time"+","+ this.getMin_time()+","+ this.getMax_time()+")";
	        			 if(this.getLimit()!=null && Integer.parseInt(this.getLimit())>0) 
	        				 query=query+" LIMIT "+Integer.parseInt(this.getLimit());
	         
	         return query;
	    }

}
