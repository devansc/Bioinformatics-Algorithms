public class test {

    public static void main(String[] args) {
        int i, sum = 0;
        for (i = 1; i <= 52; i ++) {
            sum += i;
            System.out.println("Week " + i +  " deposit amount = $" + i + " account balance = " + sum);
        }
        System.out.println(sum);
    }
}
