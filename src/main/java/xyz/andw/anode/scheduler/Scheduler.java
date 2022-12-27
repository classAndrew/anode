package xyz.andw.anode.scheduler;

import java.util.PriorityQueue;

public class Scheduler extends PriorityQueue<Job> {

    public void addJob(Job job) {
        this.add(job);
    }

    public void addJob(Runnable runnable, long delay) {
        this.add(new Job(runnable, delay, false));
    }

    public void addJobAsync(Job job) {
        this.add(job);
    }

    public void addJobAsync(Runnable runnable, long delay) {
        this.add(new Job(runnable, delay, true));
    }
}
