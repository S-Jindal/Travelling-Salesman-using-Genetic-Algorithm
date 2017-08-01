# Solution to TSP using Genetic Algorithm.


Anyone who wants to contribute/imrove the quality of the solution or change anything, do it and upload the results as well so that we can get a clear idea of how the performance was affected.
<br />
<br />
# Details of the Algorithm :
<br />
<br />
_Phenotype and Genotype_
Both of them are same in this algo and basically reperesented by a permuatation of N integers (where N is number of cities). All the permutations are generated by keeping in mind that constraints that each adjacent entries will have an edge and the last element will have an edge with the first element as well.
<br />
<br />
*Population Model*
It is neither a steady state GA nor generational GA. It generates some k number of offsprings in each generation where k is a random number in range [1, MAX_REPRODUCTIONS] where MAX_REPRODUCTIONS is set to some constant(refer to code GA.java)
<br />
<br />
*Parent Generation*
Parent generation is done via Stochastic Universal Sampling which is very similar to Roulette wheel method. 
<br />
<br />
*Crossover*
This code uses Davi's order crossover (OX1).
<br />
<br />
*Mutation Operator*
The mutation operation is rather just a swap mutation which will interchange two random points. In case non valid species is generated, the solution is rejected and mutation is called again (but not more than a certain limit). 
Elitism is employed as well and the most fit member is never mutated. For other members, probability of mutation is set in the code - pm.
<br />
<br />
*Survivor Selection*
In this fitness based selection is employed, the children tend to replace the least fit individuals in the population. 
<br />
<br />
