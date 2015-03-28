import java.util.Random;

public class CrossOver {

	public Individual onePointCO(Individual ind1, Individual ind2) {
		Random r = new Random();

		int divPoint = r.nextInt(ind1.getSequence().length() - 3) + 1;
		String seq = ind1.getSequence().substring(0, divPoint);
		seq += ind2.getSequence().substring(divPoint);

		// MUTATION
		if (r.nextFloat() <= 0.01) {
			String[] nucleotides = { "A", "C", "T", "G" };
			int pos = r.nextInt(ind1.getSequence().length());
			String p1 = seq.substring(0, pos);
			String p2 = seq.substring(pos + 1);
			seq = p1 + nucleotides[r.nextInt(4)] + p2;
		}

		return new Individual(seq);
	}

	public Individual bestOfEach(Individual ind1, Individual ind2) {
		Random r = new Random();
		String seq = "";

		float[][] m1 = ind1.matrix();
		float[][] m2 = ind1.matrix();

		for (int j = 0; j < ind1.getSequence().length(); j++) {
			char c1 = ' ';
			float max1 = -1;
			for (int i = 0; i < 4; i++) {
				if(m1[i][j] > max1){
					max1 = m1[i][j];
					switch (i) {
					case (0):
						c1 = 'A';
						break;
					case (1):
						c1 = 'C';
						break;
					case (2):
						c1 = 'T';
						break;
					case (3):
						c1 = 'G';
						break;
					}
				}
			}
			for (int i = 0; i < 4; i++) {
				if(m2[i][j] > max1){
					max1 = m2[i][j];
					switch (i) {
					case (0):
						c1 = 'A';
					break;
					case (1):
						c1 = 'C';
					break;
					case (2):
						c1 = 'T';
					break;
					case (3):
						c1 = 'G';
					break;
					}
				}
			}
			seq += c1;
		}


		// MUTATION
		if (r.nextFloat() <= 0.01) {
			String[] nucleotides = { "A", "C", "T", "G" };
			int pos = r.nextInt(ind1.getSequence().length());
			String p1 = seq.substring(0, pos);
			String p2 = seq.substring(pos + 1);
			seq = p1 + nucleotides[r.nextInt(4)] + p2;
		}

		return new Individual(seq);
	}

	public Individual twoPointCO(Individual ind1, Individual ind2) {
		Random r = new Random();

		int temp1 = r.nextInt(ind1.getSequence().length() - 3) + 1;
		int temp2 = r.nextInt(ind1.getSequence().length() - 3) + 1;

		int divPoint1 = Math.min(temp1, temp2);
		int divPoint2 = Math.max(temp1, temp2);

		String seq = ind1.getSequence().substring(0, divPoint1);
		seq += ind2.getSequence().substring(divPoint1, divPoint2);
		seq += ind1.getSequence().substring(divPoint2);

		if (r.nextFloat() <= 0.01) {
			String[] nucleotides = { "A", "C", "T", "G" };
			int pos = r.nextInt(ind1.getSequence().length());

			/*
			 * if
			 * (ind1.matrix()[n.lastIndexOf(ind1.getSequence().charAt(pos))][pos
			 * ] != 1 && ind2.matrix()[n.lastIndexOf(ind2.getSequence().charAt(
			 * pos))][pos] != 1) {
			 */

			String p1 = seq.substring(0, pos);
			String p2 = seq.substring(pos + 1);
			seq = p1 + nucleotides[r.nextInt(4)] + p2;
		}

		return new Individual(seq);
	}

}
