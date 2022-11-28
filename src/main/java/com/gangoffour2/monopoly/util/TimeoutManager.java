package com.gangoffour2.monopoly.util;

public class TimeoutManager <T>{

    private State state;

    public TimeoutManager(){
        state = State.WAITING;
    }

    public synchronized T waitTimeout(NoArgsFunction<T> normal, NoArgsFunction<T> timeout, Integer milliseconds)
            throws InterruptedException {
        wait(milliseconds);
        if (state == State.STOPPED){
            return normal.exec();
        }
        else {
            return timeout.exec();
        }
    }

    public synchronized void stopTimeout(){
        if (state == State.WAITING){
            state = State.STOPPED;
            notifyAll();
        }
    }

}
