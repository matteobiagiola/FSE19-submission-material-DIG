package org.evosuite.testcase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.evosuite.testcase.statements.Statement;

public class SeleniumGenotype implements Cloneable, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5713124578984121840L;

	protected List<Statement> statements;
	
	/** The actual java variables containing the values of the 'genotype' entries */
	protected List<Integer> statementPositions;
	
	public SeleniumGenotype() {
		statements = new ArrayList<Statement>();
		statementPositions = new ArrayList<Integer>();
	}
	
	public SeleniumGenotype(int size){
		statements = new ArrayList<Statement>(size);
		statementPositions = new ArrayList<Integer>(size);
	}

	/**
	 * @param statements the genotype to set
	 */
	public void setStatements(List<Statement> statements) {
		this.statements = statements;
	}

	/**
	 * @return the genotype
	 */
	public List<Statement> getStatements() {
		return statements;
	}

	/**
	 * @param parameterPositions the parameterPositions to set
	 */
	public void setStatementPositions(List<Integer> statementPositions) {
		this.statementPositions = statementPositions;
	}

	/**
	 * @return the parameterPositions
	 */
	public List<Integer> getStatementPositions() {
		return statementPositions;
	}
	
	@Override
	public SeleniumGenotype clone (){
		SeleniumGenotype copy = new SeleniumGenotype();
		ArrayList<Statement> g = new ArrayList<Statement>(statements.size());
		for (Statement s : statements){
			g.add(s);
		}
		copy.setStatements(g);
		
		ArrayList<Integer> positions = new ArrayList<Integer>(statementPositions.size());
		for (Integer i : statementPositions){
			positions.add(i);
		}
		copy.setStatementPositions(positions);
		return copy;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (Iterator<Statement> iterator = statements.iterator(); iterator.hasNext();) {
			buffer.append(((Statement) iterator.next()).toString());
			if (iterator.hasNext()){
				buffer.append(",");
			}
		}
		buffer.append("]");
		return buffer.toString();
	}

}
