import java.util.Random;

public class Selection {

	public Individual roulletSelection() {
		int totalFitness = 0, temp = 0;
		int selected;
		Random r = new Random();
		for (Individual ind : Population.getInstance().getPopulation()) {
			totalFitness += ind.getFitness();
			//totalFitness += (ind.getFitness() * (ind.getPresence() + 1));
			//totalFitness += (ind.getFitness() * ind.getFitness());
		}

		selected = r.nextInt(totalFitness);

		for (Individual ind : Population.getInstance().getPopulation()) {
			temp += ind.getFitness();
			//temp += (ind.getFitness() * (ind.getPresence() + 1));
			//temp += (ind.getFitness() * ind.getFitness());
			
			if (temp > selected) {
				return ind;
			}
		}

		return null;
	}

}
