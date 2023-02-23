import java.util.HashMap;
import java.util.Stack;

public class Mather {
    public String infixExpr;
    public String postfixExpr;

    private final HashMap<Character, Integer> operationPriority = new HashMap<Character, Integer>() {{
        put('(', 0);
        put('+', 1);
        put('-', 1);
        put('*', 2);
        put('/', 2);
        put('^', 3);
        put('~', 4);
    }};


    public Mather(String expression) {
        infixExpr = expression;
        postfixExpr = toPostfix(infixExpr + "\r");
    }

    private String getStringNumber(String expr, int pos) {
        String strNumber = "";

        for (; pos < expr.length(); pos++) {
            char num = expr.charAt(pos);

            if (Character.isDigit(num))
                strNumber += num;
            else {
                pos--;
                break;
            }
        }

        return strNumber;
    }

    private String toPostfix(String infixExpr) {
        StringBuilder postfixExpr = new StringBuilder();
        Stack<Character> stack = new Stack<Character>();

        for (int i = 0; i < infixExpr.length(); i++) {
            char c = infixExpr.charAt(i);

            if (Character.isDigit(c)) {
                postfixExpr.append(getStringNumber(infixExpr, i)).append(" ");
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (stack.size() > 0 && stack.peek() != '(')
                    postfixExpr.append(stack.pop());
                stack.pop();
            } else if (operationPriority.containsKey(c)) {
                char op = c;

                if (op == '-' && (i == 0 || (i > 1 && operationPriority.containsKey(infixExpr.charAt(i - 1)))))
                    op = '~';

                while (!stack.isEmpty() && (operationPriority.get(stack.peek()) >= operationPriority.get(op)))
                    postfixExpr.append(stack.pop());
                stack.push(op);
            }
        }
        for (char op : stack)
            postfixExpr.append(op);

        return postfixExpr.toString();
    }

    private double execute(char op, double first, double second) {
        switch (op) {
            case '+':
                return first + second;
            case '-':
                return first - second;
            case '*':
                return first * second;
            case '/':
                return first / second;
            case '^':
                return Math.pow(first, second);
            default:
                return 0;
        }
    }

    public double calculate()
    {
        Stack<Double> locals = new Stack<Double>();
        int counter = 0;

        for (int i = 0; i < postfixExpr.length(); i++)
        {
            char c = postfixExpr.charAt(i);

            if (Character.isDigit(c))
            {
                String number = getStringNumber(postfixExpr, i);
                locals.push(Double.parseDouble(number));
            }
            else if (operationPriority.containsKey(c))
            {
                counter += 1;
                if (c == '~')
                {
                    double last = locals.size() > 0 ? locals.pop() : 0;

                    locals.push(execute('-', 0, last));
                    System.out.println(counter + ") " + c + last + " = " + locals.peek());
                    continue;
                }

                double second = locals.size() > 0 ? locals.pop() : 0,
                        first = locals.size() > 0 ? locals.pop() : 0;

                locals.push(execute(c, first, second));
                System.out.println(counter + ") " + first + " " + c + " " + second + " = " + locals.peek());
            }
        }

        return locals.pop();
    }
}
