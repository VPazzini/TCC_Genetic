package main;

import java.util.Random;

public class Selection {
	
	public Individual randomSelection(){
		Random r = new Random();

		int selected = r.nextInt(Population.getInstance().getPopulation().size());
		
		return Population.getInstance().getPopulation().get(selected);
	}
	
	public Individual roulletSelection() {
		int totalFitness = 0, temp = 0;
		int selected;
		Random r = new Random();
		for (Individual ind : Population.getInstance().getPopulation()) {
			totalFitness += this.getFitnessMethod(ind);
		}

		selected = r.nextInt(totalFitness);

		for (Individual ind : Population.getInstance().getPopulation()) {
			temp += this.getFitnessMethod(ind);
			
			if (temp > selected) {
				return ind;
			}
		}

		return null;
	}
	
	private float getFitnessMethod(Individual ind){
		return ind.getFitness();
		//return (ind.getFitness() * (ind.getPresence() + 1));
		//return ind.getFitness() * ind.getFitness() * ind.getFitness();
		//return ind.getFitness() * ind.getFitness();
	}

}
