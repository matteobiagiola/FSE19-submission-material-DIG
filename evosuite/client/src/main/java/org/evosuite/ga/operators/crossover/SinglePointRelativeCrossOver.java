/**
 * Copyright (C) 2010-2016 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.ga.operators.crossover;

import org.evosuite.Properties;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.utils.Randomness;

/**
 * Cross over individuals at relative position
 *
 * @author Gordon Fraser
 */
public class SinglePointRelativeCrossOver extends CrossOverFunction {

	private static final long serialVersionUID = -5320348525459502224L;

	/**
	 * {@inheritDoc}
	 *
	 * The splitting point is not an absolute value but a relative value (eg, at
	 * position 70% of n). For example, if n1=10 and n2=20 and splitting point
	 * is 70%, we would have position 7 in the first and 14 in the second.
	 * Therefore, the offspring d have n<=max(n1,n2)
	 */
	@Override
	public void crossOver(Chromosome parent1, Chromosome parent2)
	        throws ConstructionFailedException {
		
		if(parent1 instanceof TestChromosome){
			logger.debug("@@@@parent1: {}", ((TestChromosome) parent1).getTestCase().toCode());
			logger.debug("@@@@parent2: {}", ((TestChromosome) parent2).getTestCase().toCode());
		}
		
		if (parent1.size() < 2 || parent2.size() < 2) {
			return;
		}

		Chromosome t1 = parent1.clone();
		Chromosome t2 = parent2.clone();
		// Choose a position in the middle
		float splitPoint = Randomness.nextFloat();
		
		if(parent1 instanceof TestChromosome)
		{	
			int pos1 = 0;
			int pos2 = 0;
			
			if(Properties.SELENIUM){
				/*
				 *-1 it is needed because the crossover point cannot be at the end of one individual
				 *otherwise the offsprings would be identical to their parents
				 * 
				 * */
				int max_length = Properties.GENOTYPE_LENGTH - 1;
				logger.debug("@@@@single point crossover max length: {}", max_length);
				pos1 = ((int) Math.floor(max_length * splitPoint)) + 1;
				pos2 = ((int) Math.floor(max_length * splitPoint)) + 1;
			}
			else{
				pos1 = ((int) Math.floor((t1.size() - 1) * splitPoint)) + 1;
				pos2 = ((int) Math.floor((t2.size() - 1) * splitPoint)) + 1;
			}
			
			TestChromosome parent1Cast = (TestChromosome) parent1;
			TestChromosome parent2Cast = (TestChromosome) parent2;

			if(Properties.CUSTOM_MOSA){
				boolean bothMethodStatement = false;
				int defensiveCounter = 10;
				while(!bothMethodStatement && defensiveCounter >= 0){
					if(parent1Cast.getTestCase().hasStatement(pos1)
							&& pos1 > 0
							&& parent1Cast.getTestCase().getStatement(pos1) instanceof MethodStatement){
						logger.debug("headMethod set");
						if(parent2Cast.getTestCase().hasStatement(pos2) 
								&& pos2 > 0
								&& parent2Cast.getTestCase().getStatement(pos2) instanceof MethodStatement){
							logger.debug("tailMethod set");
							bothMethodStatement = true;
						}else{
							if(Randomness.nextDouble() <= 0.5){
								pos2--;
							}else{

								pos2++;
							}
						}
					}else{
						if(Randomness.nextDouble() <= 0.5){
							pos1--;
						}else{
							pos1++;
						}
					}
					defensiveCounter--;
				}
				logger.debug("Defensive counter: {}", defensiveCounter);
				if(defensiveCounter <= 0){
					logger.warn("Test case crossover MOSA failed, defensive counter 0");
					return;
				}else{
					logger.debug("@@@@test case single point crossover pos1, statement: {} , pos2, statement: {}", parent1Cast.getTestCase().getStatement(pos1), parent2Cast.getTestCase().getStatement(pos2));
					parent1.crossOver(t2, pos1, pos2);
					parent2.crossOver(t1, pos2, pos1);
				}
			}else{
				logger.debug("@@@@test case single point crossover pos1, statement: {} , pos2, statement: {}", parent1Cast.getTestCase().getStatement(pos1), parent2Cast.getTestCase().getStatement(pos2));
				parent1.crossOver(t2, pos1, pos2);
				parent2.crossOver(t1, pos2, pos1);
			}
		}else if(parent1 instanceof TestSuiteChromosome){
			int pos1 = ((int) Math.floor((t1.size() - 1) * splitPoint)) + 1;
			int pos2 = ((int) Math.floor((t2.size() - 1) * splitPoint)) + 1;
			
			logger.debug("@@@@test suite single point crossover pos1: {} , pos2: {}", pos1, pos2);

			parent1.crossOver(t2, pos1, pos2);
			parent2.crossOver(t1, pos2, pos1);
		}
		
	}

}
