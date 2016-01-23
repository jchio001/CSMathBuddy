package jonathanchiou.csmathhelper.main.Utils;

import java.util.ArrayList;

/**
 * Created by jman0_000 on 1/22/2016.
 */
public class TimedProblemData {
    long time;
    String num1;
    String num2;
    String op;
    String result;
    ArrayList<String> answerSet;

    public TimedProblemData(long time, String num1, String num2, String op, String result, ArrayList<String> answerSet) {
        this.time = time;
        this.num1 = num1;
        this.num2 = num2;
        this.op = op;
        this.result = result;
        this.answerSet = answerSet;
    }

    public Long getTime() { return time; }

    public void setTime(long time) { this.time = time; }

    public String getNum1() { return num1; }

    public void setNum1(String num1) { this.num1 = num1; }

    public String getNum2() { return num2; }

    public void setNum2(String num2) { this.num2 = num2; }

    public String getOp() { return op; }

    public void setOp(String op) { this.op = op; }

    public String getResult() { return result; }

    public void setResult(String result) { this.result = result; }

    public ArrayList<String> getAnswerSet() { return answerSet; }

    public void setAnswerSet(ArrayList<String> answerSet) { this.answerSet = answerSet; }
}
