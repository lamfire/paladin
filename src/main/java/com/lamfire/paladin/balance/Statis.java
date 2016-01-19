package com.lamfire.paladin.balance;

import com.lamfire.logger.Logger;
import com.lamfire.utils.Maps;
import com.lamfire.utils.ThreadFactory;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


class Statis implements Runnable{
    private static final Logger LOGGER = Logger.getLogger(Statis.class);
    private final AtomicLong requests = new AtomicLong();
    private final AtomicLong forwards = new AtomicLong();
    private final AtomicLong ignores = new AtomicLong();
    private final AtomicLong waitQueueFullIgnores = new AtomicLong();
    private final Map<Backend,AtomicLong> backendForwards = Maps.newHashMap();

    private static final Statis instance = new Statis();

    public static Statis getInstance(){
        return instance;
    }

    private Statis(){
        service.scheduleWithFixedDelay(this,5,5, TimeUnit.SECONDS);
    }

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(new ThreadFactory("BALANCE_STATIS"));
    private long waitQueueLength = 0;

    public void incrementWaitQueueFullIgnores(){
        waitQueueFullIgnores.incrementAndGet();
    }

    public void incrementRequests(){
        requests.incrementAndGet();
    }

    public void incrementForwards(){
        forwards.incrementAndGet();
    }

    public void incrementIgnores(){
        ignores.incrementAndGet();
    }

    public void setWaitQueueLength(long waitQueueLength){
        this.waitQueueLength = waitQueueLength;
    }

    public void incrementBackend(Backend backend){
        AtomicLong atom = backendForwards.get(backend);
        if(atom == null){
            atom = new AtomicLong(0);
            backendForwards.put(backend,atom);
        }
        atom.incrementAndGet();
    }

    @Override
    public String toString() {
        return "Statis{" +
                "requests=" + requests +
                ", waitQueueLength=" + waitQueueLength +
                ", forwards=" + forwards +
                ", ignores=" + ignores +
                ", backendForwards=" + backendForwards +
                '}';
    }

    @Override
    public void run() {
        LOGGER.info(this.toString());
    }
}
