package ds.algo.dcp;

public class IntegerPalindrome {

  public static void main(String[] args) {
    System.out.println(isPalindrome(121));
    System.out.println(isPalindrome(888));
    System.out.println(isPalindrome(123));
    System.out.println(isPalindrome(1221));
    System.out.println(isPalindrome(2232));
    System.out.println(isPalindrome(22322));
    System.out.println(isPalindrome(22325));
    System.out.println(isPalindrome(22532));
  }

  private static boolean isPalindrome(int num) {
    boolean res = true;
    int temp = num;
    int div = 1;
    while (temp > 9) {
      div *= 10;
      temp /= 10;
    }

    while (num > 9 && res) {
      int f = num / div;
      int l = num % 10;
      res = f == l;
      num %= div;
      num /= 10;
      div /= 100;
    }

    return res;
  }
}
