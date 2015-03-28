
public class Util {
	public static String reverse(String sequence) {
		String rev = "";
		for (int i = sequence.length() - 1; i >= 0; i--) {
			if (sequence.charAt(i) == 'A') {
				rev += 'T';
			}
			if (sequence.charAt(i) == 'C') {
				rev += 'G';
			}
			if (sequence.charAt(i) == 'T') {
				rev += 'A';
			}
			if (sequence.charAt(i) == 'G') {
				rev += 'C';
			}
		}
		return rev;
	}
}
