package jonathanchiou.csmathhelper.main.Utils;

//Since I'm pulling multiple items to my SharedPreferences
//I need to make a class so that my fragment(s) are able to receive
//all 4-5 values at once
public class ProblemData {
    private String num1;
    private String num2;
    private String result;
    private String mode;
    private String op;

    public ProblemData(String num1, String num2, String mode, String result, String op) {
        this.num1 = num1;
        this.num2 = num2;
        this.mode = mode;
        this.result = result;
        this.op = op;
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

    public String getOp() { return op; }
}
