import java.util.Random;


public class CrossOver {
	
	public Individual onePointCO(Individual ind1, Individual ind2) {
		Random r = new Random();

		int divPoint = r.nextInt(ind1.getSequence().length() - 3) + 1;
		String seq = ind1.getSequence().substring(0, divPoint);
		seq += ind2.getSequence().substring(divPoint);
		
		//MUTATION
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
		seq += ind2.getSequence().substring(divPoint1,divPoint2);
		seq += ind1.getSequence().substring(divPoint2);
		
		if (r.nextFloat() <= 0.01) {
			String[] nucleotides = { "A", "C", "T", "G" };
			int pos = r.nextInt(ind1.getSequence().length());
			
			/*if (ind1.matrix()[n.lastIndexOf(ind1.getSequence().charAt(pos))][pos] != 1
			&& ind2.matrix()[n.lastIndexOf(ind2.getSequence().charAt(
					pos))][pos] != 1) {
			 */
			
			String p1 = seq.substring(0, pos);
			String p2 = seq.substring(pos + 1);
			seq = p1 + nucleotides[r.nextInt(4)] + p2;
		}
		
		return new Individual(seq);
	}
	
	
}
