package pl.edu.pja.s28687.logistics;

import pl.edu.pja.s28687.train.Locomotive;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class RailroadLock extends ReentrantLock {

    private final Map<Thread, Locomotive> waitingLocomotives = new HashMap<>();

    public RailroadLock() {
        super(true);
    }

    public void lock(Locomotive locomotive) {
        waitingLocomotives.put(Thread.currentThread(), locomotive);
        super.lock();
        waitingLocomotives.remove(Thread.currentThread());
    }

    public void unlock() {
        super.unlock();
    }

    public List<Thread> getWaitingThreads() {
        return new ArrayList<>(getQueuedThreads());
    }

    public List<Locomotive> getWaitingLocomotives() {
        List<Locomotive> waitingLocs = new LinkedList<>();
        for (Thread t : getQueuedThreads()) {
            waitingLocs.add(waitingLocomotives.get(t));
        }
        Collections.reverse(waitingLocs);
        return waitingLocs;
    }
}
