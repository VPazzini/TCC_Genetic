

public class Sequence {
	
	private String sequence;
	private String name;
	private Integer lenght;
	
	public Sequence(String name, String seq){
		this.setName(name);
		this.setSequence(seq);
		this.lenght = sequence.length();
	}
	
	public float findInSequence(Individual ind, boolean verbose) {
		float match1, match2, temp = 0;
		double threshold = 0.7;
		String subSeq = "", tempSeq = "";
		String motif = ind.getSequence();
		for (int i = 0; i < this.sequence.length() - motif.length(); i++) {
			subSeq = this.sequence.substring(i, i + motif.length());

			match1 = similarity(motif, subSeq);
			match2 = similarity(ind.getRevSequence(), subSeq);
			if (match1 >= threshold && match1 > temp && match1 > match2) {
				temp = find(motif, subSeq);
				tempSeq = subSeq;
			}
			if (match2 >= threshold && match2 > temp && match2 > match1) {
				temp = find(ind.getRevSequence(), subSeq);
				tempSeq = Util.reverse(subSeq);
			}
		}

		if (temp > 0) {
			ind.setPresence(ind.getPresence() + 1);
			ind.setFitness(ind.getFitness() + temp);
			ind.getMatches().add(tempSeq);
		}
		return temp;
	}


	public float similarity(String motif, String seq) {
		if (motif.length() != seq.length()) {
			return 0;
		}
		float match = 0;
		for (int i = 0; i < motif.length(); i++) {
			if (motif.charAt(i) == seq.charAt(i)) {
				match++;
			}
		}
		return match / motif.length();
	}

	public float find2(String motif, String seq) {
		if (motif.length() != seq.length()) {
			return 0;
		}
		float match = 0;
		for (int i = 0; i < motif.length(); i++) {
			if (motif.charAt(i) == seq.charAt(i)) {
				match++;
			}
		}
		return match / motif.length();
	}

	public float find(String motif, String seq) {
		if (motif.length() != seq.length()) {
			return 0;
		}
		float match = 0;
		double right = 1, wrong = 1;
		for (int i = 0; i < motif.length(); i++) {
			if (motif.charAt(i) == seq.charAt(i)) {
				match += right;
				right += 1;
				wrong = 1;
			} else {
				match -= wrong;
				wrong += 1;
				right = 1;
			}
		}
		return match;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int lenght(){
		return this.lenght;
	}
	
}
