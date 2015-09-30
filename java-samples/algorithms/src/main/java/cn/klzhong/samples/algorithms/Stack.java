package cn.klzhong.samples.algorithms;


/**
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 *   push(x) -- Push element x onto stack.
 *   pop() -- Removes the element on top of the stack.
 *   top() -- Get the top element.
 *   getMin() -- Retrieve the minimum element in the stack.
 */
public class Stack {
    java.util.List<Integer> stackContent = new java.util.ArrayList<>();
    java.util.List<Integer> minStack = new java.util.ArrayList<>();

    public void test() {
        Stack s = new Stack();
        s.push(2);
        s.push(0);
        s.push(3);
        s.push(0);
        System.out.println(s.getMin() + "");
        s.pop();
        System.out.println(s.getMin() + "");
        s.pop();
        System.out.println(s.getMin() + "");
        s.pop();
        System.out.println(s.getMin() + "");
    }

    public void push(int x) {
        stackContent.add(x);
        if (minStack.isEmpty()) {
            minStack.add(x);
        } else {
            int lastMin = minStack.get(minStack.size() - 1);
            if (lastMin <= x) return;
            minStack.add(x);
        }
    }

    public void pop() {
        int last = stackContent.remove(stackContent.size() - 1);
        int lastMin = minStack.get(minStack.size() - 1);
        if (last == lastMin) {
            for (int i : stackContent) {
                if (i == lastMin) return;
            }
            minStack.remove(minStack.size() - 1);
        }
    }

    public int top() {
        return stackContent.get(stackContent.size() - 1);
    }

    public int getMin() {
        return minStack.get(minStack.size() - 1);
    }
}
