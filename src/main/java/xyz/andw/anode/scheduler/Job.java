package xyz.andw.anode.scheduler;

public class Job implements Comparable<Job> {
    
    public boolean threaded = false;
    public long runAt = 0;
    public Runnable runnable;

    public Job(Runnable runnable, long delay, boolean threaded) {
        runAt = System.currentTimeMillis() + delay;
        this.runnable = runnable;
        this.threaded = threaded;
    }

    public void run() {
        if (threaded) {
            new Thread(runnable).start();
        } else {
            runnable.run();
        }
    }
    
    public int compareTo(Job other) {
        if (runAt == other.runAt) return 0;
        return runAt < other.runAt ? -1 : 1;
    }
}
