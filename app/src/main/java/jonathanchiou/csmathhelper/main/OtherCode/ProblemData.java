package jonathanchiou.csmathhelper.main.OtherCode;

/**
 * Created by jman0_000 on 1/9/2016.
 */
public class ProblemData {
    private String num1;
    private String num2;
    private String result;
    private String mode;

    public ProblemData(String num1, String num2, String mode, String result) {
        this.num1 = num1;
        this.num2 = num2;
        this.mode = mode;
        this.result = result;
    }

    public String getNum1() {
        return num1;
    }

    public String getNum2() {
        return num2;
    }

    public String getResult() {
        return result;
    }

    public String getMode() {
        return mode;
    }
}
