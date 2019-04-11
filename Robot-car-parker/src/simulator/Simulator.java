package simulator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import space.*;
import ui.Workspace;

public class Simulator {

    private Workspace _wspace;
    private double _xsize;
    private double _ysize;
    private double _startx;
    private double _starty;
    private double _starttheta;
    private double _length;
    
    public Simulator(Workspace wspace) {
    	/*
        _wspace = wspace;
        _xsize = // x size
        _ysize = // y size
        _startx = // start x
        _starty = // start y
        _starttheta = // start theta
        _length = // length
        */
    }

    public Stack<Action> simulate(){
        boolean inGoal = false;
        Node endPoint;
        Node last = new Node(new Action(0, 0), _starttheta, _startx, _starty, 0);
        Queue<Node> nodeQueue = new LinkedList<>();
        double time = 0;
        double deltaT = .1;
        while (!inGoal && !nodeQueue.isEmpty()){
            time += deltaT;
            Node n = nodeQueue.poll();
            for (double turn = -30; turn <= 30; turn += 10){
                for (double accel = -2; accel <= 2; accel += .5){
                    double vprime = n.v + accel * deltaT;
                    if (math.abs(vprime) > 10) continue;
                    double tempVal = ((n.v + vprime) / 2) * deltaT;
                    double x = n.x + tempVal * Math.cos(n.theta);
                    double y = n.y + tempVal * math.sin(n.theta);
                    double roh = _length / math.tan(turn);
                    double theta = n.theta + ((n.v + vprime) / (2 * roh)) * deltaT;
                    if (/*this action does not cause a collision*/){
                        Node ndot = new Node(new Action(theta, accel), theta, x, y, time);
                        ndot.parent = n;
                        if (/*ndot is within the goal*/){
                            endPoint = ndot;
                            inGoal = true;
                            break;
                        }
                        nodeQueue.add(ndot);
                    }
                }
                if (inGoal) break;
            }
        }
        Node curr = endPoint;
        Stack<Action> actionStack = new Stack<>();
        while (curr.parent != null){
            actionStack.add(curr.action);
            curr = curr.parent;
        }
        return actionStack;
    }

    private class Action {
        
        public double turn;
        public double accel;
        
        public Action(double t, double a){
            turn = t;
            accel = a;
        }
    }

    private class Node {
        
        public Node parent = null;
        public Action action;
        public double theta;
        public double x;
        public double y;
        public int time;

        public Node(Action a, double t, double xx, double yy, int timebefore){
            action = a;
            theta = t;
            x = xx;
            y = yy;
            time = timebefore;
        }
    }
}